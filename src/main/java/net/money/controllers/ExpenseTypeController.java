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
import net.money.models.ExpenseInfo;
import net.money.models.ExpenseType;
import net.money.models.Family;
import net.money.models.User;
import net.money.payload.request.ExpenseInfoRequest;
import net.money.payload.request.ExpenseTypeRequest;
import net.money.repository.ExpenseTypeRepository;
import net.money.repository.FamilyRepository;
import net.money.repository.UserRepository;
import net.money.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/expensetype/")
@CrossOrigin(origins = "${crossOrigin}", maxAge = 3600)
public class ExpenseTypeController {

	@Autowired
	JwtUtils jwtUtils = new JwtUtils();
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ExpenseTypeRepository expenseTypeRepository;
	
	@Autowired
	FamilyRepository familyRepository;
	
	@PostMapping("/list")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public List<ExpenseType> getExpenseTypeList(@RequestBody String token){
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username).orElse(null);
		
		Family family = user.getFamily();
		
		List<ExpenseType> expenseTypes = expenseTypeRepository.findByFamily(family);
		
		return expenseTypes;
	}
	@PostMapping("/add")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public ExpenseType addExpenseTypes(@RequestBody ExpenseTypeRequest expenseTypeRequest){
		Family family = familyRepository.findById(expenseTypeRequest.getFamilyid()).orElse(null);
		
		ExpenseType expenseType = new ExpenseType(
												expenseTypeRequest.getName(), 
												family, 
												expenseTypeRequest.getMemo());
		return expenseTypeRepository.save(expenseType);
	}
	
	@GetMapping("/get-expensetype/{id}")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public ExpenseType getExpenseTypeById(@PathVariable Long id) {
		ExpenseType expenseType = expenseTypeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Expense Type not found with id:" +id));
		
		return expenseType;
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public ResponseEntity<Boolean> editExpenseType(@PathVariable Long id, @RequestBody ExpenseTypeRequest expenseTypeRequest){
		ExpenseType expenseType = expenseTypeRepository.findById(id).orElse(null);
		expenseType.setName(expenseTypeRequest.getName());
		expenseType.setMemo(expenseTypeRequest.getMemo());
		
		expenseTypeRepository.save(expenseType);
		return ResponseEntity.ok(Boolean.TRUE);
	}
	
}
