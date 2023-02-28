package com.linktreeclone.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linktreeclone.api.exception.CredentialsTakenException;
import com.linktreeclone.api.exception.NotFoundException;
import com.linktreeclone.api.model.ERole;
import com.linktreeclone.api.model.Role;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.request.AuthRequest;
import com.linktreeclone.api.payload.request.RegisterRequest;
import com.linktreeclone.api.payload.response.ApiResponse;
import com.linktreeclone.api.payload.response.MessageResponse;
import com.linktreeclone.api.payload.response.UserResponse;
import com.linktreeclone.api.repository.RoleRepository;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.security.jwt.JwtUtils;
import com.linktreeclone.api.security.service.UserDetailsImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserResponse>> me(
		@Valid @NotNull @RequestHeader("Authorization") String bearerToken
	) throws RuntimeException {
		String token = bearerToken.substring(7);

		boolean isValid = jwtUtils.validateJwtToken(token);

		if (isValid) {
			String username = jwtUtils.getUsernameFromJwtToken(token);
			Optional<User> existingUser = userRepository.findByUsername(username);
			if (existingUser.isPresent()) {
				User user = existingUser.get();
				List<String> roles = user.getRoles()
					.stream()
					.map(role -> role.getName().toString())
					.toList();
				return new ResponseEntity<ApiResponse<UserResponse>>(
					new ApiResponse<UserResponse>(
						new UserResponse(
							user.getId(), 
							user.getUsername(),
							user.getEmail(),
							roles
						), 
						null
					), 
					HttpStatus.OK
				);
			} else {
				throw new NotFoundException("User not found!", "User cannot be found with given jwt token!");
			}
		}
		 
		throw new RuntimeException("Invalid token!");
	}

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(@Valid @RequestBody AuthRequest loginRequest) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(auth);

		// String jwt = jwtUtils.generateJwtToken(auth);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok()
		.header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
		.body(new ApiResponse<UserResponse>(
			new UserResponse(
				userDetails.getId(), 
				userDetails.getUsername(), 
				userDetails.getEmail(), 
				roles
			), null
		));

		// return new ResponseEntity<ApiResponse<JwtResponse>>(
		// 	new ApiResponse<JwtResponse>(
		// 		new JwtResponse(
		// 			jwt, 
		// 			userDetails.getId(), 
		// 			userDetails.getUsername(), 
		// 			userDetails.getEmail(), 
		// 			roles
		// 		), null
		// 	),
		// 	HttpStatus.OK
		// );
    }

    @PostMapping("/register")
	public ResponseEntity<ApiResponse<MessageResponse>> registerUser(
		@Valid @RequestBody RegisterRequest registerRequest
	) throws CredentialsTakenException {
		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			throw new CredentialsTakenException("Credentials taken!", "Username is already taken!");
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new CredentialsTakenException("Credentials taken!", "Email is already taken!");
		}
		
		// Create new user's account
		User user = new User(
            registerRequest.getUsername(), 
			registerRequest.getEmail(),
			passwordEncoder.encode(registerRequest.getPassword()));

		Set<String> strRoles = registerRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<ApiResponse<MessageResponse>>(
			new ApiResponse<MessageResponse>(
				new MessageResponse("User registered successfully!"),
				null
			),
			HttpStatus.OK
		);
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<MessageResponse>> logout() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok()
		.header(HttpHeaders.SET_COOKIE, cookie.toString())
		.body(new ApiResponse<MessageResponse>(
			new MessageResponse("You've been loggged out!"), 
			null
		));
	}
}
