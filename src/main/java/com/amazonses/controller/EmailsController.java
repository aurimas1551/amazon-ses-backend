package com.amazonses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonses.model.Emails;
import com.amazonses.service.EmailsService;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class EmailsController {

	@Autowired
	private EmailsService emailsService;

	@PostMapping("email")
	public Emails addEmail(@RequestBody Emails emails) {
		return emailsService.saveEmail(emails);
	}

	@GetMapping("email/{id}")
	public Emails findEmail(@PathVariable int id) {
		return emailsService.getEmail(id);
	}

	@GetMapping("emails")
	public List<Emails> findEmails() {
		return emailsService.getEmails();
	}

	@DeleteMapping("email/{id}")
	public String deleteEmail(@PathVariable int id) {
		return emailsService.deleteEmail(id);
	}

	@PostMapping("email/{messageId}")
	public Emails updateEmailStatus(@PathVariable String messageId, @RequestBody String status) {
		Emails email = emailsService.getEmailByMessageId(messageId);
		email.setStatus(status);
		return emailsService.saveEmail(email);
	}

}
