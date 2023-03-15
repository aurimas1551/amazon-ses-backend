package com.amazonses.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Emails")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Emails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String templateName;
	private String dataVaraibles;
	private String messageId;
	private String receivingEmail;
	private String status;
	private String senderEmail;

	@Temporal(TemporalType.DATE)
	private Date createdDate = new Date(System.currentTimeMillis());

}
