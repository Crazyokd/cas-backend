package oct.rekord.cas.dao;

import oct.rekord.cas.bean.Application;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ApplicationDAO {
    @Insert("insert into application(application_from_id, application_to_id, category, link_id, comment) " +
            "values(#{applicationFromId}, #{applicationToId}, #{category}, #{linkId}, #{comment})")
    int insertApplication(Application application);

    @Select("select user_id from manager_1")
    List<Integer> selectManager1();

    @Select("select user_id from manager_2")
    List<Integer> selectManager2();

    @Select("select * from application where application_to_id = #{userId}")
    List<Application> selectApplicationByToId(Integer userId);

    @Update("update application set status = #{status}, reply_time  = #{replyTime} " +
            "where application_id = #{applicationId}")
    int updateApplication(Application application);
}
