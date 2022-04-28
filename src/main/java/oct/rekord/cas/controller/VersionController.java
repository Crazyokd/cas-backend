package oct.rekord.cas.controller;

import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("version")
public class VersionController {
    @Autowired
    VersionService versionService;

    @GetMapping("/get-latest")
    public ReturnData getLastVersion() {
        return versionService.getLatestVersion();
    }
}
