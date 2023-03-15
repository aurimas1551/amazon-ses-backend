package com.amazonses.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.CreateTemplateRequest;
import com.amazonaws.services.simpleemail.model.CreateTemplateResult;
import com.amazonaws.services.simpleemail.model.DeleteTemplateRequest;
import com.amazonaws.services.simpleemail.model.DeleteTemplateResult;
import com.amazonaws.services.simpleemail.model.Template;
import com.amazonses.object.TemplateEntity;


@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
//http://localhost:4200
@RestController
@RequestMapping("api/ses/")
public class TemplateSESController {

	private static String userKey = "AKIAIMNFZX65GNBBVQFQ";
	private static String userSecret = "dQNFuzcZhL1ZMg6uzHcgliKGFOWs9INnRwGfbiKb";
	private static String userRegion = "eu-central-1";

	private static AmazonSimpleEmailService ses;

	// Fake data-------------------------------------------------

	@GetMapping("template")
	public TemplateEntity getTemplate() {
		TemplateEntity templateEntity = new TemplateEntity();
		templateEntity.setTemplateName("DefaultTemplateKVK");
		templateEntity.setEmailName("{{subject}}");
		templateEntity.setEmailHtmlText("{{body}}");
		templateEntity.setEmailText("{{body}}");
		return templateEntity;
	}

	@PostMapping("template")
	public ResponseMetadata addTemplate(@RequestBody TemplateEntity templateEntity) throws Exception {
		init();
		return createTemplate(templateEntity);
	}

	@DeleteMapping("template")
	public ResponseMetadata removeTemplate(@RequestBody String templateName) throws Exception {
		init();
		return deleteTemplate(templateName);
	}

	private ResponseMetadata createTemplate(TemplateEntity templateEntity) {
		Template template = new Template();
		template.setTemplateName(templateEntity.getTemplateName());
		template.setSubjectPart(templateEntity.getEmailName());
		template.setHtmlPart(templateEntity.getEmailHtmlText());
		template.setTextPart(templateEntity.getEmailText());
		CreateTemplateRequest createTemplateRequest = new CreateTemplateRequest();
		createTemplateRequest.setTemplate(template);
		CreateTemplateResult result = ses.createTemplate(createTemplateRequest);
		return result.getSdkResponseMetadata();
	}

	private ResponseMetadata deleteTemplate(String templateName) {
		DeleteTemplateRequest deleteTemplateRequest = new DeleteTemplateRequest();
		deleteTemplateRequest.setTemplateName(templateName);
		DeleteTemplateResult result = ses.deleteTemplate(deleteTemplateRequest);
		return result.getSdkResponseMetadata();
	}

	private void init() throws Exception {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(userKey, userSecret);
		AWSStaticCredentialsProvider initialCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
		ses = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(initialCredentialsProvider)
				.withRegion(userRegion).build();
	}

}
