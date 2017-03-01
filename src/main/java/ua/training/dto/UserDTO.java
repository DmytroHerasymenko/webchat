package ua.training.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Created by dima on 28.02.17.
 */
public class UserDTO {
    @NotNull
    //\\w все цифры и буквы. + один и много знаков
    @Pattern(regexp = "[\\w]+")
    private String name;
    @NotNull
    @Email
    private String login;
    @NotNull
    @Size(min = 2, max = 12)
    private String password;

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
}
