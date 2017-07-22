package tech.shann.entity.mapper;

import tech.shann.entity.User;
import tech.shann.entity.enums.Sex;
import tech.shann.entity.model.UserModel;
import tech.shann.util.BaseMapper;
import tech.shann.util.IdColumn;
import tech.shann.util.TableName;
import org.apache.ibatis.annotations.*;

import java.util.Map;

/**
 * Created by shann on 17/7/3.
 */
@TableName("sys_user")
@IdColumn("id")
public interface UserMapper extends BaseMapper<User,Long> {

    @Delete("delete from sys_user where mobile=#{mobile}")
    int deleteUser1(User u);

    @Delete("delete from sys_user where mobile=#{mobile}")
    int deleteUser0(Map<String,Object> m);

    @Update("update sys_user set" +
            "    user_name = concat(user_name,'_',#{suffix})" +
            "    where sex = ${sex.getValue()}")
    int batchUpdateUser(Map<String,Object> m);

    @Update("update sys_user set" +
            "    user_name = #{userName}," +
            "    sex = ${sex.getValue()}" +
            "    where id = #{id}")
    int updateUser(User u);

    @Insert("insert into sys_user (user_name,mobile,create_time,sex)" +
            " values (#{userName},#{mobile},#{createTime},${sex.getValue()})")
    int insertUser(User u);

    @Select("SELECT u.*,uc.company_id " +
            "FROM sys_user u " +
            "  left join user_company uc on (u.id = uc.user_id) " +
            "WHERE u.id = #{id}")
    UserModel selectUserModel1(Long id);

    @Select("SELECT * FROM sys_user WHERE id = #{id} and sex = ${sex.getValue()}")
    User selectUser6(User user);

    @Select("SELECT * FROM sys_user WHERE id = #{id} and sex = ${sex.getValue()}")
    User selectUser5(@Param("id") Long id, @Param("sex") Sex sex);

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    User selectUser4(Long id);

    User selectUser3(@Param("id") Long id);

//    @SelectProvider(type = SqlDynamicBuilder.class, method = "buildDynamic")
//    public List<User> dynamicSelect(String str, User param);
//    public List<User> dynamicSelect(@Param("str") String str, @Param("param") Object param);

//    @Select("SELECT * FROM sys_user")
//    Page<User> selectPage1(n);
}
