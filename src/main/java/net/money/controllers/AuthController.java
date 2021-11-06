package net.money.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.money.models.ERole;
import net.money.models.Family;
import net.money.models.Role;
import net.money.models.User;
import net.money.payload.request.LoginRequest;
import net.money.payload.request.SignupRequest;
import net.money.payload.response.JwtResponse;
import net.money.payload.response.MessageResponse;
import net.money.repository.FamilyRepository;
import net.money.repository.RoleRepositoty;
import net.money.repository.UserRepository;
import net.money.security.jwt.JwtUtils;
import net.money.security.services.UserDetailsImpl;

@CrossOrigin(origins = "${crossOrigin}", maxAge =  3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepositoty roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	FamilyRepository familyRepository;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(),
												 userDetails.getEmail(),
												 userDetails.getFamily().getId(),
												 userDetails.getDisplayname(),
												 roles));
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken"));
			//Create new user's account
			}
			Family family = familyRepository.findById(signupRequest.getFamilyId()).orElse(null);
			if(family == null) {
				return ResponseEntity.ok(new MessageResponse("Family is not found!"));
			}
			User user = new User(signupRequest.getUsername(), 
								encoder.encode(signupRequest.getPassword()), 
								signupRequest.getEmail(), 
								family, 
								signupRequest.getDisplayname());
					
			Set<String> strRoles = signupRequest.getRole();
			Set<Role> roles = new HashSet<>();
			
			if(strRoles == null) {
				Role userRole = roleRepository.findByName(ERole.ROLE_USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found"));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch(role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
										.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
					case "householder":
						Role householderRole = roleRepository.findByName(ERole.ROLE_HOUSEHOLDER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(householderRole);
					default:
						Role userRole = roleRepository.findByName(ERole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
					
				});
			}
			user.setRoles(roles);
			userRepository.save(user);
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
			
			
		
	}
}
