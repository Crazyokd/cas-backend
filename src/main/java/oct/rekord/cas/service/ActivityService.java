package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;

import javax.servlet.http.HttpServletRequest;

public interface ActivityService {
    ReturnData getRunningActivity(HttpServletRequest request);

    ReturnData getClosedActivity(HttpServletRequest request);

    ReturnData getParticipateActivity(HttpServletRequest request, String userId);

    ReturnData getTodayActivity(HttpServletRequest request, String userId);
}
