package oct.rekord.cas.util;

import oct.rekord.cas.exception.BaseException;
import oct.rekord.cas.common.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


@Slf4j
public class FileUtil {

    public static String fileTransfer(MultipartFile file, String path, List<String> suffixList, long minSize, long maxSize) {
        if (file == null || file.isEmpty()) {
            return "default.jpg";
        }
        //获取原文件名
        String originalFilename = file.getOriginalFilename();
        //获取后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!suffixList.contains(suffixName) || file.getSize() < minSize || file.getSize() > maxSize) {
            throw new BaseException(CodeEnum.REQUEST_FAILED.getCode(), "文件参数错误");
        }
        //新的文件名
        String fileName = RandomNumberUtil.getUUID() + suffixName;
        //文件的保存路径
        String filePath = path + fileName;
        try {
            file.transferTo(new File(filePath));
            return fileName;
        } catch (IOException e) {
            log.error("----------->图片转存失败:" + e.getMessage());
            throw new BaseException(CodeEnum.SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    public static boolean delFile(String path){
        File file = new File(path);
        if (file.exists()){
            file.delete();
            return true;
        }
        return false;
    }

    public static byte[] fileToBytes(File file) throws Exception {
        try (FileInputStream fis = new FileInputStream(file)){
            byte[] bytesArray = new byte[(int) file.length()];
            fis.read(bytesArray);
            return bytesArray;
        } catch (Exception e){
            throw new Exception("文件转换异常");
        }
    }

    public static byte[] fileNameToBytes(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) {
            return fileToBytes(file);
        } else {
            throw new Exception("文件不存在");
        }
    }

}
