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

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class SendMail
{
  private static String clbMailhost = "outlook.office365.com";

  private static String clbFromAddress = "pumaadmin@brillio.com";

  private static String toAddress = "";

  private static String subject = "Whats UP Board Survey";

  private static String content = " ";

public SendMail(String toAddress,String content) {
    SendMail.toAddress= toAddress;
    SendMail.content = content;
}

  public  boolean sendMail()
  {
    try
    {
      Properties props = new Properties();
      props.put("mail.smtp.host", clbMailhost);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "BRIllio@8890");

      props.put("mail.smtp.starttls.enable", "true");

      Session session = Session.getDefaultInstance(props, new Authenticator()
      {
        protected PasswordAuthentication getPasswordAuthentication()
        {
          return new PasswordAuthentication("pumaadmin@brillio.com", "BRIllio@8890");
        }
      });
      Message msg = new MimeMessage(session);

      MimeBodyPart mbp = new MimeBodyPart();
      mbp.setContent(content, "text/html");

      Multipart multiPart = new MimeMultipart();
      multiPart.addBodyPart(mbp);

      msg.setSentDate(new Date());
      msg.setFrom(new InternetAddress(clbFromAddress));
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));

      msg.setSubject(subject);

      msg.setContent(multiPart);

      mbp.setText(content,"UTF-8", "html");

      msg.saveChanges();

      Transport transport = session.getTransport("smtp");
      transport.connect(clbMailhost, "pumaadmin@brillio.com", "BRIllio@8890");
      transport.sendMessage(msg, msg.getAllRecipients());
      transport.close();
      return true;
    }
    catch (Exception e) {
      Notification.show("ERROR", "cannot mail to "+toAddress+" because: "+e.getMessage(),Type.ERROR_MESSAGE);
      return false;
    }
  }

}

