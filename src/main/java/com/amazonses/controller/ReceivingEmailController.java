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

import com.amazonses.model.ReceivingEmail;
import com.amazonses.service.ReceivingEmailService;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class ReceivingEmailController {

	@Autowired
	private ReceivingEmailService service;

	@PostMapping("receivingEmail")
	public ReceivingEmail addReceivingEmail(@RequestBody ReceivingEmail receivingEmail) {
		return service.saveReceivingEmail(receivingEmail);
	}

	@PostMapping("receivingEmails")
	public List<ReceivingEmail> addReceivingEmails(@RequestBody List<ReceivingEmail> receivingEmail) {
		return service.saveReceivingEmails(receivingEmail);
	}

	@GetMapping("receivingEmails")
	public List<ReceivingEmail> findAllReceivingEmails() {
		return service.getReceivingEmails();
	}

	@GetMapping("receivingEmail/{id}")
	public ReceivingEmail findReceivingEmailById(@PathVariable int id) {
		return service.getReceivingEmail(id);
	}

	@GetMapping("receivingEmail/name/{receivingEmail}")
	public ReceivingEmail findReceivingEmailById(@PathVariable String receivingEmail) {
		return service.getReceivingEmailByEmail(receivingEmail);
	}

	@DeleteMapping("receivingEmail/{id}")
	public String deleteReceivingEmail(@PathVariable int id) {
		return service.deleteReceivingEmail(id);
	}

}
