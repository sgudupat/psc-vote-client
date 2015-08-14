package com.client.vote;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.os.AsyncTask;

public class LongRunningGetIO extends AsyncTask <Void, Void, String> {
	String otp="";

	public LongRunningGetIO(String sotp) {
		// TODO Auto-generated constructor stub
		this.otp=sotp;
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		final String username = "murali.n333@gmail.com";
		final String password="omnamovenkateshaya";
		

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });

			try {

				Message message1 = new MimeMessage(session);
				message1.setFrom(new InternetAddress("murali.n333@gmail.com"));
				message1.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("murali.n333@gmail.com"));
				message1.setSubject("OTP Password");
				message1.setText(otp);

				Transport.send(message1);

				//System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		/* String[] recipients = {"murali.n333@gmail.com"};
	        String from = "service@boost.att-mail.com";
	        // Set the host smtp address
	        Properties props = new Properties();
	        props.put("mail.smtp.host", "localhost");
	        // create some properties and get the default Session
	        Session session = Session.getDefaultInstance(props, null);
	        //session.setDebug(debug);
	        // create a message
	        Message msg = new MimeMessage(session);
	        // set the from and to address
	        InternetAddress addressFrom;
			try {
				addressFrom = new InternetAddress(from);
			
	        msg.setFrom(addressFrom);
	       // String addressTo = recipients;
	        InternetAddress[] addressTo = new InternetAddress[recipients.length];
	        for (int i = 0; i < recipients.length; i++) {
	            addressTo[i] = new InternetAddress(recipients[i].trim());
	        }
	     
	        msg.setRecipients(Message.RecipientType.TO, addressTo);
	        // Setting the Subject and Content Type
	        msg.setSubject("murali");
	        String message="998765";
	        String content="dsd";
	        msg.setContent(message, content);
	        Transport.send(msg);
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			return "success";
	   
	}
}

	


