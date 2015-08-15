package com.client.vote;

import android.os.AsyncTask;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class LongRunningGetIO extends AsyncTask<Void, Void, String> {

    String otp = "";
    String emailAddress;

    public LongRunningGetIO(String rec, String sotp) {
        this.otp = sotp;
        this.emailAddress = rec;
    }

    @Override
    protected String doInBackground(Void... params) {
        final String username = "murali.n333@gmail.com";
        final String password = "omnamovenkateshaya";

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
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            message1.setSubject("OTP Password");
            message1.setText(otp);
            Transport.send(message1);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "success";
    }
}