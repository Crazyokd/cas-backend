package oct.rekord.cas.controller;

import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.HeadImgService;
import oct.rekord.cas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HeadImgService headImgService;

    @PostMapping("/login")
    public ReturnData login(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, String agent) {
        return userService.login(request, username, password, agent);
    }

    @PostMapping("/register")
    public ReturnData register(HttpServletRequest request, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("phone") String phone) {
        return userService.register(request, username, password, phone);
    }

    @PostMapping("/getAvatar")
    public ReturnData getHeadImg(HttpServletRequest request, @RequestParam("userid") String userid) {
        try {
            return headImgService.getHeadImg(request, userid);
        }catch (Exception e) {
            return ReturnData.fail(501, "头像获取失败");
        }
    }

    @PostMapping("/setAvatar")
    public ReturnData setHeadImg(HttpServletRequest request, @RequestParam("userid") String userid, @RequestParam("img") MultipartFile file) {
        return headImgService.setHeadImg(request, userid, file);
    }
}
