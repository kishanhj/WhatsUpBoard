//package com.example.Mailer;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.PrintStream;
//import java.util.Date;
//import java.util.Properties;
//import javax.activation.DataHandler;
//import javax.activation.FileDataSource;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.Message.RecipientType;
//import javax.mail.Multipart;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//
//public class SendMail
//{
//  private static String clbMailhost = "outlook.office365.com";
//
//  private static String clbFromAddress = "pumaadmin@brillio.com";
//  private static String toAddressFile = "";
//
//  private static String subject = "";
//  private static String replyTo = "";
//  private static String attachFile = "";
//
//  public static void main(String[] args)
//  {
//    try
//    {
//      toAddressFile = args[0];
//      subject = args[1];
//      attachFile = args[2];
//      replyTo = args[3];
//
//      Properties props = new Properties();
//      props.put("mail.smtp.host", clbMailhost);
//      props.put("mail.smtp.port", "587");
//      props.put("mail.smtp.auth", "BRillio@8890");
//
//      props.put("mail.smtp.starttls.enable", "true");
//      System.out.println("SendMail - after args and props");
//
//      Session session = Session.getDefaultInstance(props, new Authenticator()
//      {
//        protected PasswordAuthentication getPasswordAuthentication()
//        {
//          return new PasswordAuthentication("pumaadmin@brillio.com", "BRillio@8890");
//        }
//      });
//      System.out.println("SendMail - after authentication");
//      Message msg = new MimeMessage(session);
//
//      Multipart multiPart = new MimeMultipart();
//
//      msg.setSentDate(new Date());
//      msg.setFrom(new InternetAddress(clbFromAddress));
//
//      InternetAddress[] addressTo = InternetAddress.parse(readFile(toAddressFile));
//      msg.setRecipients(Message.RecipientType.TO, addressTo);
//
//      msg.setSubject(subject);
//      System.out.println("SendMail - after mail message creation");
//
//      if ((attachFile != null) && (!attachFile.trim().equals("")))
//      {
//        System.out.println("SendMail - inside add attachment");
//        MimeBodyPart attachmentPart = new MimeBodyPart();
//
//        FileDataSource fds = new FileDataSource(attachFile);
//        attachmentPart.setDataHandler(new DataHandler(fds));
//        attachmentPart.setFileName(fds.getName());
//        multiPart.addBodyPart(attachmentPart);
//
//        System.out.println("SendMail - after add attachment");
//      }
//      msg.setContent(multiPart);
//
//      msg.saveChanges();
//
//      System.out.println("SendMail - after mail changes save");
//      Transport transport = session.getTransport("smtp");
//      transport.connect(clbMailhost, "pumaadmin@brillio.com", "BRillio@8890");
//      System.out.println("SendMail - after connection");
//      transport.sendMessage(msg, msg.getAllRecipients());
//      transport.close();
//    }
//    catch (Exception e) {
//      System.out.println(e);
//    }
//  }
//
//  private static String readFile(String filename)
//  {
//    FileInputStream fin = null;
//    String contents = null;
//    try {
//      File f = new File(filename);
//      if (f.exists()) {
//        fin = new FileInputStream(f);
//        int fileSize = fin.available();
//        byte[] fileData = new byte[fileSize];
//        fin.read(fileData);
//        contents = new String(fileData);
//      }
//    } catch (Exception ioe) {
//      System.out.println("Please specify correct file path");
//      try
//      {
//        if (fin != null)
//          fin.close();
//      }
//      catch (Exception localException1)
//      {
//      }
//    }
//    finally
//    {
//      try
//      {
//        if (fin != null)
//          fin.close();
//      }
//      catch (Exception localException2)
//      {
//      }
//    }
//    return contents;
//  }
//}