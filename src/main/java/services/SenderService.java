package services;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import repository.UserRepository;

@Service
public class SenderService {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private UserRepository userRepository;

    public void send(String mailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailTo);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

    public void activateMe(User u) {
        send(u.getEmail(), "Shopy - мы рады, что вы с нами", "Активируйте аккаунт по ссылке:  http://localhost:8080/activationProfile/" + u.getActivationCode());
    }
}
