package com.amazonses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonses.model.DefaultEmail;
import com.amazonses.repository.DefaultEmailRepo;

@Service
public class DefaultEmailService {

	@Autowired
	private DefaultEmailRepo repository;

	public DefaultEmail saveDefaultEmail(DefaultEmail defaultEmail) {
		return repository.save(defaultEmail);
	}

	public List<DefaultEmail> getDefaultEmails() {
		return repository.findAll();
	}

}
