package oct.rekord.cas.controller;

import oct.rekord.cas.bean.AuthorityRecord;
import oct.rekord.cas.bean.User;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ReturnData login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "version", required = false) String version) {
        return userService.login(username, password, version);
    }

    @PostMapping("/register")
    public ReturnData register(User user) {
        return userService.register(user);
    }

    @PostMapping("/get-avatar")
    public ReturnData getHeadImg(HttpServletRequest request) {
            return userService.getHeadImg(request);
    }

    @PostMapping("/set-avatar")
    public ReturnData setHeadImg(HttpServletRequest request, @RequestParam("headImg") MultipartFile file) {
        return userService.setHeadImg(request, file);
    }

    @PostMapping("/get-code")
    public ReturnData getCode(@RequestParam("phone") String phone, @RequestParam(value = "bits", required = false) Integer bits) {
        return userService.getCode(phone, bits);
    }

    @PostMapping("/authorize")
    public ReturnData authorize(AuthorityRecord authorityRecord) {
        return userService.authorize(authorityRecord);
    }
}
