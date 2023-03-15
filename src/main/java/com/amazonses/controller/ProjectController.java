package com.amazonses.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.BulkEmailDestination;
import com.amazonaws.services.simpleemail.model.BulkEmailDestinationStatus;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendBulkTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.SendBulkTemplatedEmailResult;
import com.amazonses.model.Emails;
import com.amazonses.model.Project;
import com.amazonses.object.LayoutEmailEntity;
import com.amazonses.service.ProjectService;
import com.amazonses.service.ReceivingEmailService;

@CrossOrigin(origins = "https://kvksesbd.ngrok.io")
@RestController
@RequestMapping("api/")
public class ProjectController {

	private static String userKey = "AKIAIMNFZX65GNBBVQFQ";
	private static String userSecret = "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";
	private static String userRegion = "eu-central-1";
	private static AmazonSimpleEmailService ses;
	private static String configSet = "TestReikalavimai";

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ReceivingEmailService receivingEmailService;

	@PostMapping("project")
	public Project addProject(@RequestBody Project project) {
		return projectService.saveProject(project);
	}

	@PostMapping("project/resend/{id}")
	public Project resendingProjectEmails(@PathVariable int id, @RequestBody Project project) throws Exception {
		Project existingProject = projectService.getProject(id);
		List<Emails> existingEmails = existingProject.getEmails();
		init();
		List<BulkEmailDestinationStatus> layoutEmailFeedback = resendLayoutEmail(project);
		for (int i = 0; i < project.getEmails().size(); i++) {
			int index = 0;
			for (Emails email : existingEmails) {
				if (email.getId() == project.getEmails().get(i).getId()) {
					Emails newEmail = new Emails();
					newEmail.setId(project.getEmails().get(i).getId());
					newEmail.setDataVaraibles(project.getEmails().get(i).getDataVaraibles());
					newEmail.setMessageId(layoutEmailFeedback.get(i).getMessageId());
					newEmail.setReceivingEmail(project.getEmails().get(i).getReceivingEmail());
					newEmail.setSenderEmail(project.getEmails().get(i).getSenderEmail());
					newEmail.setStatus(layoutEmailFeedback.get(i).getStatus());
					newEmail.setTemplateName(project.getEmails().get(i).getTemplateName());
					existingProject.getEmails().set(index, newEmail);
				}
				index++;
			}

		}
		
		return projectService.saveProject(existingProject);
	}

	@PostMapping("project/{id}")
	public Project updateProject(@PathVariable int id, @RequestBody Project project) throws Exception {
		Project existingProject = projectService.getProject(id);
		List<Emails> newEmails = project.getEmails();
		List<Emails> existingEmails = existingProject.getEmails();
		LayoutEmailEntity layoutEmailEntity = new LayoutEmailEntity();
		layoutEmailEntity.setConfigSet(configSet);
		layoutEmailEntity.setSenderEmail(project.getEmails().get(0).getSenderEmail());
		layoutEmailEntity.setTemplateName(project.getEmails().get(0).getTemplateName());
		layoutEmailEntity.setDataFormat(project.getEmails().get(0).getDataVaraibles());
		layoutEmailEntity.setContactsEmail(receivingEmailService.getReceivingEmails());
		init();
		List<BulkEmailDestinationStatus> layoutEmailFeedback = sendLayoutEmails(layoutEmailEntity);
		newEmails.get(0).setReceivingEmail(layoutEmailEntity.getContactsEmail().get(0).getReceivingEmail());
		newEmails.get(0).setMessageId(layoutEmailFeedback.get(0).getMessageId());
		newEmails.get(0).setStatus(layoutEmailFeedback.get(0).getStatus());
		existingEmails.add(newEmails.get(0));
		for (int i = 1; i < layoutEmailEntity.getContactsEmail().size(); i++) {
			Emails newEmail = new Emails();
			newEmail.setTemplateName(layoutEmailEntity.getTemplateName());
			newEmail.setDataVaraibles(layoutEmailEntity.getDataFormat());
			newEmail.setSenderEmail(layoutEmailEntity.getSenderEmail());
			newEmail.setReceivingEmail(layoutEmailEntity.getContactsEmail().get(i).getReceivingEmail());
			newEmail.setMessageId(layoutEmailFeedback.get(i).getMessageId());
			newEmail.setStatus(layoutEmailFeedback.get(i).getStatus());
			existingEmails.add(newEmail);
		}
		existingProject.setEmails(existingEmails);
		return projectService.saveProject(existingProject);
	}

	@GetMapping("project/{id}")
	public Project findProject(@PathVariable int id) {
		return projectService.getProject(id);
	}

	@GetMapping("projects")
	public List<Project> findProjects() {
		return projectService.getProjects();
	}

	@DeleteMapping("project/{id}")
	public String deleteProject(@PathVariable int id) {
		return projectService.deleteProject(id);
	}

	private void init() throws Exception {
		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(userKey, userSecret);
		AWSStaticCredentialsProvider initialCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
		ses = AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(initialCredentialsProvider)
				.withRegion(userRegion).build();
	}

	private static List<BulkEmailDestinationStatus> sendLayoutEmails(LayoutEmailEntity layoutEmailEntity) {
		List<BulkEmailDestination> bulkEmailDestinations = new ArrayList<BulkEmailDestination>();
		for (int i = 0; i < layoutEmailEntity.getContactsEmail().size(); i++) {
			Destination destination = new Destination();
			List<String> toAddresses = new ArrayList<String>();
			toAddresses.add(layoutEmailEntity.getContactsEmail().get(i).getReceivingEmail());
			destination.setToAddresses(toAddresses);
			BulkEmailDestination bulkEmailDestination = new BulkEmailDestination();
			bulkEmailDestination.setDestination(destination);
			bulkEmailDestination.setReplacementTemplateData(layoutEmailEntity.getDataFormat());
			bulkEmailDestinations.add(bulkEmailDestination);
		}
		SendBulkTemplatedEmailRequest bulkTemplatedEmailRequest = new SendBulkTemplatedEmailRequest();
		bulkTemplatedEmailRequest.withDestinations(bulkEmailDestinations);
		bulkTemplatedEmailRequest.withTemplate(layoutEmailEntity.getTemplateName());
		bulkTemplatedEmailRequest.withDefaultTemplateData(layoutEmailEntity.getDataFormat());
		bulkTemplatedEmailRequest.withConfigurationSetName(configSet);
		bulkTemplatedEmailRequest.withSource(layoutEmailEntity.getSenderEmail());
		SendBulkTemplatedEmailResult bulkTemplatedEmailResult = ses.sendBulkTemplatedEmail(bulkTemplatedEmailRequest);
		return bulkTemplatedEmailResult.getStatus();
	}

	private static List<BulkEmailDestinationStatus> resendLayoutEmail(Project project) {
		List<BulkEmailDestinationStatus> layoutEmailFeedback = new ArrayList<BulkEmailDestinationStatus>();
		for (int i = 0; i < project.getEmails().size(); i++) {
			SendBulkTemplatedEmailRequest templatedEmailRequest = new SendBulkTemplatedEmailRequest();
			List<BulkEmailDestination> bulkEmailDestinations = new ArrayList<BulkEmailDestination>();
			BulkEmailDestination bulkEmailDestination = new BulkEmailDestination();
			Destination destination = new Destination();
			List<String> toAddresses = new ArrayList<String>();
			toAddresses.add(project.getEmails().get(i).getReceivingEmail());
			destination.setToAddresses(toAddresses);
			bulkEmailDestination.setDestination(destination);
			bulkEmailDestination.setReplacementTemplateData(project.getEmails().get(i).getDataVaraibles());
			bulkEmailDestinations.add(bulkEmailDestination);
			templatedEmailRequest.withDestinations(bulkEmailDestinations);
			templatedEmailRequest.withTemplate(project.getEmails().get(i).getTemplateName());
			templatedEmailRequest.withDefaultTemplateData(project.getEmails().get(i).getDataVaraibles());
			templatedEmailRequest.withConfigurationSetName(configSet);
			templatedEmailRequest.withSource(project.getEmails().get(i).getSenderEmail());
			SendBulkTemplatedEmailResult bulkTemplatedEmailResult = ses.sendBulkTemplatedEmail(templatedEmailRequest);
			layoutEmailFeedback.addAll(i, bulkTemplatedEmailResult.getStatus());
		}
		return layoutEmailFeedback;

	}

}
