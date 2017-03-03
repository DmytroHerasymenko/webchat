package ua.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.dto.UserDTO;
import ua.training.service.LoginService;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 28.02.17.
 */
@Controller
public class RegistrationController {
    @Autowired
    LoginService loginService;
    @RequestMapping(value = "/registration", method = RequestMethod.GET, name = "registrationLink")
    public ModelAndView registration(@ModelAttribute("registration") UserDTO user){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("registration");
        return mv;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationHandler(@ModelAttribute("registration") @Validated UserDTO user,
                                            BindingResult bindingResult, HttpSession session){
        if(bindingResult.getAllErrors().size() != 0) {
            session.setAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
            return "redirect:registration";
        }
        loginService.registrationUser(user);
        return "redirect:/";
    }

}
