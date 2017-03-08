package ua.training.controller.validation;

import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 07.03.17.
 */
public class Validator {

    public static Boolean validation(BindingResult bindingResult, HttpSession session){
        if(bindingResult.getAllErrors().size() != 0) {
            session.setAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return false;
        }
        return true;
    }
}
