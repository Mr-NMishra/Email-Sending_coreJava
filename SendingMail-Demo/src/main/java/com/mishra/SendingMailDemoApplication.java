package com.mishra;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SendingMailDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SendingMailDemoApplication.class, args);
		String message = "Testing mail messgae";
		String subject = "Testing Message Subject";
		String to = "mishra.narendra003@gmail.com";
		String from = "nikmydesire@gmail.com";

		// Sendng mail with attachemnt
		SendAttachement(message, subject, from, to);

		// Sending Mail in Text Form only
//		sendMail(message, subject, to, from);
	}

	private static void SendAttachement(String message, String subject, String from, String to) {
		// creating host
		String host = "smtp.gmail.com";
		// now getting system properties
		Properties properties = System.getProperties();

		// setting Important information to properties
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 465);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Step to get session Object
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication("nikmydesire@gmail.com", "64574258");
			}

		});
		session.setDebug(true);

		// compose the message
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			// sent form
			mimeMessage.setFrom(from);
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			mimeMessage.setSubject(subject);
			// adding attachemnet

			// locating file
			String location = "/home/nick/Pictures/Screenshot -2021-12-22 03-27-24.png";

			MimeMultipart mimeMultipart = new MimeMultipart();

			MimeBodyPart MimeText = new MimeBodyPart();

			MimeBodyPart mimeattachement = new MimeBodyPart();

			try {
				MimeText.setText(message);
				// Creating file
				File file = new File(location);
				mimeattachement.attachFile(file);

				// now attaching file
				mimeMultipart.addBodyPart(MimeText);
				mimeMultipart.addBodyPart(mimeattachement);
			} catch (Exception e) {
				// TODO: handle exception
			}

			mimeMessage.setContent(mimeMultipart);

			Transport.send(mimeMessage);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void sendMail(String message, String subject, String to, String from) {
		// Variable for gmail
		// gmail host
		String host = "smtp.gmail.com";

		// fetching system property
		Properties properties = System.getProperties();
		System.out.println(properties);

		// setting important information to properties
		// setting host
		properties.put("mail.smtp.host", host);
		// setting smtp port
		properties.put("mail.smpt.port", "465");
		// enabling ssl
		properties.put("mail.smtp.ssl.enable", "true");
		// authentication627e637a
		properties.put("mail.smtp.auth", "true");

		// Steps to send mail
		// Step-1
		// Getting session object
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nikmydesire@gmail.com", "64574258");
			}

		});

		// debugging if any error occurs
		session.setDebug(true);

		// Step-2
		// compose the message [text/file/attachment]
		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			// sending mail from
			mimeMessage.setFrom(from);
			// sending mail to
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// adding subject to message
			mimeMessage.setSubject(subject);
			// adding text to message
			mimeMessage.setText(message);

			// Step-3
			// send the message using transport class
			Transport.send(mimeMessage);
			System.out.println("Send successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
