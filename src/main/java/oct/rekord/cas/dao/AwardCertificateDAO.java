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

    @Options(useGeneratedKeys = true, keyColumn = "acId", keyProperty = "acId")
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{userId}, #{name}, #{isValid}, #{category}, #{explanation}, #{comment}, #{imgPath}, #{semesterId})"})
    int updateAwardCertificate(AwardCertificate awardCertificate);

    @Select("select * from award_certificate where acId = #{acId}")
    AwardCertificate selectAwardCertificateByACId(String acId);
}
