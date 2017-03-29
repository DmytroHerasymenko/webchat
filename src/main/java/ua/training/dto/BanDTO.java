package ua.training.dto;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by dima on 23.03.17.
 */
public class BanDTO extends ResourceSupport {
    private String login;
    private Long userId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
