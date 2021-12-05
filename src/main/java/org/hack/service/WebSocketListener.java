package org.hack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hack.domain.bean.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketListener extends TextWebSocketHandler {

    private WebSocketSession session; // Un seul client

    private Map<String, Object> locks = new HashMap<>();
    private static ThreadLocal<Message> messages = new ThreadLocal<>();

    private final ObjectMapper mapper; // Thread safe

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        Message m = null;
        try {
            m = mapper.readValue(message.getPayload(), Message.class);
        } catch (Exception e) {
            return; // Message ignore (test de connexion ?)
        }

        messages.set(m);

        Object o;
        synchronized (locks) {
            o = locks.remove(m.getId());
        }

        if (o != null) {
            synchronized (o) {
                o.notifyAll();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)  {

        if (this.session != null) {
            try { this.session.close(); } catch (Exception e) {}
        }
        this.session = session;
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    }

    public Message call(Message input) throws IOException {

        if (session == null) return null;

        input.setId(UUID.randomUUID().toString());

        synchronized (this) {
            TextMessage message = new TextMessage(mapper.writeValueAsString(input));
            session.sendMessage(message);

            // Synchro
            Object o = new Object();
            synchronized (locks) {
                locks.put(input.getId(), o);
            }
            synchronized (o) {
                try { o.wait(); } catch (Exception e) {}
            }
        }

        Message out = messages.get();
        messages.remove();

        return out;
    }
}
