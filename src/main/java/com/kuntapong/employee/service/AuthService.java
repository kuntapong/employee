package com.kuntapong.employee.service;

import com.kuntapong.employee.entity.User;
import com.kuntapong.employee.repository.UserRepository;
import com.kuntapong.employee.service.exception.AuthServiceLoginFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class AuthService {

    public static final int TOKEN_EXPIRE_HOURS = 24;

    private SecureRandom random = new SecureRandom();
    private Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password) {
        //Do login
        //Find user by username and password
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            //Generate token
            String token = generateToken(username);
            user.setToken(token);
            user.setTokenExpire(LocalDateTime.now().plusHours(TOKEN_EXPIRE_HOURS));
            userRepository.save(user);

            return token;
        }else {
            throw new AuthServiceLoginFailException();
        }
    }

    private String generateToken( String username ) {
        byte[] randomBytes = new byte[24];
        random.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
