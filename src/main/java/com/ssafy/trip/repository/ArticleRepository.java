package com.ssafy.trip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.Article;
import com.ssafy.trip.model.MemberUser;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
	Optional<Article> findByNum(Long num);
	Article findByNumAndNumNotNull(Long num);
//	블로그에서 검색기능
	List<Article> findByTitleContaining(String keyword);
	List<Article> findByTitleContainingAndNum(String keyword, int user_num);
	List<Article> findByUserNum(Long user_num);
	List<Article> findByTitleContainingOrUserNum(String keyword, int user_num);
	List<Article> findByUserNicknameContaining(String keyword);
	@Query(nativeQuery = true, value = "select * from article where user_num = :user_num order by num desc limit :limit, 10")
	List<Article> findByUsernumPaging(@Param("user_num") Long user_num, @Param("limit") int limit);
	List<Article> findByTrippackageNum(@Param("trippackageNum") Long trippackageNum);
	List<Article> findByUserNumAndTrippackageNumIsNull(Long user_num);
	@Transactional
	@Modifying
	@Query("update Article set trippackage_num = :tripNum where num = :articleNum")
	void updateTripPackage(@Param("tripNum") Long tripNum, @Param("articleNum") Long articleNum);
	
	List<Article> findAll();
	@Transactional
    @Modifying
	void deleteByNum(Long num);
	
	@Transactional
    @Modifying
	void deleteAllByUserNum(Long usernum);
	List<Article> findByLikearticle(MemberUser user);
	
	List<Article> findTop3ByOrderByLikeCountDesc();
	List<Article>findTop3ByOrderByViewsDesc();
	Boolean existsByNum(Long num);
	List<Article> findTop3ByOrderByNumDesc();
}
