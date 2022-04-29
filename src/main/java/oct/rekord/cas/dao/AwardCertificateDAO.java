package oct.rekord.cas.dao;

import oct.rekord.cas.bean.AwardCertificate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AwardCertificateDAO {
    String TABLE_NAME = "award_certificate";
    String INSERT_FIELDS = "user_id, name, is_valid, category, explanation, comment, img_path, semester_id";

    @Select({"select * from", TABLE_NAME, "where user_id = #{user_id}"})
    List<AwardCertificate> selectAwardCertificateByUserID(@Param("user_id") Integer userId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId}, #{name}, #{isValid}, #{category}, #{explanation}, #{comment}, #{imgPath}, #{semesterId})"})
    @SelectKey(statement = "select last_insert_id()", resultType = Integer.class, before = false, keyProperty = "acId")
    Integer uploadAwardCertificate(AwardCertificate awardCertificate);

    @Select("select * from award_certificate where acId = #{acId}")
    AwardCertificate selectAwardCertificateByACId(Integer acId);

    @Update("update award_certificate set is_valid = '1' where ac_id = #{acId}")
    int updateValidation(Integer acId);
}
