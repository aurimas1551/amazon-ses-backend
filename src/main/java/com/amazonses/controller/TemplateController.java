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

import com.amazonses.model.Template;
import com.amazonses.service.TemplateService;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class TemplateController {

	@Autowired
	private TemplateService service;

	@PostMapping("template")
	public Template saveTemplate(@RequestBody Template template) {
		return service.saveTemplate(template);
	}

	@GetMapping("template/{id}")
	public Template getTemplate(@PathVariable int id) {
		return service.getTemplate(id);
	}

	@GetMapping("templates")
	public List<Template> getTemplates() {
		return service.getTemplates();
	}

	@DeleteMapping("template/{id}")
	public String deleteTemplate(@PathVariable int id) {
		return service.deleteTemplate(id);
	}

}
