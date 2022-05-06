package oct.rekord.cas.interceptor;


import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.common.CodeEnum;
import oct.rekord.cas.common.Token;
import oct.rekord.cas.exception.BaseException;
import oct.rekord.cas.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rekord
 * @date 2022/5/6 15:34
 */

@Slf4j
@Component("authorizationInterceptor")
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    RedisUtil redisUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Object userId = redisUtil.get(Token.USER_TOKEN_PREFIX + token);

        if (token == null || "".equals(token.trim()) || userId == null) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(),"token超时或失效");
        }

        Object lastToken = redisUtil.get(Token.USER_ID_PREFIX + userId);
        if(!token.equals(lastToken)){
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "该账号已在其他设备上登录");
        }

        redisUtil.set(Token.USER_ID_PREFIX + userId, token, Token.TOKEN_EXPIRE);
        redisUtil.set(Token.USER_TOKEN_PREFIX + token, userId.toString(), Token.TOKEN_EXPIRE);

        request.setAttribute("userId",userId);
        return true;
    }
}
