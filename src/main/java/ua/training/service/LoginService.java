package ua.training.service;

import org.springframework.stereotype.Service;
import ua.training.domain.User;
import ua.training.dto.UserDTO;

/**
 * Created by dima on 28.02.17.
 */
@Service
public interface LoginService {
    void registrationUser(UserDTO user);
    User verifyUserLogin (String login, String password);

}
