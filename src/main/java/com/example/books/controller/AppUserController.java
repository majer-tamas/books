package com.example.books.controller;

import com.example.books.dto.*;
import com.example.books.model.AppUser;
import com.example.books.security.JwtTokenUtil;
import com.example.books.security.JwtUserDetailsService;
import com.example.books.service.AppUserService;
import com.example.books.validator.UserDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class AppUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AppUserService appUserService;
    private final UserDtoValidator userDtoValidator;

    @Autowired
    public AppUserController(AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil,
                             JwtUserDetailsService userDetailsService,
                             AppUserService appUserService,
                             UserDtoValidator userDtoValidator
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.appUserService = appUserService;
        this.userDtoValidator = userDtoValidator;
    }

    @InitBinder(value = "userDTO")
    protected void init(WebDataBinder binder) {
        binder.addValidators(userDtoValidator);
    }

    @GetMapping("/test")
    public ResponseEntity<StringResponse> test() {
        logger.info("test() works.");
        return new ResponseEntity<>(new StringResponse("oké"), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> createNewUser(@RequestBody @Valid UserDTO userDTO, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
        logger.info("User created.");
        return ResponseEntity.ok(appUserService.createNewUser(userDTO, request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        if (!appUserService.isUserVerified(authenticationRequest.getUsername())) {
            logger.info("Not verified");
            throw new IllegalArgumentException("The email is not verified. Please check your emails.");
        }
        if (appUserService.isUserBanned(authenticationRequest.getUsername())) {
            logger.info("Banned user.");
            throw new IllegalArgumentException("User with username " + authenticationRequest.getUsername() + " is banned.");
        }
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.info("User successfully authenticated.");
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            logger.info("Disabled user");
            throw new IllegalArgumentException("User with username " + username + " is disabled.");
        } catch (BadCredentialsException e) {
            logger.info("Invalid username and/or password.");
            throw new IllegalArgumentException("Invalid username and/or password.");
        }
    }

    @GetMapping("/verify/{hash}")
    public ResponseEntity<Void> verifyEmail(@PathVariable("hash") String hash) {
        if (appUserService.verifyEmail(hash)) {
            logger.info("Email verified.");
            return ResponseEntity.ok().build();
        }
        logger.info("Invalid hash.");
        throw new IllegalArgumentException("Invalid hash.");
    }

    @GetMapping("/getRegexes")
    public ResponseEntity<List<RegexListItem>> getRegexes() {
        return ResponseEntity.ok(appUserService.getRegexes());
    }

    @PostMapping("/checkIfWantedUsernameIsTaken")
    public ResponseEntity<Boolean> checkIfWantedUsernameIsTaken(@RequestBody String wantedUsername) {
        boolean taken = appUserService.checkIfWantedUsernameIsTaken(wantedUsername);
        return ResponseEntity.ok(taken);
    }

    @PostMapping("/checkIfWantedEmailIsTaken")
    public ResponseEntity<Boolean> checkIfWantedEmailIsTaken(@RequestBody String wantedEmail) {
        boolean taken = appUserService.checkIfWantedEmailIsTaken(wantedEmail);
        return ResponseEntity.ok(taken);
    }
}
