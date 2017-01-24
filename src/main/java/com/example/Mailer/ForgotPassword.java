package com.example.Mailer;


import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.example.Helpers.PropertyUtils;
import com.example.constants.ValidationConstants;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;

public class ForgotPassword
{

  private static String fromAddress= new PropertyUtils().getproperty("fromAddress");

  private static String fromPassword= new PropertyUtils().getproperty("fromPassword");

  private static String clbMailhost = "outlook.office365.com";

  private static String clbFromAddress = fromAddress;

  private static String toAddress = "";

  private static String subject = "Password Reset";

  private static String content = " ";


  public static  void sendMail(TextField emailId,String password)  {
    try
    {
      System.out.println(fromAddress+","+fromPassword);
      toAddress = emailId.getValue();
      content = "Your new password is "+password;
      Properties props = new Properties();
      props.put("mail.smtp.host", clbMailhost);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", fromPassword);

      props.put("mail.smtp.starttls.enable", "true");
     // System.out.println("SendMail - after args and props");

      Session session = Session.getDefaultInstance(props, new Authenticator()
      {
        protected PasswordAuthentication getPasswordAuthentication()
        {
          return new PasswordAuthentication(fromAddress, fromPassword);
        }
      });
     // System.out.println("SendMail - after authentication");
      Message msg = new MimeMessage(session);

      MimeBodyPart mbp = new MimeBodyPart();
      mbp.setContent(content, "text/html");

      Multipart multiPart = new MimeMultipart();
      multiPart.addBodyPart(mbp);

      msg.setSentDate(new Date());
      msg.setFrom(new InternetAddress(clbFromAddress));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

      msg.setSubject(subject);

     // System.out.println("SendMail - after mail message creation");


      msg.setContent(multiPart);

      //msg.setText(content);
      mbp.setText(content,"UTF-8", "html");

      msg.saveChanges();

    //  System.out.println("SendMail - after mail changes save");
      Transport transport = session.getTransport("smtp");
      transport.connect(clbMailhost, fromAddress, fromPassword);
    //  System.out.println("SendMail - after connection");
      transport.sendMessage(msg, msg.getAllRecipients());
      transport.close();
    }
    catch (Exception e) {
     Notification.show(ValidationConstants.ERROR,e.getMessage(), Type.ERROR_MESSAGE);
    }
  }





}

