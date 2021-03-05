package com.ssafy.trip.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.trip.exception.ResourceNotFoundException;
import com.ssafy.trip.help.ArticleLikeListResponseObject;
import com.ssafy.trip.model.Article;
import com.ssafy.trip.model.Comment;
import com.ssafy.trip.model.MemberUser;
import com.ssafy.trip.model.Paging;
import com.ssafy.trip.repository.ArticleRepository;
import com.ssafy.trip.repository.CommentRepository;
import com.ssafy.trip.repository.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/article")
public class ArticleController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	
	@GetMapping("/update/{num}")
	public ResponseEntity<Article> getArticleForUpdate(@PathVariable(value = "num") Long num) {
		Article article = articleRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "num", num));
		
		return ResponseEntity.ok(article);
	}
	
	@GetMapping("/{num}/{usernum}")
	public ResponseEntity<Article> getArticleByNum(@PathVariable(value = "num") Long num,
			@PathVariable(value = "usernum") Long usernum) {
		Article article = articleRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "num", num));

		if (article.getViews() == null) {
			article.setViews((long) 0);
		}
		if (article.getUser_num() != usernum && usernum != 0) {

			article.setViews(article.getViews() + 1);

			articleRepository.save(article);
		}

		return ResponseEntity.ok(article);
	}

	@DeleteMapping("/{num}")
	public ResponseEntity<String> deleteArticleByNum(@PathVariable(value = "num") Long num) {

		commentRepository.deleteAllByArticlenumAndReplyIsNull(num);
		commentRepository.deleteAllByArticlenum(num);
		articleRepository.deleteByNum(num);
		return ResponseEntity.ok(SUCCESS);
	}

	@PutMapping("/{num}")
	public ResponseEntity<String> modifyArticleByNum(@PathVariable(value = "num") Long num,
			@RequestBody Article article) {
		articleRepository.save(article);

		return ResponseEntity.ok(SUCCESS);
	}

	@PostMapping
	public ResponseEntity<String> postArticle(@RequestBody Article article) {
		articleRepository.save(article);

		return ResponseEntity.ok(SUCCESS);
	}

	@PostMapping("/files")
	public ResponseEntity<List<String>> uploadFiles(@RequestPart List<MultipartFile> files) throws Exception {
		String workspacePath = System.getProperty("user.dir");
		String[] pathSplited = workspacePath.split("/");
		if (pathSplited[pathSplited.length - 1].equals("target")) {
			String newPath = pathSplited[0];
			for (int i = 1; i < pathSplited.length - 1; i++) {
				newPath += "/" + pathSplited[i];
			}
			workspacePath = newPath;
		}
		String contentBaseDir = workspacePath + "/frontend/public/content/registered/";
		String imgPublicBaseDir = workspacePath + "/frontend/public/articleImage/";
		List<String> result = new LinkedList<String>();

		for (MultipartFile file : files) {
			String originalFileName = file.getOriginalFilename();

			String[] splited = originalFileName.split("\\.");
			String realName = splited[0];
			String extension = splited[splited.length - 1];
			String baseDir;
			if (extension.equals("html")) {
				baseDir = contentBaseDir;
				String newName = realName + "." + extension;
				File dest = new File(baseDir + newName);

				int index = 0;
				while (dest.exists()) {
					index++;
					newName = realName + "(" + index + ")." + extension;
					dest = new File(baseDir + newName);
				}
				System.out.println(baseDir + newName);
				file.transferTo(dest);
				result.add(newName);
			} else {
				baseDir = imgPublicBaseDir;
				String newName = realName + "." + extension;
				File dest = new File(baseDir + newName);

				int index = 0;
				while (dest.exists()) {
					index++;
					newName = realName + "(" + index + ")." + extension;
					dest = new File(baseDir + newName);
				}

				file.transferTo(dest);
				result.add(newName);
			}

		}

		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/tempFiles")
	public ResponseEntity<List<String>> uploadTempFiles(@RequestPart List<MultipartFile> files) throws Exception {
		String workspacePath = System.getProperty("user.dir");
		String[] pathSplited = workspacePath.split("/");
		if (pathSplited[pathSplited.length - 1].equals("target")) {
			String newPath = pathSplited[0];
			for (int i = 1; i < pathSplited.length - 1; i++) {
				newPath += "/" + pathSplited[i];
			}
			workspacePath = newPath;
		}
		String contentBaseDir = workspacePath + "/frontend/public/content/temp/";
		String imgPublicBaseDir = workspacePath + "/frontend/public/articleImage/";
		List<String> result = new LinkedList<String>();

		for (MultipartFile file : files) {
			String originalFileName = file.getOriginalFilename();

			String[] splited = originalFileName.split("\\.");
			String realName = splited[0];
			String extension = splited[splited.length - 1];
			String baseDir;
			if (extension.equals("html")) {
				baseDir = contentBaseDir;
				String newName = realName + "." + extension;
				File dest = new File(baseDir + newName);

				int index = 0;
				while (dest.exists()) {
					index++;
					newName = realName + "(" + index + ")." + extension;
					dest = new File(baseDir + newName);
				}
				System.out.println(baseDir + newName);
				file.transferTo(dest);
				result.add(newName);
			} else {
				baseDir = imgPublicBaseDir;
				String newName = realName + "." + extension;
				File dest = new File(baseDir + newName);

				int index = 0;
				while (dest.exists()) {
					index++;
					newName = realName + "(" + index + ")." + extension;
					dest = new File(baseDir + newName);
				}

				file.transferTo(dest);
				result.add(newName);
			}

		}

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getList/{hostNum}")
	public List<Article> findArticlesByHostNum(@PathVariable(value = "hostNum") Long hostNum) {
		List<Article> list = articleRepository.findByUserNum(hostNum);
		return list;
	}

	@PostMapping("/getList")
	public List<Article> findArticlesByPaging(@RequestBody Paging paging) {
		List<Article> list = articleRepository.findByUsernumPaging(paging.getUsernum(), paging.getLimit());
		return list;
	}

	@GetMapping("/searchArticle/{keyword}")
	public List<Article> searchArticle(@PathVariable(value = "keyword") String keyword) {
		List<Article> searchArticle = articleRepository.findByTitleContaining(keyword);

		return searchArticle;
	}

	// 좋아요 기능 -남시성

	@PostMapping("/likelist")
	public List<ArticleLikeListResponseObject> findArticleLikeList(@RequestBody Paging paging) {
		MemberUser user = userRepository.findByNum(paging.getUsernum())
				.orElseThrow(() -> new ResourceNotFoundException("User", "usernum", paging.getUsernum()));

		List<Article> articles = articleRepository.findByLikearticle(user);
		List<ArticleLikeListResponseObject> objs = new ArrayList<ArticleLikeListResponseObject>();

		MemberUser writer = null;
		for (Article article : articles) {
			writer = userRepository.findByNum(article.getUser_num())
					.orElseThrow(() -> new ResourceNotFoundException("User", "num", article.getUser_num()));

			objs.add(new ArticleLikeListResponseObject(article, writer));
		}

		if (paging.getLimit() > objs.size())
			return null;
		int max = paging.getLimit() + 9;
		if (max > objs.size())
			max = objs.size();
		List<ArticleLikeListResponseObject> list = objs.subList(paging.getLimit(), max);

		return list;
	}

	@DeleteMapping("/likelist/{usernum}/{num}")
	public ResponseEntity<String> DeleteArticleLikeList(@PathVariable(value = "usernum") Long usernum,
			@PathVariable(value = "num") Long num) {
		MemberUser user = userRepository.findByNum(usernum)
				.orElseThrow(() -> new ResourceNotFoundException("User", "usernum", usernum));
		Article article = articleRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "num", num));

		List<MemberUser> users = article.getLikearticle();
		article.setLikeCount(article.getLikeCount() - 1);
		users.remove(user);

		article.setLikearticle(users);
		articleRepository.save(article);

		return ResponseEntity.ok(SUCCESS);
	}

	@PutMapping("/likelist/{usernum}/{num}")
	public ResponseEntity<String> UpdateArticleLikeList(@PathVariable(value = "usernum") Long usernum,
			@PathVariable(value = "num") Long num) {
		MemberUser user = userRepository.findByNum(usernum)
				.orElseThrow(() -> new ResourceNotFoundException("User", "usernum", usernum));
		Article article = articleRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "num", num));

		List<MemberUser> users = article.getLikearticle();
		article.setLikeCount(article.getLikeCount() + 1);
		users.add(user);

		article.setLikearticle(users);
		articleRepository.save(article);

		return ResponseEntity.ok(SUCCESS);
	}
	// 좋아요 기능 - 남시성

	@GetMapping("/like/{articleNum}/{userNum}")
	public ResponseEntity<Boolean> getIsLike(@PathVariable(value = "userNum") Long userNum,
			@PathVariable(value = "articleNum") Long articleNum) {
		MemberUser user = userRepository.findByNum(userNum)
				.orElseThrow(() -> new ResourceNotFoundException("User", "num", userNum));

		Boolean isLike = false;
		List<Article> articles = articleRepository.findByLikearticle(user);
		for (Article article : articles) {
			if (article.getNum() == articleNum)
				isLike = true;
		}

		return ResponseEntity.ok(isLike);
	}

	@PutMapping("/{num}/{userNum}/{flag}")
	public ResponseEntity<String> modifyLikeInfoInArticle(@PathVariable(value = "userNum") Long userNum,
			@PathVariable(value = "num") Long num, @PathVariable(value = "flag") boolean flag) {

		Article article = articleRepository.findByNum(num)
				.orElseThrow(() -> new ResourceNotFoundException("Article", "num", num));

		MemberUser user = userRepository.findByNum(userNum)
				.orElseThrow(() -> new ResourceNotFoundException("User", "num", userNum));

		List<MemberUser> users = article.getLikearticle();

		if (flag)
			users.add(user);
		else
			users.remove(user);
		if (flag) {
			article.setLikeCount(article.getLikeCount() + 1);
		} else {
			article.setLikeCount(article.getLikeCount() - 1);
		}
		article.setLikearticle(users);
		articleRepository.save(article);

		return ResponseEntity.ok(SUCCESS);
	}

	@PostMapping("/uploadWithFile")
	public String registArticleByFile(@RequestBody MultipartFile img) throws IOException {
		String imgName = img.getOriginalFilename();
		Article article = new Article();
		article.setThumbnail(imgName);

		File url = new File("images/" + imgName);
		url.createNewFile();
		FileOutputStream fout = new FileOutputStream(url);
		fout.write(img.getBytes());
		fout.close();
		return "ok";
	}

	@GetMapping("/likesort")
	public List<Article> getLikeSortedListArticle() {
		List<Article> list = articleRepository.findTop3ByOrderByLikeCountDesc();

		return list;
	}

	@GetMapping("/viewsort")
	public List<Article> getViewSortedListArticle() {
		List<Article> list = articleRepository.findTop3ByOrderByViewsDesc();
		return list;
	}

	@GetMapping("tripPackage/{trippackageNum}")
	public List<Article> getTripPackage(@PathVariable(value = "trippackageNum") Long trippackageNum) {
		List<Article> list = articleRepository.findByTrippackageNum(trippackageNum);

		return list;
	}

	@GetMapping("tripPackage/default/{userNum}")
	public List<Article> getTripPackageDefault(@PathVariable(value = "userNum") Long userNum) {
		List<Article> list = articleRepository.findByUserNumAndTrippackageNumIsNull(userNum);

		return list;
	}

	@PutMapping("tripPackage/{tripNum}/{articleNum}")
	public ResponseEntity<String> updateTripPackage(@PathVariable(value = "tripNum") Long tripNum,
			@PathVariable(value = "articleNum") Long articleNum) {
		if (tripNum == 0)
			articleRepository.updateTripPackage(null, articleNum);
		else
			articleRepository.updateTripPackage(tripNum, articleNum);

		return ResponseEntity.ok(SUCCESS);
	}

	@GetMapping("/commentsort")
	public List getCommentsortedListArticle() {
		List<Long> commentLength = new ArrayList<>();
		List<Long> articleNumList = new ArrayList<>();

		List<Article> list = articleRepository.findAll();
		for (long i = 0; i < list.size(); i++) {
			articleNumList.add(list.get((int) i).getNum());
		}

		List<Long> comment = new ArrayList<>();
		for (long i = 0; i < articleNumList.size(); i++) {

			commentLength.add((long) commentRepository.findByArticlenum(articleNumList.get((int) i)).size());

		}

		for (long i = 0; i < list.size() - 1; i++) {
			for (long j = 1; j < list.size() - i; j++) {
				if (commentLength.get((int) j - 1) < commentLength.get((int) j)) {

					long tmp = commentLength.get((int) (j - 1));
					commentLength.set((int) (j - 1), commentLength.get((int) j));
					commentLength.set((int) j, tmp);
					
					long tmp2 = articleNumList.get((int) j - 1);
					articleNumList.set((int) (j - 1), articleNumList.get((int) j));
					articleNumList.set((int) j, tmp2);
				}
			}
		
		}
		
		List commentSort = new ArrayList<>();
		for (long i = 0; i < 3; i++) {
			Article article = articleRepository.findByNumAndNumNotNull(articleNumList.get((int) i));

			commentSort.add(article);
			commentSort.add(commentLength.get((int) i));
		}

		return commentSort;
	}

	@GetMapping("/recentSort")
	public List<Article> recentArticle() {
		List<Article> recent = articleRepository.findTop3ByOrderByNumDesc();

		return recent;
	}

}
