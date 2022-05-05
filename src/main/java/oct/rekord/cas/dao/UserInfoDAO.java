package oct.rekord.cas.dao;

import oct.rekord.cas.bean.AuthorityRecord;
import oct.rekord.cas.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "username, password, phone";
//    String SELECT_FIELDS ="user_id, username, sn, sn_password,class_name,app_version";

    /** 插入一条用户信息
     *
     * @Param user
     */
    @Options(useGeneratedKeys = true, keyColumn = "userId", keyProperty = "userId")
    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ") values(#{username}, #{password}, #{phone})"})
    int insertUser(User user);


    /** 通过账号密码得到用户所有信息
     *
     * @param username
     * @param password
     * @return
     */
    @Select({"select","* from ",TABLE_NAME,"where username = #{username} and password = #{password}"})
    User selectUserInfoByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /** 通过 user_id 得到用户头像文件路径
     *
     * @param userId
     * @return
     */
    @Select({"select head_img_path from ", TABLE_NAME, "where user_id = #{userId}"})
    String selectHeadImgPathByUserId(@Param("userId") Integer userId);

    /** 更新用户头像
     *
     * @param userId
     * @param headImgPath
     * @return
     */
    @Update({"update ", TABLE_NAME, "set head_img_path = #{headImgPath} ", "where user_id = #{userId}"})
    Integer updateHeadImgPath(@Param("userId") Integer userId, @Param("headImgPath") String headImgPath);

    @Select("select level from user where user_id = #{userId}")
    Integer selectLevelByUserId(Integer userId);

    @Update("update user set level = #{newLevel} where user_id = #{userId}")
    int updateUserLevel(Integer userId, Integer newLevel);

    @Insert("insert into manager2(user_id, parent) values(#{userId}, #{managerId})")
    int insertManager2(Integer userId, Integer managerId);

    @Delete({"delete from manager2 where user_id = #{userId}"})
    int removeByUserId(Integer userId);

    @Select("select manager_id from manager1 where user_id = #{parentId}")
    int selectManagerIdByUserId(Integer parentId);

    @Insert("insert into authority_record(from_user_id, to_user_id, action) " +
            "values(#{fromUserId}, #{toUserId}, #{action})")
    int insertAuthorityRecord(AuthorityRecord authorityRecord);
}
