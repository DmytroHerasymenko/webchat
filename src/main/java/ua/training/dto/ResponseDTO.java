package ua.training.dto;

import java.util.List;

/**
 * Created by dima on 23.03.17.
 */
public class ResponseDTO extends AuthDTO{
    private List<BanDTO> users;

    public List<BanDTO> getUsers() {
        return users;
    }

    public void setUsers(List<BanDTO> users) {
        this.users = users;
    }
}
