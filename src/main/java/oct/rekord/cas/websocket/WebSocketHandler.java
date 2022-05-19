package oct.rekord.cas.websocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Rekord
 * @date 2022/5/19 14:50
 */

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    /**
     * 在线人数
     */
    private static final AtomicInteger onlineNum = new AtomicInteger();

    /**
     * 会话池
     */
    private static final ConcurrentHashMap<Integer, WebSocketSession> SESSION_POOLS = new ConcurrentHashMap<>();


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println(session.getAttributes().get("userId") + "发来消息：" + message.getPayload());
    }

    /**
     * 连接建立后调用此方法
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Integer userId = Integer.valueOf((String) attributes.get("userId"));
        log.info("用户" + userId + "已连接");
        onlineNum.incrementAndGet();
        // 将当前session加入会话池
        SESSION_POOLS.put(userId, session);
    }

    /**
     * 连接断开后调用此方法
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        onlineNum.decrementAndGet();
        Map<String, Object> attributes = session.getAttributes();
        Integer userId = Integer.valueOf((String) attributes.get("userId"));
        SESSION_POOLS.remove(userId);
    }
}
