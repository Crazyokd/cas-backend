package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;

import javax.servlet.http.HttpServletRequest;

public interface ActivityService {
    ReturnData getRunningActivity(HttpServletRequest request, Integer pageNum, Integer pageSize);

    ReturnData getClosedActivity(HttpServletRequest request, Integer pageNum, Integer pageSize);

    ReturnData getParticipateActivity(HttpServletRequest request, String userId);

    ReturnData getTodayActivity(HttpServletRequest request, String userId);

    ReturnData getActivity(Integer actId);
}
