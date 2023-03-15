package com.amazonses.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DefaultEmail")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DefaultEmail {

	@Id
	@GeneratedValue
	private int defaultEmailId;
	private String subject;
	private String body;
	private int sentEmailAmount;

}
