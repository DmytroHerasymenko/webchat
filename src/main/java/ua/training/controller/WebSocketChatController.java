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
import ua.training.service.WebChatService;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
                clients.put(userDTO.getLogin(), session);
                session.sendMessage(new TextMessage("{'auth':'yes'}"));
                List<Map.Entry<String, String>> messages = webChatService.getMessagesForUser(userDTO);
                for(Map.Entry<String, String> item : messages){
                    JsonObject jMessage = new JsonObject();
                    jMessage.addProperty(item.getKey(), item.getValue());
                    session.sendMessage(new TextMessage(jMessage.toString()));
                }
                return;
            }
        }
        if(clients.values().contains(session) && clientRequest.containsKey("list")){
            Set<String> clientsLogin = clients.keySet();
            JsonObject clientsList = new JsonObject();
            clientsList.add("list", gson.toJsonTree(clientsLogin));
            session.sendMessage(new TextMessage(clientsList.toString()));
            return;
        }
        if(clients.values().contains(session) && clientRequest.containsKey("broadcast")){
            String login = getLoginBySession(session);
            JsonObject broadcastMessage = new JsonObject();
            String mess = clientRequest.get("broadcast");
            broadcastMessage.addProperty(login, mess);
            webChatService.saveBroadcastMessage(login, mess);
            for(WebSocketSession clientSession : clients.values()){
                clientSession.sendMessage(new TextMessage(broadcastMessage.toString()));
            }
            return;
        }
        if(clients.values().contains(session) && clientRequest.containsKey("name")){
            String receiver = clientRequest.get("name");
            String mess = clientRequest.get("message");
            String sender = getLoginBySession(session);
            WebSocketSession receiverSession = clients.get(receiver);
            if(receiverSession == null){
                webChatService.savePrivateMessage(receiver, sender, mess);
                return;
            } else {
                JsonObject privateMessage = new JsonObject();
                privateMessage.addProperty("name", sender);
                privateMessage.addProperty("message", mess);
                receiverSession.sendMessage(new TextMessage(privateMessage.toString()));
                return;
            }
        }
        if(clients.values().contains(session) && clientRequest.containsKey("disconnect")){
            String login = getLoginBySession(session);
            clients.remove(login);
            webChatService.invalidateHttpSession(login);
            session.sendMessage(new TextMessage("{'disconnect':'yes'}"));
            return;
        }
    }
    private String getLoginBySession(WebSocketSession session){
        for(Map.Entry<String, WebSocketSession> item : clients.entrySet()){
            if(item.getValue() == session){
                return item.getKey();
            }
        }
        return null;
    }
}
