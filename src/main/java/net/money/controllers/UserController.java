package net.money.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.money.models.Family;
import net.money.models.User;
import net.money.repository.ExpenseInfoRepository;
import net.money.repository.ExpenseTypeRepository;
import net.money.repository.FamilyRepository;
import net.money.repository.RoleRepositoty;
import net.money.repository.StoreRepository;
import net.money.repository.UserRepository;
import net.money.security.jwt.JwtUtils;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "${crossOrigin}", maxAge = 3600)
public class UserController {
	
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
	
	@PostMapping("/member-list")
	@PreAuthorize("hasRole('HOUSEHOLDER')")
	public ResponseEntity<List<User>> getMemberOfFamily(@RequestBody String token){
		String username = jwtUtils.getUserNameFromJwtToken(token);
		User user = userRepository.findByUsername(username).orElse(null);
		Family family = user.getFamily();
		
		List<User> users = userRepository.findByFamily(family);
		return ResponseEntity.ok(users);
	}
}
