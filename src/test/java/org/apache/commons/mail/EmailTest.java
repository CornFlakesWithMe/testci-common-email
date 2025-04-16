package org.apache.commons.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {

	private static final String[] TEST_EMAILS = { "ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.combd" };
	private static final String[] EMTPY_TEST_EMAILS = {};
	private static final String VALID_EMAIL = "cc@example.com";

	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		
		
	}
	
	
	
	@Test
	public void testAddBcc() throws Exception {
				
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
	}
	
	@Test (expected = EmailException.class)
	public void testAddBccException() throws Exception {
		
		email.addBcc(EMTPY_TEST_EMAILS);
	}
	
	@Test
	public void testAddCc() throws Exception {
		
        email.addCc(VALID_EMAIL);
        assertEquals(1, email.getCcAddresses().size());
    }
	
	@Test
	public void testAddHeader() throws Exception {
		
        email.addHeader("Example", "9");
        assertEquals("9", email.headers.get("Example"));
    }

	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderExceptionEmptyHeader() throws Exception {
		
		email.addHeader("", "5");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAddHeaderExceptionEmptyValue() throws Exception {
		
		email.addHeader("Example", "");
	}
	
	@Test
	public void testAddReplyTo() throws Exception {
		
        email.addReplyTo(VALID_EMAIL, "User");
        assertEquals(1, email.getReplyToAddresses().size());
    }
	
	@Test
	public void testGetHostNameNull() throws Exception {
		
        assertNull(email.getHostName());
    }
	
	@Test
	public void testGetHostNameNotNull() throws Exception {
		
		email.setHostName("name.com");
        assertEquals("name.com", email.getHostName());
    }
	
	@Test (expected = EmailException.class)
	public void testGetMailSessionEmpty() throws Exception {
		
		email.setHostName(""); 
		email.getMailSession();
    }
	
	@Test
	public void testGetMailSession() throws Exception {
		
		email.setHostName("example.com");
        Session session = email.getMailSession();
        
        assertNotNull(session);
        assertEquals("example.com", session.getProperty("mail.smtp.host"));
    }
	
	@Test
    public void testGetSentDateValue() {
		
        Date customDate = new Date();
        email.setSentDate(customDate);
        
        assertEquals(customDate, email.getSentDate());
    }
	
	@Test
    public void testGetSocketConnectionTimeoutValue() throws Exception {
		
        assertEquals(60000, email.getSocketConnectionTimeout()); 
    }
	
	@Test
    public void testSetFromValidEmail() throws Exception {
		
        email.setFrom("sender@example.com");

        assertEquals("sender@example.com", email.getFromAddress().getAddress());
    }
	
	@Test
    public void testBuildMimeMessageValid() throws EmailException {
		
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage(); 
        assertNotNull(email.getMimeMessage()); 
    }

    @Test(expected = EmailException.class)
    public void testBuildMimeMessageInvalidForm() throws EmailException {
    	
        email.setHostName("smtp.example.com");
        email.addTo("recipient@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage(); 
    }

    @Test(expected = EmailException.class)
    public void testBuildMimeMessageInvalidRecipient() throws EmailException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage(); 
    }
    
    @Test(expected = IllegalStateException.class)
    public void testBuildMimeMessageValid2() throws EmailException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage(); 
        email.buildMimeMessage(); 
    }
    
    @Test
    public void testBuildMimeMessageCCList() throws EmailException, MessagingException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.addCc("cc@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage();
        InternetAddress[] ccAddresses = (InternetAddress[]) email.getMimeMessage().getRecipients(Message.RecipientType.CC);

        assertEquals("cc@example.com", ccAddresses[0].getAddress());
    }

    @Test
    public void testBuildMimeMessageCCList2() throws EmailException, MessagingException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.addBcc("bcc@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage();
        InternetAddress[] bccAddresses = (InternetAddress[]) email.getMimeMessage().getRecipients(Message.RecipientType.BCC);

        assertEquals("bcc@example.com", bccAddresses[0].getAddress());
    }

    @Test
    public void testBuildMimeMessageValidReply() throws EmailException, MessagingException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.addReplyTo("reply@example.com", "Reply User");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");

        email.buildMimeMessage();
        InternetAddress[] replyToAddresses = (InternetAddress[]) email.getMimeMessage().getReplyTo();

        assertEquals("reply@example.com", replyToAddresses[0].getAddress());
    }

    @Test
    public void testBuildMimeMessageValidHeaders() throws EmailException, MessagingException {
    	
        email.setHostName("smtp.example.com");
        email.setFrom("sender@example.com");
        email.addTo("recipient@example.com");
        email.setSubject("Test Subject");
        email.setMsg("Test Message.");
        email.addHeader("Header", "HeaderValue");

        email.buildMimeMessage();
        String[] headers = email.getMimeMessage().getHeader("Header");

        assertEquals("HeaderValue", headers[0]);
    }
	
	
}
