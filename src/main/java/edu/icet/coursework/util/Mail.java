package edu.icet.coursework.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Mail implements Runnable {
    private String msg;
    private String to;
    private String subject;
    private File file;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void outMail() throws MessagingException, IOException {
        String from = "jexionline@gmail.com"; // sender's email address
        String password = "txme usid tcqw dfmp"; // sender's email password

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(from));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(this.subject);

            if (file != null) {
                MimeBodyPart textPart = new MimeBodyPart();
                textPart.setText(this.msg);

                MimeBodyPart filePart = new MimeBodyPart();
                filePart.attachFile(this.file);

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(filePart);

                mimeMessage.setContent(multipart);
            } else {
                mimeMessage.setText(this.msg, "UTF-8", "html");
            }

            Transport.send(mimeMessage);
            System.out.println("Email sent successfully.");
        } catch (MessagingException | IOException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void run() {
        try {
            outMail();
        } catch (MessagingException | IOException e) {
            System.err.println("Error in email sending thread: " + e.getMessage());
        }
    }
}
