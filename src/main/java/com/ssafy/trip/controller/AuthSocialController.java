package com.ssafy.trip.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.help.JwtAuthenticationResult;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.repository.UserRepository;
import com.ssafy.trip.security.JwtTokenProvider;
import com.ssafy.trip.service.AuthSocialService;

@RestController
@RequestMapping("/api/social")
public class AuthSocialController {
	
	public static final Logger logger = LoggerFactory.getLogger(AuthSocialController.class);
	@Autowired
	private AuthSocialService authSocial;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;
	
	@GetMapping("/kakao/code")
	public ModelAndView kakaoLogin(@RequestParam("code") String code , @RequestParam("state") String state) {

	    String url = "http://i3b207.p.ssafy.io/login/social/kakao/?code=" + code + "&state=" + state;
	    return new ModelAndView("redirect:" + url);
	}
	@GetMapping("/google/code")
	public ModelAndView googleLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
	    


	    String url = "http://i3b207.p.ssafy.io/login/social/google/?code=" + code + "&state=" + state;
	    return new ModelAndView("redirect:" + url);
	}
    @GetMapping("/google/login")
	public ResponseEntity<?> GooleLogin(@RequestParam("code") String code, HttpServletRequest request ) {
	    
	    String access_Token = authSocial.getGoogleAccessToken(code);
	    
	    
	    
	    HashMap<String, Object> userInfo = authSocial.getGoogleUserInfo(access_Token);
	    
	    
	    
	    if (userRepository.existsByEmail((String)userInfo.get("email"))) {

	    	MemberUser user = userRepository.findByEmail((String)userInfo.get("email"))
	    			.orElseThrow(() -> new ResourceNotFoundException("User", "email",(String)userInfo.get("email")));
	    	
	    	if (user.isValid() == false) {
	    	user.setValid(true);
	    	userRepository.save(user);}
	    	
	    	HttpSession session = request.getSession();
	    	session.setAttribute((String)userInfo.get("email"), access_Token);
			
			HashMap<String, Object> obj = new HashMap<>();
			obj.put("token", (Object)new JwtAuthenticationResult(access_Token));

			obj.put("email", userInfo.get("email"));
	    	return  ResponseEntity.ok(obj);
	    }
	   
	    return new ResponseEntity<>(userInfo,HttpStatus.OK);
	}

	
    @GetMapping("/kakao/login")
	public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request ) {
	    
	    String access_Token = authSocial.getKakaoAccessToken(code);
	    
	    
	    
	    HashMap<String, Object> userInfo = authSocial.getKakaoUserInfo(access_Token);
	    
	    
	    if (userRepository.existsByEmail((String)userInfo.get("email"))) {

	    	MemberUser user = userRepository.findByEmail((String)userInfo.get("email"))
	    			.orElseThrow(() -> new ResourceNotFoundException("User", "email",(String)userInfo.get("email")));
	    	HttpSession session = request.getSession();
	    	session.setAttribute((String)userInfo.get("email"), access_Token);
			
			HashMap<String, Object> obj = new HashMap<>();
			obj.put("token", (Object)new JwtAuthenticationResult(access_Token));

			obj.put("email", userInfo.get("email"));
	    	return  ResponseEntity.ok(obj);
	    }
	   
	    return new ResponseEntity<>(userInfo,HttpStatus.OK);
	}

	
}

