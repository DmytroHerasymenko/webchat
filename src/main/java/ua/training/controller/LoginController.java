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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
                              HttpSession session, HttpServletRequest request, HttpServletResponse response){
        User user = loginService.verifyUserLogin(login, password);
        session.setAttribute("user", user);
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("JSESSIONID")){
                cookie.setHttpOnly(false);
                response.addCookie(cookie);
                break;
            }
        }
        if(user.getUserRole().getRole() == Role.USER){
            return "redirect:chat";
        }
        if(user.getUserRole().getRole() == Role.ADMIN){
            return "redirect:admin";
        }
        return "redirect:registration";
    }
}