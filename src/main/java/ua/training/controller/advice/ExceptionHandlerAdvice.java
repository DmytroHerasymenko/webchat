package ua.training.controller.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.training.exception.LoginServiceException;
import ua.training.exception.UserLoginServiceException;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 28.02.17.
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(LoginServiceException.class)
    public String loginServiceExceptionHandler(LoginServiceException e, HttpSession session){
        session.setAttribute("error", e.getMessage());
        return "redirect:registration";
    }
    @ExceptionHandler(UserLoginServiceException.class)
    public String verificationUserExceptionHandler(UserLoginServiceException e, HttpSession session){
        session.setAttribute("error", e.getMessage());
        return "redirect:login";
    }
}
