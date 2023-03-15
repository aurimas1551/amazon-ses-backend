package com.amazonses.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amazonses.model.DefaultEmail;

@Repository
public interface DefaultEmailRepo extends JpaRepository<DefaultEmail, Integer>{

}
