package oct.rekord.cas.websocket;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
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
//        // 打印 id
//        System.out.println(session.getId());
//        // 打印接受的协议
//        System.out.println(session.getAcceptedProtocol());
//        // 打印 session 是否打开
//        System.out.println(session.isOpen());
//        // 打印 二进制消息大小限制
//        System.out.println(session.getBinaryMessageSizeLimit());
//        // 打印扩展
//        System.out.println(session.getExtensions());
//        // 打印握手头部
//        System.out.println(session.getHandshakeHeaders());
//        // 打印本地地址
//        System.out.println(session.getLocalAddress());
//        // 打印原则
//        System.out.println(session.getPrincipal());
//        // 打印远程地址
//        System.out.println(session.getRemoteAddress());
//        // 打印文本消息大小限制
//        System.out.println(session.getTextMessageSizeLimit());
//        // 打印 uri
//        System.out.println(session.getUri());

        sendString(session, message.getPayload());
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
     * 发生异常，关闭连接
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        SESSION_POOLS.remove(session.getAttributes().get("userId"));
        log.info("websocket发生异常！" ,exception);
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
        log.info("用户" + userId + "断开连接");
        SESSION_POOLS.remove(userId);
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public static void sendString(WebSocketSession fromSession, String message) throws IOException {
        for (WebSocketSession webSocket : SESSION_POOLS.values()) {
            if (webSocket.isOpen() && webSocket != fromSession) {
                webSocket.sendMessage(new TextMessage(message));
            }
        }
        log.debug("webSocket发送消息，内容：{}，当前连接数：{}", message, SESSION_POOLS.size());
    }
}
