package ua.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.dto.BanDTO;
import ua.training.dto.ResponseBanDTO;
import ua.training.dto.ResponseDTO;
import ua.training.dto.UserDTO;
import ua.training.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by dima on 23.03.17.
 */
@RestController
public class AdminRestController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAllUsersExceptAdmins (HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN) {
            ResponseDTO banDTO = new ResponseDTO();
            banDTO.setAuth("no");
            return new ResponseEntity<ResponseDTO>(banDTO, HttpStatus.OK);
        }
        List<UserDTO> users = userService.getAllUsersWithoutAdmins();
        ResponseDTO ban = new ResponseDTO();
        ban.setAuth("yes");
        List<BanDTO> bans = new ArrayList<>();
        for(UserDTO u : users){
            BanDTO banDTO = new BanDTO();
            banDTO.setUserId(u.getId());
            banDTO.setLogin(u.getLogin());
            if(u.isBanned()){
                ResponseEntity<ResponseBanDTO> removeFromList =
                        methodOn(AdminRestController.class).deleteFromBanList(u.getId(), session);
                Link link = linkTo(removeFromList).withRel("remove");
                banDTO.add(link);
            } else {
                ResponseEntity<ResponseBanDTO> addToList =
                        methodOn(AdminRestController.class).addToBanList(u, session);
                Link link = linkTo(addToList).withRel("add");
             banDTO.add(link);
            }
            bans.add(banDTO);
        }
        ban.setUsers(bans);
        return new ResponseEntity<ResponseDTO>(ban, HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/add", method = RequestMethod.GET)
    public ResponseEntity<ResponseBanDTO> addToBanList(@RequestBody UserDTO userDTO, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN) {
            ResponseBanDTO banDTO = new ResponseBanDTO();
            banDTO.setAuth("no");
            return new ResponseEntity<>(banDTO, HttpStatus.OK);
        }
        userService.addUserToBanList(userDTO);
        ResponseBanDTO responseBanDTO = new ResponseBanDTO();
        responseBanDTO.setSuccess("yes");
        return new ResponseEntity<>(responseBanDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseBanDTO> deleteFromBanList(@PathVariable("id")Long id, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null || user.getUserRole().getRole() != Role.ADMIN) {
            ResponseBanDTO banDTO = new ResponseBanDTO();
            banDTO.setAuth("no");
            return new ResponseEntity<>(banDTO, HttpStatus.OK);
        }
        userService.deleteUserFromBanList(id);
        ResponseBanDTO responseBanDTO = new ResponseBanDTO();
        responseBanDTO.setSuccess("yes");
        return new ResponseEntity<>(responseBanDTO, HttpStatus.OK);
    }
}
