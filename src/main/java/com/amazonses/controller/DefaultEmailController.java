package com.amazonses.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonses.model.DefaultEmail;
import com.amazonses.model.ReceivingEmail;
import com.amazonses.service.DefaultEmailService;
import com.amazonses.service.ReceivingEmailService;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class DefaultEmailController {

	private static String userKey = "AKIAIMNFZX65GNBBVQFQ";
	private static String userSecret = "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";
	private static String userRegion = "eu-central-1";
	private static String configSet = "TestReikalavimai";
	private static String senderEmail = "aurimas1551@gmail.com";
	private static AmazonSimpleEmailService ses;

	@Autowired
	private DefaultEmailService service;

	@Autowired
	private ReceivingEmailService receivingEmailService;

	@PostMapping("defaultemail")
	public DefaultEmail addDefaultEmail(@RequestBody DefaultEmail defaultEmail) throws Exception {
		init();
		List<ReceivingEmail> receivingEmails = receivingEmailService.getReceivingEmails();
		ArrayList<String> contacts = new ArrayList<String>();
		for (int i = 0; i < receivingEmails.size(); i++) {
			contacts.add(receivingEmails.get(i).getReceivingEmail());
		}
		defaultEmail.setSentEmailAmount(receivingEmails.size());
		sendEmail(defaultEmail, contacts);
		return service.saveDefaultEmail(defaultEmail);
	}

	@GetMapping("defaultemails")
	public List<DefaultEmail> findDefaultEmails() {
		return service.getDefaultEmails();
	}

	private void init() throws Exception {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(userKey, userSecret);
		AWSStaticCredentialsProvider initialCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
		ses = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(initialCredentialsProvider)
				.withRegion(userRegion).build();
	}

	private void sendEmail(DefaultEmail defaultEmail, ArrayList<String> contacts) {
		SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(contacts))
				.withMessage(new Message().withSubject(new Content().withData(defaultEmail.getSubject()))
						.withBody(new Body().withText(new Content().withData(defaultEmail.getBody()))))
				.withSource(senderEmail).withConfigurationSetName(configSet);
		ses.sendEmail(request);
	}

}
