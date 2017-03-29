package ua.training.repository;

import ua.training.dto.UserDTO;

/**
 * Created by dima on 28.03.17.
 */
public interface BlackListRepositoryCustom {
    void addUserToBlackList(UserDTO userDTO);
    void removeUserFromBlackList(Long id);
}
