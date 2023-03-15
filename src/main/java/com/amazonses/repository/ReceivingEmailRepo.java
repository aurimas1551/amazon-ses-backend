package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.ReceivingEmail;

@Repository
public interface ReceivingEmailRepo extends JpaRepository<ReceivingEmail, Integer>{

	ReceivingEmail findByReceivingEmail(String receivingEmail);

}
