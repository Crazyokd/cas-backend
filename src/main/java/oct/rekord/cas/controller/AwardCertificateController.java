package oct.rekord.cas.controller;

import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.AwardCertificateService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("awardcertificate")
public class AwardCertificateController {

    @Autowired
    AwardCertificateService acService;

    @PostMapping("/get-all")
    ReturnData getAllAC(HttpServletRequest request) {
        return acService.getAllAC(request);
    }

    @PostMapping("/upload")
    ReturnData uploadAwardCertificate(HttpServletRequest request, @RequestParam("name") String name, @RequestParam("isValid") String isValid,
                                      @RequestParam("category") String category, @RequestParam("explanation") String explanation, @RequestParam("comment") String comment,
                                      @RequestParam("semesterName") String semesterName, @RequestParam(name = "acImg", required = false) MultipartFile file) {

        return acService.uploadAwardCertificateByUserId(request, name, isValid, category, explanation, comment, semesterName, file);
    }

    @PostMapping("/get-detail")
    public ReturnData getOne(Integer acId) {
        return acService.getAC(acId);
    }
}
