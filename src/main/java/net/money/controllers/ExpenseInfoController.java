package net.money.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.money.exeption.ResourceNotFoundException;
import net.money.models.ERole;
import net.money.models.ExpenseInfo;
import net.money.models.ExpenseType;
import net.money.models.Family;
import net.money.models.Role;
import net.money.models.Store;
import net.money.models.User;
import net.money.payload.request.ExpenseInfoRequest;
import net.money.payload.response.ExpenseInfoResponse;
import net.money.repository.ExpenseInfoRepository;
import net.money.repository.ExpenseTypeRepository;
import net.money.repository.FamilyRepository;
import net.money.repository.RoleRepositoty;
import net.money.repository.StoreRepository;
import net.money.repository.UserRepository;
import net.money.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/expenseinfo/")
@CrossOrigin(origins = "${crossOrigin}", maxAge = 3600)
public class ExpenseInfoController {
	@Autowired
	ExpenseInfoRepository expenseInfoRepository;

	@Autowired
	ExpenseTypeRepository expenseTypeRepository;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	FamilyRepository familyRepository;

	@Autowired
	JwtUtils jwtUtils = new JwtUtils();

	@Autowired
	RoleRepositoty roleRepositoty;

	@PostMapping("/list/{year}/{month}")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public List<ExpenseInfoResponse> getListExpenseInfo(@RequestBody String token, @PathVariable Integer year,
			@PathVariable Integer month) {

		// Tim ngay cuoi thang va dau thang
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month - 1);
		gc.set(Calendar.DAY_OF_MONTH, 0);
		Date monthStart = gc.getTime();
		gc.add(Calendar.MONTH, 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		Date monthEnd = gc.getTime();
		// Ket thuc tim ngay cuoi thang, dau thang

		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username).orElse(null);
		Family family = user.getFamily();
		Set<Role> roles = user.getRoles();

		List<ExpenseInfo> expenseInfos = new ArrayList<>();
		List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		if (roles.contains(roleRepositoty.findByName(ERole.ROLE_HOUSEHOLDER).orElse(null))) {
			expenseInfos = expenseInfoRepository.getExpenseInfoForFamily(family, monthStart, monthEnd);

		} else if (roles.contains(roleRepositoty.findByName(ERole.ROLE_USER).orElse(null))) {
			expenseInfos = expenseInfoRepository.getExpenseInfoForUser(user, monthStart, monthEnd);
		} else {
			expenseInfos = null;
		}

		if (expenseInfos != null) {
			for (ExpenseInfo expenseInfo : expenseInfos) {
				ExpenseInfoResponse expenseInfoResponse = new ExpenseInfoResponse(expenseInfo.getId(),
						expenseInfo.getExpenseType().getName(), simpleDateFormat.format(expenseInfo.getDatetime()),
						expenseInfo.getAmountOfMoney(), expenseInfo.getStore().getName(), expenseInfo.getName(),
						expenseInfo.getImage(), expenseInfo.getMemo(), expenseInfo.getUser().getId(),
						expenseInfo.getUser().getDisplayname());
				expenseInfoResponses.add(expenseInfoResponse);
			}
			return expenseInfoResponses;
		} else {
			return null;
		}

	}

	@GetMapping("/get-expenseinfo/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public ExpenseInfo getExpenseInfoById(@PathVariable Long id) {
		ExpenseInfo expenseInfo = expenseInfoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense Info not found with id:" + id));

		return expenseInfo;
	}

	@PostMapping("/filter-data")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public ModelMap getFilterData(@RequestBody String token) {
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username).orElse(null);
		Set<Role> roles = user.getRoles();
		Family family = user.getFamily();
		// Get ExpenseType
		List<ExpenseType> expenseTypes = expenseTypeRepository.findByFamily(family);
		// Get Store List
		List<Store> stores = storeRepository.findByFamily(family);
		// Get Users
		List<User> userList = new ArrayList<>();
		if (roles.contains(roleRepositoty.findByName(ERole.ROLE_HOUSEHOLDER).orElse(null))) {
			userList = userRepository.findByFamily(family);

		} else if (roles.contains(roleRepositoty.findByName(ERole.ROLE_USER).orElse(null))) {
			userList.add(user);
		} else {
			userList = null;
		}
		// Return ModelMap
		ModelMap FilterData = new ModelMap();
		FilterData.addAttribute("expenseTypes", expenseTypes);
		FilterData.addAttribute("stores", stores);
		FilterData.addAttribute("userList", userList);
		return FilterData;

	}

	@PostMapping("/add")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public ExpenseInfo addNewExpenseInfo(@RequestBody ExpenseInfoRequest expenseInfoRequest) {
		ExpenseType expenseType = expenseTypeRepository.findById(expenseInfoRequest.getExpenseTypeId()).orElse(null);
		Family family = familyRepository.findById(expenseInfoRequest.getFamilyId()).orElse(null);
		Store store = storeRepository.findById(expenseInfoRequest.getStoreId()).orElse(null);
		User user = userRepository.findById(expenseInfoRequest.getUserId()).orElse(null);

		ExpenseInfo expenseInfo = new ExpenseInfo(expenseType, family, expenseInfoRequest.getDatatime(),
				expenseInfoRequest.getAmountOfMoney(), store, expenseInfoRequest.getName(),
				expenseInfoRequest.getImage(), expenseInfoRequest.getMemo(), user);

		store.setUsingTimes(store.getUsingTimes() + 1);
		storeRepository.save(store);
		return expenseInfoRepository.save(expenseInfo);

	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public ResponseEntity<Map<String, Boolean>> deleteExpenseById(@PathVariable Long id) {
		ExpenseInfo expenseInfo = expenseInfoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense Info not found with id:" + id));
		expenseInfoRepository.delete(expenseInfo);

		Map<String, Boolean> respose = new HashMap<>();
		respose.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(respose);
	}

	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public ResponseEntity<Boolean> editExpenseInfo(@PathVariable Long id,
			@RequestBody ExpenseInfoRequest expenseInfoRequest) {
		ExpenseInfo expenseInfo = expenseInfoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense Info not found" + id));
		// Thay doi so lan su dung store
		Store oldStore = expenseInfo.getStore();
		oldStore.setUsingTimes(oldStore.getUsingTimes() - 1);
		storeRepository.save(oldStore);

		ExpenseType expenseType = expenseTypeRepository.findById(expenseInfoRequest.getExpenseTypeId()).orElse(null);
		Family family = familyRepository.findById(expenseInfoRequest.getFamilyId()).orElse(null);
		Store store = storeRepository.findById(expenseInfoRequest.getStoreId()).orElse(null);
		User user = userRepository.findById(expenseInfoRequest.getUserId()).orElse(null);

		expenseInfo.setExpenseType(expenseType);
		expenseInfo.setDatetime(expenseInfoRequest.getDatatime());
		expenseInfo.setAmountOfMoney(expenseInfoRequest.getAmountOfMoney());
		expenseInfo.setStore(store);
		expenseInfo.setName(expenseInfoRequest.getName());
		expenseInfo.setImage(expenseInfoRequest.getImage());
		expenseInfo.setMemo(expenseInfoRequest.getMemo());
		expenseInfo.setUser(user);
		expenseInfoRepository.save(expenseInfo);

		Store newStore = expenseInfo.getStore();
		newStore.setUsingTimes(newStore.getUsingTimes() + 1);
		storeRepository.save(newStore);

		return ResponseEntity.ok(Boolean.TRUE);
	}

	@PostMapping("/filter")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public List<ExpenseInfoResponse> getDataForFilter(@RequestBody LinkedHashMap<String, String> filterData) {
		// Lay du lieu gui ve tu Filter
		System.out.println(filterData);
		Long expenseTypeId = Long.parseLong(filterData.get("expenseTypeId"));
		Integer moneyRange = Integer.parseInt(filterData.get("moneyRange"));
		Integer year = Integer.parseInt(filterData.get("year"));
		Integer month = Integer.parseInt(filterData.get("month"));
		Long storeId = Long.parseLong(filterData.get("storeId"));
		Long memberId = Long.parseLong(filterData.get("memberId"));
		String accessToken = filterData.get("accessToken");
		// Phan tich nguoi dung da gui ve du lieu
		String username = jwtUtils.getUserNameFromJwtToken(accessToken);
		User user = userRepository.findByUsername(username).orElse(null);
		Set<Role> roles = user.getRoles();
		Family family = user.getFamily();

		// Chuyen du lieu loc thanh model
		ExpenseType expenseType = expenseTypeRepository.findById(expenseTypeId).orElse(null);
		Store store = storeRepository.findById(storeId).orElse(null);
		User member = userRepository.findById(memberId).orElse(null);
		Long lowMoney = null;
		Long highMoney = null;
		if(moneyRange.equals(1)) {
			lowMoney = 0L;
			highMoney = 1000L;
		}
		else if(moneyRange.equals(2)){
			lowMoney = 1000L;
			highMoney = 5000L;
		}
		else if(moneyRange.equals(3)) {
			lowMoney = 5000L;
			highMoney = 10000L;
		}
		else if(moneyRange.equals(4)) {
			lowMoney = 10000L;
			highMoney = Long.MAX_VALUE;
		}
		else {
			lowMoney = null;
			highMoney = null;
		}
		// Tim ngay cuoi thang va dau thang
		Calendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month - 1);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		Date monthStart = gc.getTime();
		gc.add(Calendar.MONTH, 1);
		gc.add(Calendar.DAY_OF_MONTH, -1);
		Date monthEnd = gc.getTime();
		// Lay du lieu
		List<ExpenseInfo> expenseInfos = new ArrayList<>();
		List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		if (roles.contains(roleRepositoty.findByName(ERole.ROLE_HOUSEHOLDER).orElse(null))) {
			expenseInfos = expenseInfoRepository.getExpenseInfoForFilter(family, expenseType, store, member, lowMoney,
					highMoney, monthStart, monthEnd);

		} else if (roles.contains(roleRepositoty.findByName(ERole.ROLE_USER).orElse(null))) {
			expenseInfos = expenseInfoRepository.getExpenseInfoForFilter(family, expenseType, store, user, lowMoney,
					highMoney, monthStart, monthEnd);
		} else {
			expenseInfos = null;
		}

		if (expenseInfos != null) {
			for (ExpenseInfo expenseInfo : expenseInfos) {
				ExpenseInfoResponse expenseInfoResponse = new ExpenseInfoResponse(expenseInfo.getId(),
						expenseInfo.getExpenseType().getName(), simpleDateFormat.format(expenseInfo.getDatetime()),
						expenseInfo.getAmountOfMoney(), expenseInfo.getStore().getName(), expenseInfo.getName(),
						expenseInfo.getImage(), expenseInfo.getMemo(), expenseInfo.getUser().getId(),
						expenseInfo.getUser().getDisplayname());
				expenseInfoResponses.add(expenseInfoResponse);
			}
			return expenseInfoResponses;
		} else {
			return null;
		}
	}

}
