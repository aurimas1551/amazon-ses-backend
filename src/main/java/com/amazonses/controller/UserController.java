package com.amazonses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonses.model.User;
import com.amazonses.repository.UserRepo;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class UserController {

	@Autowired
	private UserRepo userRepo;

	@SuppressWarnings("unchecked")
	@PostMapping("login")
	public ResponseEntity<User> loginUser(@RequestBody User userData) {
		System.out.println(userData);
		System.out.println(userData.getUserId());
		User user = userRepo.findByUserId(userData.getUserId());
		System.out.println(user);
		if (user.getPassword().equals(userData.getPassword())) {
			return ResponseEntity.ok(user);
		} else {
			return (ResponseEntity<User>) ResponseEntity.notFound();
		}
	}

}
