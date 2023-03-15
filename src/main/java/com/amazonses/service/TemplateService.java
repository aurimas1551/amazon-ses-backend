package com.amazonses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonses.model.Template;
import com.amazonses.repository.TemplateRepo;

@Service
public class TemplateService {

	@Autowired
	private TemplateRepo repository;

	public Template saveTemplate(Template template) {
		return repository.save(template);
	}

	public Template getTemplate(int id) {
		return repository.findById(id).orElse(null);
	}

	public List<Template> getTemplates() {
		return repository.findAll();
	}

	public String deleteTemplate(int id) {
		repository.deleteById(id);
		return "template deleted" + id;
	}

}
