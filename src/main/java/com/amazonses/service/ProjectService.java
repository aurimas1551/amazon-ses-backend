package com.amazonses.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonses.model.Project;
import com.amazonses.repository.ProjectRepo;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepo repository;

	public Project saveProject(Project project) {
		return repository.save(project);
	}

	public Project getProject(int id) {
		return repository.findById(id).orElse(null);
	}

	public List<Project> getProjects() {
		return repository.findAll();
	}

	public String deleteProject(int id) {
		repository.deleteById(id);
		return "deleted project" + id;
	}

}
