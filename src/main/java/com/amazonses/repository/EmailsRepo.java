package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.Emails;

@Repository
public interface EmailsRepo extends JpaRepository<Emails, Integer>{
	
	Emails findByMessageId(String messageId);

}
