package com.amazonses.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ReceivingEmails")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ReceivingEmail {

	@Id
	@GeneratedValue
	private int id;
	private String receivingEmail;

}
