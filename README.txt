CIS376 Assignment3: JUnit Testing
Corneliu Floarea (cfloarea@umich.edu)

This is a JUnit test project to test 10 different methods for a common emails application.

- Email   addBcc(String... emails)
- Email   addCc(String email)
- void    addHeader(String name, String value)
- Email   addReplyTo(String email, String name)
- void    buildMimeMessage()
- String  getHostName()
- Session getMailSession()
- Date    getSentDate()
- int     getSocketConnectionTimeout()
- Email   setFrom(String email)



Software Used

- Java 11.09
- Eclipse IDE 2020-09 (Note: JUnit 4)


Setup

1. Have both "Software Used" downloaded.
2. Import the folder after unzipped into Eclipse.
 - File -> Import -> Maven -> Existing Maven Projects -> (Root to downloaded Directory)
3. Navigate to "org.apache.commons.mail.Email" within the folder.
4. Run whole porject as: Coverage As -> 4 JUnit Test