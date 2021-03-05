package com.ssafy.trip.controller;

import java.net.URI;
import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssafy.trip.exception.AppException;
import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.help.ApiResult;
import com.ssafy.trip.help.AuthSearchRequest;
import com.ssafy.trip.help.AuthSearchUpdateRequest;
import com.ssafy.trip.help.JwtAuthenticationResult;
import com.ssafy.trip.help.LoginRequest;
import com.ssafy.trip.help.SignUpRequest;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.model.Role;
import com.ssafy.trip.model.RoleName;
import com.ssafy.trip.repository.RoleRepository;
import com.ssafy.trip.repository.UserRepository;
import com.ssafy.trip.security.JwtTokenProvider;
import com.ssafy.trip.service.EmailValidationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	EmailValidationService emailValidationService;

	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		logger.info("1-------------authenticateUser-----------------------------" + loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		System.out.println(new JwtAuthenticationResult(jwt));
		return ResponseEntity.ok(new JwtAuthenticationResult(jwt));
	}	
	
	
	@PostMapping("/authsearch")
	public ResponseEntity<?> authsearch(@Valid @RequestBody AuthSearchRequest searchRequest) {
		if (!userRepository.existsByEmail(searchRequest.getEmail())){
			return new ResponseEntity(new ApiResult(false, "해당 사용자 정보가 잘못되었거나 존재하지 않습니다."), HttpStatus.ACCEPTED);
		}else {
			MemberUser user = userRepository.findByEmail(searchRequest.getEmail())
	                .orElseThrow(() -> new ResourceNotFoundException("User", "email", searchRequest.getEmail()));

			if (!searchRequest.getName().equals(user.getName())) {
				

				return new ResponseEntity(new ApiResult(false, "해당 사용자 정보가 잘못되었거나 존재하지 않습니다."), HttpStatus.ACCEPTED);
				
			} else {

				emailValidationService.sendSearchEmail(user.getEmail(),user.getPassword());

				return new ResponseEntity(new ApiResult(true, "해당 이메일로 비밀번호 변경 링크가 발송되었습니다."), HttpStatus.ACCEPTED);
			}
		}
	}
	@PostMapping("/authsearch/update")
	public ResponseEntity<?> authsearchupdate(@Valid @RequestBody AuthSearchUpdateRequest searchRequest) {
		if( userRepository.existsByPassword(searchRequest.getCode())) {
			MemberUser user = userRepository.findByPassword(searchRequest.getCode());
			user.setPassword(passwordEncoder.encode(searchRequest.getPassword()));
			userRepository.save(user);

			return new ResponseEntity(new ApiResult(true, "비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요"), HttpStatus.ACCEPTED);
			
		} else {

			return new ResponseEntity(new ApiResult(false, "잘못된 접근입니다."), HttpStatus.ACCEPTED);
		}
	}
		

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
		logger.info("1-------------registerUser-----------------------------" + signUpRequest);
		if (userRepository.existsByNickname(signUpRequest.getNickname())) {
			return new ResponseEntity(new ApiResult(false, "사용자 별명이 이미 존재합니다!"), HttpStatus.ACCEPTED);
		}
		logger.info("2-------------registerUser-----------------------------" + signUpRequest);
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResult(false, "이메일이 이미 존재합니다!"), HttpStatus.ACCEPTED);
		}
		logger.info("3-------------registerUser-----------------------------" + signUpRequest);
		// Creating user's account
		MemberUser user = new MemberUser(null, signUpRequest.getName(), signUpRequest.getNickname(),
				signUpRequest.getEmail(), signUpRequest.getPassword(), null, null, null, 0, false, null);
		logger.info("4-------------registerUser-----------------------------" + user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));
		// logger.info("4-------------registerUser-----------------------------"+userRole);
		user.setRoles(Collections.singleton(userRole));
		
		if (signUpRequest.getValid() == true) {
			user.setValid(true);
		}
		
		MemberUser result = userRepository.save(user);
		// logger.info("5-------------registerUser-----------------------------"+result);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getNickname()).toUri();

		emailValidationService.sendEmail(signUpRequest.getEmail());

		return ResponseEntity.created(location).body(new ApiResult(true, "성공적으로 등록되었습니다."));
	}
}
