package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer>{

}
