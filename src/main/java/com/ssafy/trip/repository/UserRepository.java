package com.ssafy.trip.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.trip.model.MemberUser;

@Repository
public interface UserRepository extends JpaRepository<MemberUser, Long> {
    Optional<MemberUser> findByEmail(String email);

    Optional<MemberUser> findByNicknameOrEmail(String username, String email);
    
    Optional<MemberUser> findByNum(Long num);
    
    List<MemberUser> findByNumIn(List<Long> userIds);

    Optional<MemberUser> findByNickname(String username);
    
    Optional<MemberUser> findByName(String username);
    
    List<MemberUser> findByNicknameContaining(String username);
    Boolean existsByNickname(String username);

    Boolean existsByEmail(String email);

    MemberUser findByPassword(String password);
    

    Boolean existsByPassword(String password);
    
    Boolean existsByChatbotid(String chatbotId);
    
    
	MemberUser findByChatbotid(String chatbotId);

}
