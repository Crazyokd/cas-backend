package oct.rekord.cas.service;

import oct.rekord.cas.common.ReturnData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public interface HeadImgService {
    /**
     * 图片后缀
     */
    List<String> IMG_SUFFIX = Arrays.asList(".jpg","jpeg",".png",".gif");

    /**
     * 单个图片的最大大小
     */
    long MAX_IMG_SIZE = 1024 * 1024 * 8;

    ReturnData setHeadImg(HttpServletRequest request, String userid, MultipartFile file);

    /**
     * 获得头像
     * @param request
     */
    ReturnData getHeadImg(HttpServletRequest request, String userid) throws Exception;

}
