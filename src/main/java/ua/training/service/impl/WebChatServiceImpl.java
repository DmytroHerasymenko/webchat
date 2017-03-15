package ua.training.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.training.domain.Message;
import ua.training.domain.User;
import ua.training.dto.UserDTO;
import ua.training.listener.CreateHttpSessionListener;
import ua.training.repository.MessageRepository;
import ua.training.repository.UserRepository;
import ua.training.repository.dao.RedisDAO;
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
    @Autowired
    private UserRepository userRepository;
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
        List<String> broadcastMessages = redisDAO.getAllBroadcastMessages();
        List<Pair<String, String>> allMessages = new ArrayList<>();
        for(Message message : privateMessages){
            allMessages.add(new Pair<>(message.getSender().getLogin(), message.getBody()));
        }
        messageRepository.deleteMessagesByReceiver(userDTO.getLogin());
        for(String broadcastMessage : broadcastMessages){
            String[] arrayBroadcast = broadcastMessage.split(":");
            allMessages.add(new Pair<>(arrayBroadcast[0], arrayBroadcast[1]));
        }
        return allMessages;
    }

    @Override
    public void saveBroadcastMessage(String login, String message) {
        redisDAO.saveMessage(login, message);
    }

    @Override
    public void savePrivateMessage(String receiver, String sender, String message) {
        User recev = userRepository.getUserByLogin(receiver);
        User sendr = userRepository.getUserByLogin(sender);
        Message mess = new Message();
        mess.setBody(message);
        mess.setSender(sendr);
        mess.setReceiver(recev);
        messageRepository.save(mess);
    }

    @Override
    public void invalidateHttpSession(String login) {
        HttpSession session = CreateHttpSessionListener.getSessionByLogin(login);
        if(session != null){
            CreateHttpSessionListener.removeSession(session.getId());
            session.invalidate();
        }
    }
}
