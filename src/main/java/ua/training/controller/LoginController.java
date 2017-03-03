package ua.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.service.LoginService;

import javax.servlet.http.HttpSession;


/**
 * Created by dima on 28.02.17.
 */
@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "loginLink")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String verifyLogin(@RequestParam("login") String login, @RequestParam("password") String password,
                              HttpSession session){
        User user = loginService.verifyUserLogin(login, password);
        session.setAttribute("user", user);
        if(user.getUserRole().getRole() == Role.USER){
            return "redirect:chat";
        }
        if(user.getUserRole().getRole() == Role.ADMIN){
            return "redirect:admin";
        }
        return "redirect:registration";
    }
}