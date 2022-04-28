package oct.rekord.cas.dao;

import oct.rekord.cas.bean.Version;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VersionDAO {

    @Select("select * from version order by version_id desc limit 1")
    Version getLatestVersion();

}

