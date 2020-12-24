package andrew.projects.uniapi.Services;

import andrew.projects.uniapi.Entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    public void sendValidationCode(Account a) {
        sendEmail(a.getEmail(), "Unitest Activation Code", "Hello! Click here to activate your account  http://localhost:8080/account/activate/" + a.getActivationCode());
    }

    public void sendEmail(String to, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
        new Thread(new asyncSendSimpleMessage(msg)).start();
    }



    class asyncSendSimpleMessage implements Runnable {
        SimpleMailMessage sm;

        public asyncSendSimpleMessage(SimpleMailMessage msg) {

            this.sm = msg;
        }

        public void run() {
            mailSender.send(sm);
        }

    }
    
}