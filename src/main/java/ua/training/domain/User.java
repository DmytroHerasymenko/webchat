package ua.training.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dima on 28.02.17.
 */
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "login", unique = true)
    private String login;
    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserRole userRole;
    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessage;
    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessage;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BlackList blackList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public List<Message> getReceivedMessage() {
        return receivedMessage;
    }

    public void setReceivedMessage(List<Message> receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    public List<Message> getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(List<Message> sentMessage) {
        this.sentMessage = sentMessage;
    }

    public BlackList getBlackList() {
        return blackList;
    }

    public void setBlackList(BlackList blackList) {
        this.blackList = blackList;
    }
}
