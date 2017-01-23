package com.example.Mailer;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

public class SendReports
{
  private static String clbMailhost = "outlook.office365.com";

  private static String clbFromAddress = "pumaadmin@brillio.com";
  private static String toAddressFile = "";

  private static String subject = "WhatsUp Board Survey report";
  private static String attachFile = "";

  public SendReports(String toAddress,String month) {
	SendReports.toAddressFile = toAddress;
	Properties prop=new PropertyUtils().getConfigProperties();
	String reportLocation=prop.getProperty("ReportLocation");
	StringBuilder attachingFile=new StringBuilder(reportLocation);
	attachingFile.append(month).append("_report.xlsx");
	SendReports.attachFile = attachingFile.toString();
}

  public  boolean sendReports()
  {

    try
    {
      Properties props = new Properties();
      props.put("mail.smtp.host", clbMailhost);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "BRIllio@8890");

      props.put("mail.smtp.starttls.enable", "true");
      System.out.println("SendMail - after args and props");

      Session session = Session.getDefaultInstance(props, new Authenticator()
      {
        protected PasswordAuthentication getPasswordAuthentication()
        {
          return new PasswordAuthentication("pumaadmin@brillio.com", "BRIllio@8890");
        }
      });
      System.out.println("SendMail - after authentication");
      Message msg = new MimeMessage(session);

      Multipart multiPart = new MimeMultipart();

      msg.setSentDate(new Date());
      msg.setFrom(new InternetAddress(clbFromAddress));

      InternetAddress[] addressTo = InternetAddress.parse(toAddressFile);
      msg.setRecipients(Message.RecipientType.TO, addressTo);

      msg.setSubject(subject);
      System.out.println("SendMail - after mail message creation");

      if ((attachFile != null) && (!attachFile.trim().equals("")))
      {
        System.out.println("SendMail - inside add attachment");
        MimeBodyPart attachmentPart = new MimeBodyPart();

        FileDataSource fds = new FileDataSource(attachFile);
        attachmentPart.setDataHandler(new DataHandler(fds));
        attachmentPart.setFileName(fds.getName());
        multiPart.addBodyPart(attachmentPart);

        System.out.println("SendMail - after add attachment");
      }
      msg.setContent(multiPart);

      msg.saveChanges();

      System.out.println("SendMail - after mail changes save");
      Transport transport = session.getTransport("smtp");
      transport.connect(clbMailhost, "pumaadmin@brillio.com", "BRIllio@8890");
      System.out.println("SendMail - after connection");
      transport.sendMessage(msg, msg.getAllRecipients());
      System.out.println("SendMail - after sendMessage");
      transport.close();
      File report = new File(attachFile);
      report.delete();
      return true;
    }
    catch (Exception e) {
    	 Notification.show("ERROR", "cannot mail to "+toAddressFile+" "+e.getMessage(),Type.ERROR_MESSAGE);
    	 return false;
    }
  }

}
