package oct.rekord.cas.controller;

import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.CAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CAController {
    @Autowired
    CAService caService;
    @PostMapping("/getCAResult")
    ReturnData getCAResult(HttpServletRequest request, String userId) {
        return caService.getCAResult(request, userId);
    }
}


