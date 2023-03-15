package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.Template;

@Repository
public interface TemplateRepo extends JpaRepository<Template, Integer>{

}
