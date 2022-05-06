package oct.rekord.cas.controller;

import oct.rekord.cas.bean.Application;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/apply")
    public ReturnData apply(HttpServletRequest request, Application application) {
        return applicationService.addApplication(request, application);
    }

    @PostMapping("/get-all")
    public ReturnData getAll(HttpServletRequest request) {
        return applicationService.getAllApplication(request);
    }

    @PostMapping("/get-new")
    public ReturnData getNew(HttpServletRequest request) {
        return applicationService.getNewApplication(request);
    }

    @PostMapping("/get-my")
    public ReturnData getMy(HttpServletRequest request) {
        return applicationService.getMyApplication(request);
    }

    @PostMapping("/process")
    public ReturnData process(Application application) {
        return applicationService.processApplication(application);
    }
}
