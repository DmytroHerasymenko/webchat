package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.training.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 02.03.17.
 */
@Controller
public class ChatController {
    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView chatPage(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if(user == null){
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("chat");
            modelAndView.addObject("username", user.getName());
        }
        return modelAndView;
    }
}
