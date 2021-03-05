package com.ssafy.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.TripPackage;

@Repository
public interface TripPackageRepository extends JpaRepository<TripPackage, Long>{
	List<TripPackage> findByUserNum(Long num);
	
	@Transactional
    @Modifying
	void deleteByNum(Long num);
}
