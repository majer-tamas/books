package com.example.books.validator;

import com.example.books.dto.RegexListItem;
import com.example.books.dto.UserDTO;
import com.example.books.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoValidator implements Validator {

    private final AppUserRepository appUserRepository;
    private static final List<RegexListItem> REGEXES = new ArrayList<>();
    private static final String USERNAME_REGEX = "^[a-zA-Z\\d][a-zA-Z\\d]{1,28}[a-zA-Z\\d]$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,20}$";
    private static final String EMAIL_REGEX = "^[a-z0-9!#$%&'*+\\/=?^_‘{|}~-]+(?:\\.[a-z0-9!#$%&'*+\\/=?^_‘{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";


    @Autowired
    public UserDtoValidator(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
        REGEXES.add(new RegexListItem(USERNAME, USERNAME_REGEX));
        REGEXES.add(new RegexListItem(EMAIL, EMAIL_REGEX));
        REGEXES.add(new RegexListItem(PASSWORD, PASSWORD_REGEX));
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDTO userDTO = (UserDTO) o;
        String wantedUsername = userDTO.getUsername();
        String wantedEmail = userDTO.getEmail();
        String wantedPassword = userDTO.getPassword();

        if (wantedUsername == null) {
            errors.rejectValue(USERNAME, "username.empty");
        } else if (appUserRepository.findByUsername(wantedUsername) != null) {
            errors.rejectValue(USERNAME, "username.already.taken");
        } else if (!wantedUsername.matches(USERNAME_REGEX)) {
            errors.rejectValue(USERNAME, "username.not.valid");
        }

        if (wantedEmail == null) {
            errors.rejectValue(EMAIL, "email.empty");
        } else if (appUserRepository.findByEmail(wantedEmail) != null) {
            errors.rejectValue(EMAIL, "email.already.taken");
        } else if (!wantedEmail.matches(EMAIL_REGEX)) {
            errors.rejectValue(EMAIL, "email.wrong.formed");
        }

        if (wantedPassword == null) {
            errors.rejectValue(PASSWORD, "password.wrong.length");
        } else if (!wantedPassword.matches(PASSWORD_REGEX)) {
            errors.rejectValue(PASSWORD, "password.wrong.formed");
        }
    }

    public static List<RegexListItem> getREGEXES() {
        return REGEXES;
    }

}
