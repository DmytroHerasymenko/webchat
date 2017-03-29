package ua.training.service;

import ua.training.dto.UserDTO;

import java.util.List;

/**
 * Created by dima on 28.03.17.
 */
public interface UserService {
    List<UserDTO> getAllUsersWithoutAdmins();
    void addUserToBanList(UserDTO userDTO);
    void deleteUserFromBanList(Long id);
 }
