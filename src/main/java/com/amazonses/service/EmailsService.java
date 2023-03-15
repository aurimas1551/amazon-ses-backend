package com.amazonses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonses.model.Emails;
import com.amazonses.repository.EmailsRepo;

@Service
public class EmailsService {

	@Autowired
	private EmailsRepo repository;

	public Emails saveEmail(Emails emails) {
		return repository.save(emails);
	}

	public Emails getEmail(int id) {
		return repository.findById(id).orElse(null);
	}

	public List<Emails> getEmails() {
		return repository.findAll();
	}

	public String deleteEmail(int id) {
		repository.deleteById(id);
		return "deleted project" + id;
	}

	public Emails getEmailByMessageId(String messageId) {
		return repository.findByMessageId(messageId);
	}

}
