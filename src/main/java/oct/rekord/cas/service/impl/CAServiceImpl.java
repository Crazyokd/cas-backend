package oct.rekord.cas.service.impl;

import oct.rekord.cas.bean.CA;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.CADAO;
import oct.rekord.cas.service.CAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Service("caService")
public class CAServiceImpl implements CAService {
    @Autowired
    CADAO caDAO;

    @Override
    public ReturnData getCAResult(HttpServletRequest request, String userId) {
        Integer userIdInteger = Integer.valueOf(userId);
        List<CA> caList = new LinkedList<>();
        caList.addAll(caDAO.selectAwardCertificateByUserId(userIdInteger));
        caList.addAll(caDAO.selectParticipatedActivityByUserId(userIdInteger));
        return ReturnData.success(caList);
    }
}
