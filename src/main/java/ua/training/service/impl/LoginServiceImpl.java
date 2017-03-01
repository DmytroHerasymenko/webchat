package ua.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.domain.UserRole;
import ua.training.dto.UserDTO;
import ua.training.exception.LoginServiceException;
import ua.training.repository.UserRepository;
import ua.training.service.LoginService;

/**
 * Created by dima on 28.02.17.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void registrationUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        UserRole role = new UserRole();
        role.setRole(Role.USER);
        user.setUserRole(role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new LoginServiceException("user.exist");
        }
    }
}
