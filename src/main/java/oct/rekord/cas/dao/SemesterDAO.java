package oct.rekord.cas.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SemesterDAO {
    String TABLE_NAME = "semester";

    @Select({"select semester_id from ", TABLE_NAME, " where semester_name = #{semesterName}"})
    Integer selectSemesterIdBySemesterName(@Param("semesterName") String semesterName);

    @Select({"select semester_name from ", TABLE_NAME, " where semester_id = #{semesterId}"})
    String selectSemesterNameBySemesterId(@Param("semesterId") Integer semesterId);
}
