package ua.training.service;

import javafx.util.Pair;
import org.springframework.stereotype.Service;
import ua.training.dto.UserDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by dima on 09.03.17.
 */
@Service
public interface WebChatService {
    UserDTO getUserBySessionId(String sessionId);
    List<Pair<String, String>> getMessagesForUser(UserDTO userDTO);
    void saveBroadcastMessage(String login, String message);
    void savePrivateMessage(String receiver, String sender, String message);
    void invalidateHttpSession(String login);
}
