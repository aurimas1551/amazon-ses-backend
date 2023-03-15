package com.amazonses.object;

import java.util.List;

import com.amazonses.model.ReceivingEmail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LayoutEmailEntity {

	private String senderEmail;
	private String templateName;
	private String configSet;
	private List<ReceivingEmail> contactsEmail;
	private String dataFormat;

}
