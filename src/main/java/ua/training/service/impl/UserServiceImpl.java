package ua.training.service.impl;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import ua.training.domain.Role;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.exception.BanUserException;
import ua.training.repository.BlackListRepository;
import ua.training.repository.UserRepository;
import ua.training.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by dima on 28.03.17.
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BlackListRepository blackListRepository;
    private ResourceBundleMessageSource bean;

    public UserServiceImpl(UserRepository userRepository, BlackListRepository blackListRepository){
        this.userRepository = userRepository;
        this.blackListRepository = blackListRepository;
        bean = new ResourceBundleMessageSource();
        bean.setBasenames("db", "messages_en");
    }

    @Override
    public List<UserDTO> getAllUsersWithoutAdmins() {
        List<User> users = userRepository.findAll();
        return users.stream().filter(user -> user.getUserRole().getRole() != Role.ADMIN)
                .map(user -> {
                UserDTO userDTO = new UserDTO();
                userDTO.setName(user.getName());
                userDTO.setLogin(user.getLogin());
                userDTO.setId(user.getId());
                if (user.getBlackList() != null) {
                    userDTO.setBanned(true);
                } else {
                    userDTO.setBanned(false);
                }
                return userDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void addUserToBanList(UserDTO userDTO) {
        try {
            blackListRepository.addUserToBlackList(userDTO);
        } catch (Exception e){
            throw new BanUserException(
                    bean.getMessage("reg.addBanUserError", null, Locale.ENGLISH));
        }
    }

    @Override
    public void deleteUserFromBanList(Long id){
        try {
            blackListRepository.removeUserFromBlackList(id);
        } catch (Exception e){
            throw new BanUserException(
                    bean.getMessage("reg.removeBanUserError", null, Locale.ENGLISH));
        }
    }

}
