package oct.rekord.cas.config;

import oct.rekord.cas.interceptor.WebSocketHandShakeInterceptor;
import oct.rekord.cas.websocket.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Rekord
 * @date 2022/5/19 15:28
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandShakeInterceptor handShakeInterceptor;

    @Autowired
    private WebSocketHandler webSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
            .addHandler(webSocketHandler, "/websocket")
            .setAllowedOrigins("*")
            .addInterceptors(handShakeInterceptor);

    }

}
