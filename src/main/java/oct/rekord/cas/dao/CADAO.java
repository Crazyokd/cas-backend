package oct.rekord.cas.dao;

import oct.rekord.cas.bean.CA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CADAO {

    List<CA> selectParticipatedActivityByUserIdAndSemesterId(@Param("userId") Integer userId, @Param("semesterId") Integer semesterId);

    List<CA> selectAwardCertificateByUserIdAndSemesterId(@Param("userId") Integer userId, @Param("semesterId") Integer semesterId);
}
