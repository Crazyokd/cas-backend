package oct.rekord.cas.service.impl;

import oct.rekord.cas.dao.SemesterDAO;
import oct.rekord.cas.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("semesterService")
public class SemesterServiceImpl implements SemesterService {

    @Autowired
    SemesterDAO semesterDAO;
    @Override
    public Integer getSemesterIdBySemesterName(String semesterName) {
        return semesterDAO.selectSemesterIdBySemesterName(semesterName);
    }

    @Override
    public String getSemesterNameBySemesterId(Integer semesterId) {
        return semesterDAO.selectSemesterNameBySemesterId(semesterId);
    }
}
