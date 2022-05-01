package oct.rekord.cas.common;

import java.util.Arrays;
import java.util.List;

public class Image {
    /**
     * 图片后缀
     */
    public static final List<String> IMG_SUFFIX = Arrays.asList(".jpg","jpeg",".png",".gif");

    /**
     * 单个图片的最大大小
     */
    public static final long MAX_IMG_SIZE = 1024 * 1024 * 8;
}
