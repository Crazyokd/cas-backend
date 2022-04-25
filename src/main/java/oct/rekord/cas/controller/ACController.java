package oct.rekord.cas.controller;

import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.AwardCertificateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class ACController {

    @Autowired
    AwardCertificateService acService;

    @PostMapping("/getAllAwardCertificate")
    ReturnData getAllAC(@RequestParam("userId") Integer userId) {
        return acService.getAllAC(userId);
    }

    @PostMapping("/uploadAwardCertificate")
    ReturnData uploadAwardCertificate(@RequestParam("userId") Integer userId, @RequestParam("name") String name, @RequestParam("isValid") String isValid,
                                      @RequestParam("category") String category, @RequestParam("explanation") String explanation, @RequestParam("comment") String comment,
                                      @RequestParam("img") MultipartFile file) {

        return acService.uploadAwardCertificateByUserId(userId, name, isValid, category, explanation, comment, file);
    }
}
