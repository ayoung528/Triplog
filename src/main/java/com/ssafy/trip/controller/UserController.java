package com.ssafy.trip.controller;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.help.UserIdentityAvailability;
import com.ssafy.trip.help.UserProfile;
import com.ssafy.trip.model.Article;
import com.ssafy.trip.model.BlogInfo;
import com.ssafy.trip.model.Comment;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.repository.ArticleRepository;
import com.ssafy.trip.repository.BlogInfoRepository;
import com.ssafy.trip.repository.CommentRepository;
import com.ssafy.trip.repository.NeighborRepository;
import com.ssafy.trip.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private BlogInfoRepository blogInfoRepository;
    
    @Autowired
    private NeighborRepository neighborRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByNickname(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/users/{email}")
    public UserProfile getUserProfile(@PathVariable(value = "email") String email) {
        MemberUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        UserProfile userProfile = new UserProfile(user.getNum(), user.getEmail(), user.getNickname(), user.getName(), user.getCreatedAt(), user.getImageSrc(), user.getIntro(), user.isValid());
        return userProfile;
    }
    @GetMapping("/users/get/{num}")
    public UserProfile getUserProfileByNum(@PathVariable(value = "num") Long num) {
        MemberUser user = userRepository.findByNum(num)
                .orElseThrow(() -> new ResourceNotFoundException("User", "num", num));

        UserProfile userProfile = new UserProfile(user.getNum(), user.getEmail(), user.getNickname(), user.getName(), user.getCreatedAt(), user.getImageSrc(), user.getIntro(), user.isValid());
        return userProfile;
    }

    @PutMapping("/users/{num}")
    public ResponseEntity<String> modifyUserProfile(@RequestBody UserProfile userProfile, @PathVariable(value = "num") Long num) {
    	MemberUser user = null;
    	List<Comment> comments = commentRepository.findByUsernum(num);
    	
    	String SUCCESS = "success";
        try {
        	user = userRepository.findByNum(num)
        			.orElseThrow(() -> new ResourceNotFoundException("User", "num", num));
        	user.setName(userProfile.getName());
        	user.setNickname(userProfile.getNickname());
        	user.setIntro(userProfile.getIntro());
        	user.setImageSrc(userProfile.getImagesrc());
        	for(Comment comment:comments) {
        		comment.setUserimg(userProfile.getImagesrc());
        		comment.setUsernickname(userProfile.getNickname());

        	}
        } catch (Exception e) {
        	return null;
        }
        MemberUser updateUser = userRepository.save(user); 
        
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
    
    @DeleteMapping("/users/delete/{num}")
    public ResponseEntity<?> deleteUser(@PathVariable("num") Long num) {
      try {

    	  commentRepository.deleteAllByUsernumAndReplyIsNotNull(num);
    	  
    	  List<Comment> comments = commentRepository.findByUsernum(num);
    	  
    	  for(Comment comment : comments) {
    			  commentRepository.deleteAllByReply(comment);
    			  commentRepository.delete(comment);
    	  }
    	  
    	  neighborRepository.deleteByUserNum(num);
    	  neighborRepository.deleteByNeighborNum(num);
    	  
    	  List<Article> articles = articleRepository.findByUserNum(num);
    	  for(Article article : articles) {
    		  Long articlenum = article.getNum();
    		  commentRepository.deleteAllByArticlenumAndReplyIsNotNull(articlenum);
    		  commentRepository.deleteAllByArticlenum(articlenum);

  				articleRepository.deleteByNum(articlenum);
    	  }
    	  MemberUser user = userRepository.findByNum(num)
        			.orElseThrow(() -> new ResourceNotFoundException("User", "num", num));
      	 
    	  articles = articleRepository.findByLikearticle(user);
    	  for(Article article : articles) {
    		  List<MemberUser> users = article.getLikearticle();
    			article.setLikeCount(article.getLikeCount()-1);
    			users.remove(user);
    			
    			article.setLikearticle(users);
    			articleRepository.save(article);
    	  }

    	  blogInfoRepository.deleteByUsernum(num);
    	  userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
      }
    }
    
    @GetMapping("/{user_num}/searchArticle/{keyword}")
    public List<Article> searchArticle(@PathVariable(value="keyword") String keyword) {
    	return null;
    }
    
    @PostMapping("/user/img")
	public ResponseEntity<String> uploadImgs(@RequestPart MultipartFile img) throws Exception {
    	String workspacePath = System.getProperty("user.dir");
		String[] pathSplited = workspacePath.split("/");
		if (pathSplited[pathSplited.length - 1].equals("target")) {
			String newPath = pathSplited[0];
			for (int i = 1; i < pathSplited.length - 1; i++) {
				newPath += "/" + pathSplited[i];
			}
			workspacePath = newPath;
		}
		String baseDir = workspacePath+ "/frontend/public/userImage/";
		String originalFileName = img.getOriginalFilename();
		File dest = new File(baseDir + originalFileName);
		
		String newName = originalFileName;
		String realName = originalFileName.split("\\.")[0];
		String extension = originalFileName.split("\\.")[1];
		int index = 0;
		while(dest.exists()) {
			index++;
			newName = realName + "(" + index + ")." + extension;
			dest = new File(baseDir + newName);
		}
		
		img.transferTo(dest);
	
		return ResponseEntity.ok(newName);
	}
    @GetMapping("/{num}/avatarimage")
    public String image(@PathVariable(value="num")Long usernum ) {
    	MemberUser user = userRepository.findByNum(usernum)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", usernum));
    	
    	String img = user.getImageSrc();
    	
    	return img;
    }
    @GetMapping("/{num}/nickname")
    public String getNickname(@PathVariable(value="num")Long usernum) {
    	MemberUser user = userRepository.findByNum(usernum)
    			.orElseThrow(() -> new ResourceNotFoundException("User", "email", usernum));
    	String nickname = user.getNickname();
    	return nickname;
    }
}
