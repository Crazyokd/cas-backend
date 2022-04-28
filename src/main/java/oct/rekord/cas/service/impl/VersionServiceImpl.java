package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.Version;
import oct.rekord.cas.common.CodeEnum;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.VersionDAO;
import oct.rekord.cas.service.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class VersionServiceImpl implements VersionService {
    @Value("${my.host}")
    String host;

    @Value("${server.port}")
    String port;

    @Autowired
    VersionDAO versionDAO;

    public ReturnData getLatestVersion() {
        Version lastVersion = versionDAO.getLatestVersion();
        if (lastVersion == null) {
            return ReturnData.fail(CodeEnum.SYSTEM_ERROR.getCode(), "暂无apk下载");
        } else {
            lastVersion.setApkPath("http://" + host + ":" + port + "/home/rekord/cas/apk/" + lastVersion.getApkPath());
            return ReturnData.success(lastVersion);
        }


    }
}
