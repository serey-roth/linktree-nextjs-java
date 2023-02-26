package com.linktreeclone.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.linktreeclone.api.exception.CredentialsTakenException;
import com.linktreeclone.api.model.ERole;
import com.linktreeclone.api.model.Role;
import com.linktreeclone.api.model.User;
import com.linktreeclone.api.payload.request.AuthRequest;
import com.linktreeclone.api.payload.request.RegisterRequest;
import com.linktreeclone.api.payload.response.ApiResponse;
import com.linktreeclone.api.payload.response.JwtResponse;
import com.linktreeclone.api.payload.response.MessageResponse;
import com.linktreeclone.api.repository.RoleRepository;
import com.linktreeclone.api.repository.UserRepository;
import com.linktreeclone.api.security.jwt.JwtUtils;
import com.linktreeclone.api.security.service.UserDetailsImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
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

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody AuthRequest loginRequest) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), 
                loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generateJwtToken(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new ResponseEntity<ApiResponse>(
			new ApiResponse(
				new JwtResponse(
					jwt, 
					userDetails.getId(), 
					userDetails.getUsername(), 
					userDetails.getEmail(), 
					roles
            	), null
			),
			HttpStatus.OK
		);
    }

    @PostMapping("/register")
	public ResponseEntity<MessageResponse> registerUser(
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

		return new ResponseEntity<MessageResponse>(
			new MessageResponse("User registered successfully!"),
			HttpStatus.OK
		);
	}

}
