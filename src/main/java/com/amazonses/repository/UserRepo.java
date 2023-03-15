package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, String>{
	
	User findByUserId(String userId);

}
