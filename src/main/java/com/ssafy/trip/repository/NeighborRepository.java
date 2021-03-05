package com.ssafy.trip.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.Neighbor;

@Repository
public interface NeighborRepository extends JpaRepository<Neighbor, Long>{
	@Transactional
    @Modifying
	@Query(nativeQuery = true, value = "insert into neighbor (user_num, neighbor_num,neighbor_nickname)\r\n" + 
			"value (:user_num, :neighbor_num, (select nickname from users where num = :neighbor_num))")
	void findByUsernumPaging(@Param("user_num") Long user_num, @Param("neighbor_num") Long neighbor_num);
	
	List<Neighbor> findByUserNumOrderByNeighborNum(Long user_num);
	
	@Transactional
    @Modifying
	void deleteByUserNumAndNeighborNum(Long user_num, Long neighbor_num);
	
	@Transactional
    @Modifying
	void deleteByUserNum(Long user_num);
	
	@Transactional
    @Modifying
	void deleteByNeighborNum(Long neighbor_num);
}
