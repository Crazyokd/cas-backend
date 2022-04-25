package oct.rekord.cas.dao;

import oct.rekord.cas.bean.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityDAO {
    String TABLE_NAME = "activity";

    List<Activity> selectRunningActivity();

    List<Activity> selectClosedActivity();

    List<Activity> selectTodayActivityByUserId(Integer userId);

    List<Activity> selectParticipateActivityByUserId(Integer userId);

}
