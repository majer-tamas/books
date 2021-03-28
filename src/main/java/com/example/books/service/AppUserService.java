package com.example.books.service;

import com.example.books.dto.JwtRequest;
import com.example.books.dto.RegexListItem;
import com.example.books.dto.UserDTO;
import com.example.books.model.AppUser;
import com.example.books.repository.AppUserRepository;
import com.example.books.validator.UserDtoValidator;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@Transactional
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder bcryptEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public AppUserService(AppUserRepository appUserRepository,
                          PasswordEncoder bcryptEncoder,
                          JavaMailSender mailSender,
                          UserDtoValidator userDtoValidator
    ) {
        this.appUserRepository = appUserRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.mailSender = mailSender;
    }

    public AppUser createNewUser(UserDTO user, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        String siteURL = getSiteURL(request);
        String randomCode = RandomString.make(64);
        AppUser savedUser = appUserRepository.save(new AppUser(user.getUsername(),
                                                               bcryptEncoder.encode(user.getPassword()),
                                                               user.getEmail(),
                                                               randomCode));
        sendVerificationEmail(savedUser);
        return savedUser;
    }

    public List<RegexListItem> getRegexes() {
        return UserDtoValidator.getREGEXES();
    }

    public boolean checkIfWantedUsernameIsTaken(String wantedUsername) {
        return appUserRepository.findByUsername(wantedUsername) != null;
    }

    public boolean checkIfWantedEmailIsTaken(String wantedEmail) {
        return appUserRepository.findByEmail(wantedEmail) != null;
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    private void sendVerificationEmail(AppUser user) throws UnsupportedEncodingException, MessagingException {
        String toAddress = user.getEmail();
        String fromAddress = "majer.tamas.progmasters@gmail.com";
        String senderName = "Books Application";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Books Application";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = "http://localhost:4200/verify/" + user.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public boolean verifyEmail(String hash) {
        AppUser user = appUserRepository.findByVerificationCode(hash);
        if (user != null) {
            user.setVerificationCode(null);
            user.setVerified(true);
            return true;
        }
        return false;
    }

    public boolean isUserVerified(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) {
            return user.getVerified();
        } else {
            throw new IllegalArgumentException("Invalid username and/or password.");
        }
    }

    public boolean isUserBanned(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user != null) {
            return user.getBanned();
        } else {
            throw new IllegalArgumentException("Invalid username and/or password.");
        }
    }

}
