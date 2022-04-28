package oct.rekord.cas.controller;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ReturnData apply(Application application) {
        return applicationService.addApplication(application);
    }

    @PostMapping("/get-all")
    public ReturnData getAll(Integer userId) {
        return applicationService.getAllApplication(userId);
    }

    @PostMapping("/process")
    public ReturnData process(Application application) {
        return applicationService.processApplication(application);
    }
}
