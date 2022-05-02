package oct.rekord.cas.service.impl;

import lombok.extern.slf4j.Slf4j;
import oct.rekord.cas.bean.CA;
import oct.rekord.cas.common.ReturnData;
import oct.rekord.cas.dao.CADAO;
import oct.rekord.cas.service.CAService;
import oct.rekord.cas.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service("caService")
public class CAServiceImpl implements CAService {
    @Autowired
    CADAO caDAO;

    @Autowired
    SemesterService semesterService;

    @Override
    public ReturnData getCAResult(String userId, String semesterName) {
        Integer userIdInteger = Integer.valueOf(userId);

        // 通过 semesterName 查询 semesterId
        Integer semesterId = semesterService.getSemesterIdBySemesterName(semesterName);
        semesterId = semesterId == null ? -1 : semesterId;

        List<CA> caList = new LinkedList<>();
        caList.addAll(caDAO.selectAwardCertificateByUserIdAndSemesterId(userIdInteger, semesterId));
        caList.addAll(caDAO.selectParticipatedActivityByUserIdAndSemesterId(userIdInteger, semesterId));
        return ReturnData.success(caList);
    }
}
