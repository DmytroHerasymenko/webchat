package ua.training.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.domain.Message;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.listener.CreateHttpSessionListener;
import ua.training.repository.MessageRepository;
import ua.training.service.WebChatService;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by dima on 09.03.17.
 */
@Service
public class WebChatServiceImpl implements WebChatService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private RedisDAO redisDAO;
    @Override
    public UserDTO getUserBySessionId(String sessionId) {
        HttpSession session = CreateHttpSessionListener.getSessionById(sessionId);
        if(session == null) return null;
        User user = (User) session.getAttribute("user");
        if(user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    @Override
    public List<Pair<String, String>> getMessagesForUser(UserDTO userDTO) {
        List<Message> privateMessages = messageRepository.getMessagesByReceiver(userDTO.getLogin());
        List<Message> broadcastMessages = redisDAO.getAllBroadcastMessages();
        List<Pair<String, String>> allMessages = new ArrayList<>();
        for(Message message : privateMessages){
            allMessages.add(new Pair<>(message.getSender().getLogin(), message.getBody()));
        }
        messageRepository.deleteMessagesByReceiver(userDTO.getLogin());
        return allMessages;
    }

    @Override
    public void saveBroadcastMessage(String login, String message) {

    }

    @Override
    public void savePrivateMessage(String receiver, String sender, String message) {

    }

    @Override
    public void invalidateHttpSession(String login) {

    }
}
