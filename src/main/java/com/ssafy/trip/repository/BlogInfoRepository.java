package com.ssafy.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.BlogInfo;

@Repository
public interface BlogInfoRepository extends JpaRepository<BlogInfo, Long> {
	Boolean existsByUsernum(Long usernum);
	BlogInfo findByUsernum(Long usernum);

	BlogInfo findByNum(Long num);
	
	@Transactional
    @Modifying
	void deleteByUsernum(Long user_num);
}
