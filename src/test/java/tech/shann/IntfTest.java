package tech.shann;

import tech.shann.entity.User;
import tech.shann.entity.enums.Sex;
import tech.shann.entity.mapper.UserMapper;
import tech.shann.entity.model.UserModel;
import tech.shann.util.SqlSourceBuilder;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shann on 17/7/3.
 */
public class IntfTest {

    @Test
    public void testDelete1(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            User u = new User();
            u.setMobile("22");

            UserMapper mapper = session.getMapper(UserMapper.class);
            int i = mapper.deleteUser1(u);
            System.out.println(i);//

        }
    }

    @Test
    public void testDelete0(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<>();
            m.put("mobile",33);//数据库

            UserMapper mapper = session.getMapper(UserMapper.class);
            int i = mapper.deleteUser0(m);
            System.out.println(i);//
        }
    }

    @Test
    public void testUpdateBatch(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<>();
            m.put("sex", Sex.FEMALE);
            m.put("suffix","女");

            UserMapper mapper = session.getMapper(UserMapper.class);
            int i = mapper.batchUpdateUser(m);
            System.out.println(i);//
        }
    }

    @Test
    public void testUpdateById(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User u = mapper.selectUser4(1l);
            u.setSex(Sex.MALE);
            u.setUserName("周瑜");

            int i = mapper.updateUser(u);
            System.out.println(i);//
        }
    }


    //batchInsert使用xml方式的foreach

    @Test
    public void testInsert(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            User u = new User();
            u.setUserName("曹操");
            u.setMobile("44");
            u.setSex(Sex.MALE);
            u.setCreateTime(new Date());

            UserMapper mapper = session.getMapper(UserMapper.class);
            int i = mapper.insertUser(u);
            System.out.println(i);

        }
    }

    @Test
    public void testSelecctPage(){
        try(SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.selectPage1();
        }
    }

    @Test
    public void testSelectModel(){
        try(SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserModel u = mapper.selectUserModel1(1l);
            System.out.println(u.getUserName());
            System.out.println(u.getCompanyId());

        }
    }

    @Test
    public void testSelectByBean(){

        try(SqlSession session = sqlSessionFactory.openSession()) {

            UserMapper mapper = session.getMapper(UserMapper.class);

            User up = new User();
            up.setId(2l);
            up.setSex(Sex.FEMALE);
            User u = mapper.selectUser6(up);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().name());
        }
    }
    @Test
    public void testSelectByParam(){


        try(SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User u = mapper.selectUser5(2l, Sex.FEMALE);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().name());
        }
    }
    @Test
    public void testSelectById(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            User u = mapper.selectUser4(2l);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().name());
        }
    }
    @Test
    public void testSelectByIdXml(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            UserMapper mapper = session.getMapper(UserMapper.class);
            User u = mapper.selectUser3(2l);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().name());
        }
    }

//    @Test
//    public void testBaseDynamicSelect(){
//        try(SqlSession session = sqlSessionFactory.openSession(true)) {
//            User u = new User();
//            u.setUserName("曹操");
//
//            UserMapper mapper = session.getMapper(UserMapper.class);
//            List<User> users = mapper.dynamicSelect("select * from sys_user where user_name = #{userName}",u);
//            System.out.println(users.size());//
//        }
//    }

    @Test
    public void testBaseExistById(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Boolean b = mapper.existById(1l);
            System.out.println(b);//
        }
    }

    @Test
    public void testBaseDeleteById(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            Long i = mapper.deleteById(40l);
            System.out.println(i);//
        }
    }

    @Test
    public void testBaseUpdateById(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            UserMapper mapper = session.getMapper(UserMapper.class);

            User u = mapper.getById(40l);

            u.setUserName("曹孟德");

            mapper.updateById(u);

        }
    }
    @Test
    public void testBaseInsert(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {

            User u = new User();
            u.setUserName("曹操");
            u.setMobile("44");
            u.setSex(Sex.MALE);
            u.setCreateTime(new Date());

            UserMapper mapper = session.getMapper(UserMapper.class);
            Long i = mapper.insert(u);
            System.out.println(i);

        }
    }

    @Test
    public void testBaseGetById(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            UserMapper mapper = session.getMapper(UserMapper.class);

            User u = mapper.getById(1l);

            System.out.println(u.getId());
            System.out.println(u.getUserName());
            System.out.println(u.getMobile());
            System.out.println(u.getSex());


        }
    }

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Configuration conf = new XMLConfigBuilder(inputStream, "develop", null).parse();//指定mybatis-config.xml中使用哪个environment
        SqlSourceBuilder.build(conf);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(conf);
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

}
