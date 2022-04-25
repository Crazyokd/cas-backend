package oct.rekord.cas.service.impl;


import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.User;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.UserInfoDAO;
import oct.rekord.cas.service.UserService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoDAO userInfoDAO;

    @Override
    public ReturnData register(HttpServletRequest request, String username, String password, String phone) {
        try {
            userInfoDAO.insertUser(new User(username, password, phone));
            return ReturnData.success("注册成功");
        } catch (Exception e) {
            return ReturnData.fail(502, "该用户名已被注册使用");
        }

    }

    @Override
    public ReturnData login(HttpServletRequest request, String username, String password, String agent) {
        Map<String, Object> data = new LinkedHashMap<>();

        try {
            User user = userInfoDAO.selectUserInfoByUsernameAndPassword(username, password);
            data.put("账号ID", user.getUserId());
//        data.put("用户名", user.getUsername());
            data.put("级别", user.getLevel());
            data.put("邮箱", user.getEmail());
            data.put("头像", user.getHeadImgPath());
            data.put("手机号", user.getPhone());
            data.put("学号", user.getSn());
            data.put("学号密码", user.getSnPassword());

            return ReturnData.success(data);
        } catch (Exception e) {
            return ReturnData.fail(502, "登录失败");
        }

    }
}
