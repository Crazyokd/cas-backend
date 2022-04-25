package oct.rekord.cas.dao;

import oct.rekord.cas.bean.CA;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CADAO {

    List<CA> selectParticipatedActivityByUserId(Integer userId);

    List<CA> selectAwardCertificateByUserId(Integer userId);
}
