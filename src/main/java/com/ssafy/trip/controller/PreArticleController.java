package com.ssafy.trip.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.model.PreArticle;
import com.ssafy.trip.repository.PreArticleRepository;
import com.ssafy.trip.repository.UserRepository;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@CrossOrigin(origins = "*")
@RequestMapping("/api/chatbot")
@RestController
public class PreArticleController {
	private static final String SUCCESS = "success";
	
	@Autowired
	private PreArticleRepository preArticleRepository;

	@Autowired
	private UserRepository userRepository;

	private int status = 1;
	private String tmp = "";
	@RequestMapping(value = "/kakao", method = { RequestMethod.POST, RequestMethod.GET }, headers = {
			"Accept=application/json" })
	public HashMap<String, Object> callAPI(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, PreArticle preArticle) {
		
		HashMap<String, Object> resultJson = new HashMap<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(params);

			/* ?????? ?????? ?????? * */
			HashMap<String, Object> userRequest = (HashMap<String, Object>) params.get("userRequest");
			
			

			String utter = userRequest.get("utterance").toString().replace("\n", "");

			String media = "";

			List<HashMap<String, Object>> outputs = new ArrayList<>();
			HashMap<String, Object> template = new HashMap<>();
			HashMap<String, Object> simpleText = new HashMap<>();
			HashMap<String, Object> text = new HashMap<>();
			
			HashMap<String, Object> user = (HashMap<String, Object>) userRequest.get("user");
			
			String userId = user.get("id").toString();
			Boolean existId = userRepository.existsByChatbotid(userId);
			
			if(existId) { // ?????? id???
				// ??????(?????????)??? ????????? ????????????
				if ((utter.length() > 4) && (utter.substring(0, 4).equals("http")) && status==1) {
//					?????????(??????) ??????????????? (status == 1)
					tmp = utter;
					status += 1;
					text.put("text","???????????? ?????????????????????");
					
				} else if(status == 2 &&  (!utter.substring(0,4).equals("http"))){
					
					preArticle.setMedia(tmp);
					preArticle.setComment(utter);
					
					MemberUser member = userRepository.findByChatbotid(userId);
//					????????????
					preArticle.setUsernum(member.getNum());

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar cal = Calendar.getInstance();
					String today = null;
					today = formatter.format(cal.getTime());
					Timestamp ts = Timestamp.valueOf(today);
//						????????????
					preArticle.setDate(ts);

					preArticleRepository.save(preArticle);
					status = 1;
					text.put("text"," ?????????????????????.");
				}else {
					text.put("text", "???????????? ???????????? ?????????????????????.");
				}
		
			}else {
				text.put("text","???????????? ?????? ??????????????????.");
			}
			
			
			
			simpleText.put("simpleText", text);
			outputs.add(simpleText);
			template.put("outputs", outputs);
			resultJson.put("version", "2.0");
			resultJson.put("template", template);

		} catch (Exception e) {

		}

		return resultJson;
	}

	@RequestMapping(value = "/email", method = { RequestMethod.POST, RequestMethod.GET }, headers = {
			"Accept=application/json" })
	public HashMap<String, Object> emailvalid(@RequestBody Map<String, Object> params, HttpServletRequest request,
			HttpServletResponse response, PreArticle preArticle) {
		HashMap<String, Object> resultJson = new HashMap<>();

		try {
			List<HashMap<String, Object>> outputs = new ArrayList<>();
			HashMap<String, Object> template = new HashMap<>();
			HashMap<String, Object> simpleText = new HashMap<>();
			HashMap<String, Object> text = new HashMap<>();
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(params);
			/* ?????? ?????? ?????? * */

			HashMap<String, Object> userRequest = (HashMap<String, Object>) params.get("userRequest");

			HashMap<String, Object> user = (HashMap<String, Object>) userRequest.get("user");

			String userEmail = userRequest.get("utterance").toString().replace("\n", "");
			String userId = user.get("id").toString().replace("\n", "");
			Boolean existId = userRepository.existsByChatbotid(userId);
			Boolean existEmail  = userRepository.existsByEmail(userEmail);
			
			if (existId) {
				text.put("text","?????? ???????????? ????????????");
				
			}else {
				MemberUser memberuser = userRepository.findByEmail(userEmail)
						.orElseThrow(() -> new ResourceNotFoundException("MemberUser", "email", userEmail));

				memberuser.setChatbotId(userId);
				userRepository.save(memberuser);
				String CompleteMsg = "?????????????????????!";

				text.put("text", CompleteMsg);
			}
			

			

//	            text.put("text",utter);
			simpleText.put("simpleText", text);
			outputs.add(simpleText);
			template.put("outputs", outputs);
			resultJson.put("version", "2.0");
			resultJson.put("template", template);

		} catch (Exception e) {

		}
		return resultJson;

	}
	@GetMapping("/{usernum}")
	public List<PreArticle> getPreArticles(@PathVariable(value="usernum") Long usernum){
		
		List<PreArticle> preArticles = preArticleRepository.findByUsernum(usernum);
		
		return preArticles;
	}
	@DeleteMapping("/{num}")
	public ResponseEntity<String> deleteChatbotCard(@PathVariable(value="num") Long num){
		PreArticle preArticle = preArticleRepository.findByNum(num);
		preArticleRepository.delete(preArticle);
		
		return ResponseEntity.ok(SUCCESS);
	}
//	??????????????? ?????????????????? ????????????

	
}
