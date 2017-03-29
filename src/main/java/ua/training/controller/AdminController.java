package ua.training.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.training.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 23.03.17.
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) return "redirect:/";
        return "admin";
    }

}
