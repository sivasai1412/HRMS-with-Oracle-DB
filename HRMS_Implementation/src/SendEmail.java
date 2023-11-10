package bin;

import java.util.Properties;  
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message; 
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.AddressException;
import javax.mail.Transport;

/**
*This class is used to send the email for the employees and the contractors.
*One time password for the particular users will be sent using this class.
*/
public class SendEmail{  

	/**
	*This method is used to send the email for the particular email ID.
	*@param recepient This parameter contains the email address of the receiver.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter is the joining date of the employee.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void sendEmail(String recepient, String username, String dateOfJoin) throws Exception{
		System.out.println("\t\t\t\t\tPreparing to send mail");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
	
		String myAccountEmail = "pavankumaranjuri5454@gmail.com";
		String password = "pavan6680";

		Session session = Session.getInstance(properties, new Authenticator(){
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(myAccountEmail, password);
		}	
		});
	
		Message message = prepareMessage(session, myAccountEmail, recepient, username, dateOfJoin);
		Transport.send(message);
		System.out.println("\t\t\t\t\tMail sent successfully");	


	}


	/**
	*This method is used to send the email for the particular email ID of the contractor.
	*@param recepient This parameter contains the email address of the receiver.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter is the joining date of the contractor.
	*@param dateOfTermination This parameter is the termination date of the contractor.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void sendContractorEmail(String recepient, String username, String dateOfJoin, String dateOfTermination) throws Exception{
	System.out.println("\t\t\t\t\tPreparing to send mail");
	Properties properties = new Properties();
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.starttls.enable", "true");
	properties.put("mail.smtp.host", "smtp.gmail.com");
	properties.put("mail.smtp.port", "587");

	String myAccountEmail = "pavankumaranjuri5454@gmail.com";
	String password = "pavan6680";

	Session session = Session.getInstance(properties, new Authenticator(){
	protected PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(myAccountEmail, password);
	}	
	});
	
	Message message = prepareContractorMessage(session, myAccountEmail, recepient, username, dateOfJoin, dateOfTermination);
	Transport.send(message);
	System.out.println("\t\t\t\t\tMail sent successfully");	


}


	/**
	*This method is contains the message that will be sent to the contractors on their joining.
	*@param session This parameter acts as the connection factory for the JAVA mail API, which handles the configuration settings and authentication.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter is the joining date of the contractor.
	*@param dateOfTermination This parameter is the termination date of the contractor.
	*/
	private static Message prepareContractorMessage(Session session, String myAccountEmail, String recepient, String username, String dateOfJoin, String dateOfTermination){
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Joining Letter");
			message.setText(contractorJoiningMail(username, dateOfJoin, dateOfTermination));
			return message;
		}catch(Exception e){
			System.out.println("Error");
		}
		return null;
	}

	/**
	*This method is used to send the OTP message to the user.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param otp This parameter is the one time password that is generated. 
	*/
	public static String otpMail(String username, String otp){
	
		String s = "Dear " + username + ", \nHope you are doing well, The OTP (one time password) generated for updating your password is: " + otp + ". \nThank You.";
		return s;
	}


	/**
	*This method is used to send the one time password to the user in email.
	*@param recepient This parameter contains the email address of the receiver.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param otp This parameter contains the One time password that is generated.
	*@exception Exception This exception will be used on the Input error.
	*@see Exception 
	*/
	public static void sendEmailOtp(String recepient, String username, String otp) throws Exception{
		//System.out.println("\t\t\t\t\tPreparing to send mail");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
	
		String myAccountEmail = "pavankumaranjuri5454@gmail.com";
		String password = "pavan6680";
	
		Session session = Session.getInstance(properties, new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(myAccountEmail, password);
			}	
		});
	
		Message message = prepareOtpMessage(session, myAccountEmail, recepient, username, otp);
		Transport.send(message);
		System.out.println("\t\t\t\t\tMail sent successfully");	


	}


	/**
	*This method is contains the message that will be sent to the contractors on their joining.
	*@param session This parameter acts as the connection factory for the JAVA mail API, which handles the configuration settings and authentication.
	*@param myAccountEmail This parameter contains the email ID of the sender.
	*@param recepient This parameter contains the email address of the receiver.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param otp This parameter contains the one time password that is generated.
	*/
	private static Message prepareOtpMessage(Session session, String myAccountEmail, String recepient, String username, String otp){
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("One Time Password");
			message.setText(otpMail(username, otp));
			return message;
		}catch(Exception e){
			System.out.println("Error");
		}
		return null;
	}


	/**
	*This method is used to message which will be sent to the user.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter contains the joining date of the employee.
	*/
	public static String employeeJoiningMail(String username, String dateOfJoin){
	
		String s = "Dear " + username + ", \nHope you are doing well, The joining into the organization has been confirmed on " + dateOfJoin + ". \nThank You.";
		return s;
	}
	/**
	*This method is used to message which will be sent to the user.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter contains the joining date of the contractor.
	*@param dateOfTermination This parameter contains the termination date of the contractor.
	*/
	public static String contractorJoiningMail(String username, String dateOfJoin, String dateOfTermination){
		String s = "Dear " + username + ", \nHope you are doing well, The joining into the organization has been confirmed on " + dateOfJoin + ". Your contract period end on " + dateOfTermination + ".\nThank You.";
		return s;
	}
	/**
	*This method is contains the message that will be sent to the contractors on their joining.
	*@param session This parameter acts as the connection factory for the JAVA mail API, which handles the configuration settings and authentication.
	*@param myAccountEmail This parameter contains the email ID of the sender.
	*@param recepient This parameter contains the email address of the receiver.
	*@param username This parameter is the name of the user which is used at the salutation of the mail.
	*@param dateOfJoin This parameter contains the joining date of the employee.
	*/
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String username, String dateOfJoin){
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Joining Letter");
			message.setText(employeeJoiningMail(username, dateOfJoin));
			return message;
		}catch(Exception e){
			System.out.println("Error");
		}
	return null;
	}
}