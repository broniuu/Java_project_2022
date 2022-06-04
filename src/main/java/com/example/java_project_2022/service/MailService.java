package com.example.java_project_2022.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;


/**
 * klasa sluzaca do wyslania mail z 'firmowago' konta
 */
public class MailService {
    static String username ="szamannoreply@gmail.com";
    static String password = "ntigzagdmctmnlwz";

    /**
     *
     * wysyla email.
     *
     * @param recipientsField  odbiorca
     * @param titleField tytul
     * @param contentField  zawartosc
     * @throws   IOException
     */
    public static void sendEmail(String recipientsField, String titleField, String contentField) throws IOException {


        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientsField)
            );
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(contentField);
            message.setSubject(titleField);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File("plik.pdf"));
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart );
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
