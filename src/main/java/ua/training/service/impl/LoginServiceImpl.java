package ua.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.domain.UserRole;
import ua.training.dto.UserDTO;
import ua.training.exception.LoginServiceException;
import ua.training.exception.UserLoginServiceException;
import ua.training.repository.UserRepository;
import ua.training.repository.UserRoleRepository;
import ua.training.service.LoginService;

import javax.servlet.http.HttpSession;

/**
 * Created by dima on 28.02.17.
 */
@Service
@PropertySource("classpath:messages_en.properties")
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Override
    public void registrationUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        UserRole role = userRoleRepository.getUserRoleByRole(Role.USER);
        user.setUserRole(role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new LoginServiceException("reg.exist");
        }
    }

    @Override
    public User verifyUserLogin(String login, String password) {
        User user = userRepository.getUserByLogin(login);
        if (!(user != null && user.getPassword().equals(password))) {
            throw new UserLoginServiceException("reg.incorrect");
        }
        return user;
    }
}
