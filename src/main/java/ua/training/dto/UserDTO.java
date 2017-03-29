package ua.training.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Created by dima on 28.02.17.
 */
public class UserDTO {
    private Long id;
    @NotNull(message = "field name can not be null")
    //\\w все цифры и буквы. + один и много знаков
    @Pattern(regexp = "[\\w]+", message = "field name can not satisfy the pattern")
    private String name;
    @NotNull(message = "field login can not be null")
    @Email(message = "login isn't email")
    private String login;
    @NotNull(message = "field password can not be null")
    @Size(min = 2, max = 12, message = "size should be from 2 to 12")
    private String password;

    private boolean isBanned;

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
