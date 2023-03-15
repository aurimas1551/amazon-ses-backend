package com.amazonses.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TemplateEntity {
	
	private String templateName;
	private String emailName;
	private String emailHtmlText;
	private String emailText;
	
}
