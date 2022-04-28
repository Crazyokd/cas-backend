package oct.rekord.cas.dao;

import oct.rekord.cas.bean.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityDAO {
    String TABLE_NAME = "activity";

    @Select({"select count(act_id) from ", TABLE_NAME})
    int selectActivityCount();

    List<Activity> selectRunningActivityByPageNum(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    List<Activity> selectClosedActivity(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);

    List<Activity> selectTodayActivityByUserId(Integer userId);

    List<Activity> selectParticipateActivityByUserId(Integer userId);

    @Select("select * from activity where act_id = #{actId}")
    Activity selectActivityByActId(Integer sctId);
}
