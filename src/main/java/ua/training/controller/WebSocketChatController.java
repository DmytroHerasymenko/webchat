package ua.training.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ua.training.dto.UserDTO;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dima on 07.03.17.
 */
@Controller
public class WebSocketChatController extends TextWebSocketHandler {
    @Autowired
    private WebChatService webChatService;

    private final Map<String, WebSocketSession> clients = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String mes = message.getPayload();
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap<String, String>>(){}.getType();
        Map<String, String> clientRequest = gson.fromJson(mes, gsonType);
        if(clientRequest.containsKey("sessionId")){
            String idSession = clientRequest.get("sessionId");
            UserDTO userDTO = webChatService.getUserBySessionId(idSession);
            if(userDTO == null){
                session.sendMessage(new TextMessage("{'auth':'no'}"));
                return;
            } else {
                clients.put(userDTO.getName(), session);
                session.sendMessage(new TextMessage("{'auth':'yes'}"));
                return;
            }


        }
    }
}
