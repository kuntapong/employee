package com.kuntapong.employee.service;

import com.kuntapong.employee.entity.User;
import com.kuntapong.employee.repository.UserRepository;
import com.kuntapong.employee.service.exception.AuthServiceLoginFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    public static final int TOKEN_EXPIRE_HOURS = 24;

    private SecureRandom random = new SecureRandom();

    @Autowired
    private UserRepository userRepository;

    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(username, password);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
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
        long longToken = Math.abs( random.nextLong() );
        return Long.toString( longToken, 16 );
    }
}
