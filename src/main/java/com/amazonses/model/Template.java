package com.amazonses.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "template")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Template {

	@Id
	@GeneratedValue
	private int id;
	private String templateName;
	private String dataForm;

}
