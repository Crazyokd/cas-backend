package oct.rekord.cas.common;

import oct.rekord.cas.util.RandomNumberUtil;

/**
 * @author Rekord
 * @date 2022/5/6 15:34
 */
public class Token {
    public static final String USER_TOKEN_PREFIX = "user:token:";

    public static final String USER_ID_PREFIX = "user:id:";

    public static final long TOKEN_EXPIRE = 60 * 30;

    private Token(){}

    public static String createToken() {
        return RandomNumberUtil.getUUID();
    }

}
