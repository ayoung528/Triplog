package com.ssafy.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.repository.UserRepository;
import com.ssafy.trip.service.EmailValidationService;

@CrossOrigin(origins = "*")
@Controller
public class EmailValidationController {
	@Autowired
    UserRepository userRepository;
	
	@Autowired
    EmailValidationService emailValidationService;
	
	@RequestMapping("/emailValid/auth/{email}")
	public String validationPage(@PathVariable(value = "email") String email) {
        MemberUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        user.setValid(true);
        userRepository.save(user);
        return "redirect:http://i3b207.p.ssafy.io/emailauth/success";
    }
	
	@RequestMapping("/api/emailValid/re/{num}")
	public ResponseEntity sendMail(@PathVariable(value = "num") Long num) {
		try {
			MemberUser user = userRepository.findByNum(num)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "num", num));
	        
	        emailValidationService.sendEmail(user.getEmail());
	        return new ResponseEntity<>("success",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("fail",HttpStatus.BAD_REQUEST);
		}
        
    }
}
