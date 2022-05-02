package oct.rekord.cas.service;


public interface SemesterService {

    Integer getSemesterIdBySemesterName(String semesterName);

    String getSemesterNameBySemesterId(Integer semesterId);
}
