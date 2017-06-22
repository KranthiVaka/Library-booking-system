package admin;

import group4u_booking.UserInfo;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Medis
 */
public class EmailSender {

    Properties props;
    Session session;
    Message message;

    EmailSender() throws AddressException, IOException {
        final String username = UserInfo.getInstance().getEmailSender();
        final String password = UserInfo.getInstance().getEmailPassword();

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(UserInfo.getInstance().getEmailSender()));
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean resetPassword(String newPassword, String recipient, String userName) throws Exception {
        boolean isSent = false;
        try {
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("New password to BTH iBooking");
            message.setContent("Dear " + userName + ", "
                    + "<br/><br/>Here is your new password: " + newPassword
                    + "<br/>Consider to change password for security reasons!"
                    + "<br/><br/>Best Regards,"
                    + "<br/>Group4u Team.", "text/html; charset=utf-8");
//            message.setText("Dear " + userName + ", "
//                    + "\n\nHere is your new password: " + newPassword
//                    + "\nConsider to change password for security reasons!"
//                    + "\n\nBest Regards,"
//                    + "\nGroup4u Team.");

            Transport.send(message);
            isSent = true;
            System.out.println("Email sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return isSent;
    }
    
    
        public boolean userCreated(String newPassword, String recipient, String userID, String firstName) throws Exception {
        boolean isSent = false;
        try {
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("User account BTH iBooking");
            message.setContent("Dear " + firstName + ", "
                    +"<br/><br/>A new user created at BTH iBooking, please use the following information to login."
                    +"<br/><br/><br/>User ID: " + userID
                    + "<br/>Password: " + newPassword
                    + "<br/><br/>Consider to change password for security reasons!"
                    + "<br/><br/>Best Regards,"
                    + "<br/>Group4u Team.", "text/html; charset=utf-8");
//            message.setText("Dear " + userName + ", "
//                    + "\n\nHere is your new password: " + newPassword
//                    + "\nConsider to change password for security reasons!"
//                    + "\n\nBest Regards,"
//                    + "\nGroup4u Team.");

            Transport.send(message);
            isSent = true;
            System.out.println("Email sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return isSent;
    }
}
