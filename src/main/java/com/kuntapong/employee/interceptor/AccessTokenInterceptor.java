package com.kuntapong.employee.interceptor;

import com.kuntapong.employee.entity.User;
import com.kuntapong.employee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AccessTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("app-auth");

        if(StringUtils.isEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Optional<User> optionalUser = userRepository.findByToken(token);

        if(!optionalUser.isPresent()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        User user = optionalUser.get();

        //Check token expire time
        if(user.getTokenExpire() != null &&
                LocalDateTime.now().isAfter(user.getTokenExpire())) {
            //Log user out
            doLogout(user);
            //Return 401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        request.setAttribute("user", user);
        return true;
    }

    public void doLogout(User user) {
        user.setToken(null);
        user.setTokenExpire(null);
        userRepository.save(user);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
