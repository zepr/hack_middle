package org.hack;

import lombok.RequiredArgsConstructor;
import org.hack.service.WebSocketListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class SocketConfig implements WebSocketConfigurer {

    private final WebSocketListener listener;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(listener, "/exec");
    }
}
