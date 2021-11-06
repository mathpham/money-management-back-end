package net.money.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.money.exeption.ResourceNotFoundException;
import net.money.models.Family;
import net.money.models.Store;
import net.money.models.User;
import net.money.payload.request.StoreRequest;
import net.money.repository.ExpenseInfoRepository;
import net.money.repository.ExpenseTypeRepository;
import net.money.repository.FamilyRepository;
import net.money.repository.RoleRepositoty;
import net.money.repository.StoreRepository;
import net.money.repository.UserRepository;
import net.money.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/store/")
@CrossOrigin(origins = "${crossOrigin}", maxAge = 3600)
public class StoreController {
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
	
	@PostMapping("/list")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public List<Store> getListStore(@RequestBody String token){
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username).orElse(null);
		
		Family family = user.getFamily();
		
		List<Store> stores = storeRepository.findByFamily(family);
		
		return stores;
	}
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public Store addStore(@RequestBody StoreRequest storeRequest){
		Family family = familyRepository.findById(storeRequest.getFamilyid()).orElse(null);
		
		Store store = new Store(family, 
									storeRequest.getStoreInfo(), 
									storeRequest.getMemo(), 
									storeRequest.getName(), 
									storeRequest.getUsingTimes());
		return storeRepository.save(store);
	}
	
	@GetMapping("/get-store/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('HOUSEHOLDER')")
	public Store getStoreById(@PathVariable Long id) {
		Store store = storeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Store not found with id:" +id));
		
		return store;
	}
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public ResponseEntity<Boolean> editStore(@PathVariable Long id, @RequestBody StoreRequest storeRequest){
		Store store = storeRepository.findById(id).orElse(null);
		
		store.setStoreInfo(storeRequest.getStoreInfo());
		store.setMemo(storeRequest.getMemo());
		store.setName(storeRequest.getName());
		store.setUsingTimes(storeRequest.getUsingTimes());
		
		storeRepository.save(store);
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
}
