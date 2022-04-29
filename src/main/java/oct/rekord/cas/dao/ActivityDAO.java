package oct.rekord.cas.dao;

import oct.rekord.cas.bean.Activity;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.Date;
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

    @Insert({"insert into " + TABLE_NAME +
            "        (act_name, act_description, act_img_path, act_reg_max_count," +
            "        act_reg_start_date, act_reg_end_date, act_time, act_place, act_category, semester_id)" +
            "        values(#{actName}, #{actDescription}, #{actImgPath}, #{actRegMaxCount}, " +
            "        #{actRegStartDate}, #{actRegEndDate}, #{actTime}, #{actPlace}, #{actCategory}, #{semesterId})"})
    @SelectKey(statement = "select last_insert_id()", resultType = Integer.class, before = false, keyProperty = "actId")
    int publishActivity(Activity activity);
}
