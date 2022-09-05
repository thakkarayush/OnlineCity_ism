 // Java Program to Create Rest Controller that
// Defines various API for Sending Mail

package com.controller.user;

// Importing required classes

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bean.EmailDetailsBean;
import com.service.EmailService;

// Annotation
@Controller
// Class
public class EmailController {

	@Autowired 
	private EmailService emailService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String
	sendMail(@RequestBody EmailDetailsBean details)
	{
		String status
			= emailService.sendSimpleMail(details);

		return status;
	}

	// Sending email with attachment
	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(
		@RequestBody EmailDetailsBean details)
	{
		String status = emailService.sendMailWithAttachment(details);

		return status;
	}
}
