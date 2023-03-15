package com.amazonses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonses.model.ReceivingEmail;
import com.amazonses.repository.ReceivingEmailRepo;

@Service
public class ReceivingEmailService {

	@Autowired
	private ReceivingEmailRepo repository;

	public ReceivingEmail saveReceivingEmail(ReceivingEmail receivingEmail) {
		return repository.save(receivingEmail);
	}

	public List<ReceivingEmail> saveReceivingEmails(List<ReceivingEmail> receivingEmails) {
		return repository.saveAll(receivingEmails);
	}

	public ReceivingEmail getReceivingEmail(int id) {
		return repository.findById(id).orElse(null);
	}

	public List<ReceivingEmail> getReceivingEmails() {
		return repository.findAll();
	}

	public String deleteReceivingEmail(int id) {
		repository.deleteById(id);
		return "deleted receiving email" + id;
	}

	public ReceivingEmail getReceivingEmailByEmail(String receivingEmail) {
		return repository.findByReceivingEmail(receivingEmail);
	}

}
