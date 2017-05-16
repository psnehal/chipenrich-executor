package org.ncibi.chipenrich;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ResourceBundle;
import org.junit.Test;
import org.ncibi.main.JavaMailer;


public class TestMailer2  {

	public static void main(String[] args) throws IOException
	{
		TestMailer2 jt = new TestMailer2();
		jt.test();
		
		
	}
	public void test() {
		ResourceBundle u = ResourceBundle.getBundle("org.ncibi.resource.bundle.url");
		String uid = "43535353151513";
		String url = "result.jsp?uid=" + uid + "&df=" + "Direction";
		String sender = "snehal@umich.edu";
		String recipient = "snehalbpatil87@gmail.com";
		String subject = "ChipEnrich result Analysis";
		String emailMessage = "";
		System.out.println("1");
		emailMessage += "Hello, \n Your analyis on LRPath as been submitted. Please use the link below to check on the status of your analysis.\n\n";
		emailMessage += u.getString("webRoot") + url;
		System.out.println("2");
		JavaMailer mail = new JavaMailer();
		System.out.println("3");
		mail.sendMail(sender, recipient, subject, emailMessage);
		System.out.println("4");
	}

}
