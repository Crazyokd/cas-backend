package oct.rekord.cas.interceptor;

import oct.rekord.cas.common.Token;
import oct.rekord.cas.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author Rekord
 * @date 2022/5/19 15:15
 */

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {


    @Autowired
    RedisUtil redisUtil;


    /**
     * 握手之前。若返回false则不建立连接，若返回true则建立连接
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param attributes
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("websocket连接握手");

        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        String token = servletServerHttpRequest.getServletRequest().getParameter("token");
        Object userId = redisUtil.get(Token.USER_TOKEN_PREFIX + token);

        if (token == null || "".equals(token.trim()) || userId == null) {
            System.out.println("尚未登录");
            return false;
        }

        Object lastToken = redisUtil.get(Token.USER_ID_PREFIX + userId);
        if(!token.equals(lastToken)){
            System.out.println("该账号已在其他设备上登录");
            return false;
        }

        redisUtil.set(Token.USER_ID_PREFIX + userId, token, Token.TOKEN_EXPIRE);
        redisUtil.set(Token.USER_TOKEN_PREFIX + token, userId.toString(), Token.TOKEN_EXPIRE);

        attributes.put("userId", userId);
        return true;
    }

    /**
     * 握手成功之后调用该方法
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
