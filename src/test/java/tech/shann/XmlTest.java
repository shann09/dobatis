package tech.shann;

import tech.shann.entity.User;
import tech.shann.entity.enums.Sex;
import tech.shann.entity.model.UserModel;
import tech.shann.util.SqlSourceBuilder;
import com.github.pagehelper.Page;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by shann on 17/7/3.
 */
public class XmlTest {
    
    @Test
    public void testDelete1(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            User u = new User();
            u.setMobile("22");

            int i = session.delete("tech.shann.entity.mapper.UserMapper.deleteUser", u);
            System.out.println(i);//

        }
    }

    @Test
    public void testDelete0(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<>();
            m.put("mobile",11);

            int i = session.delete("tech.shann.entity.mapper.UserMapper.deleteUser", m);
            System.out.println(i);//
        }
    }

    @Test
    public void testUpdateBean(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            User u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUser0",1);
            u.setSex(Sex.FEMALE);
            u.setUserName("小乔");
            u.setCreateTime(new Date());
            u.setMobile("111");

//            int i = session.update("tech.shann.entity.mapper.UserMapper.updateUser3", u);
            int i = session.update("tech.shann.entity.mapper.UserMapper.updateUser4", u);//和上面那个sql等价
            System.out.println(i);//
        }
    }

    @Test
    public void testUpdateBatch(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<>();
            m.put("sex",Sex.FEMALE);
            m.put("suffix","女");

            int i = session.update("tech.shann.entity.mapper.UserMapper.updateUser2", m);
            System.out.println(i);//
        }
    }

    @Test
    public void testUpdateById(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            User u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUser0",1);
            u.setSex(Sex.FEMALE);
            u.setUserName("小乔");

            int i = session.update("tech.shann.entity.mapper.UserMapper.updateUser1", u);
            System.out.println(i);//
        }
    }

    @Test
    public void testInsertBatch(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            List<User> list = new ArrayList<>();

            User u = new User();
            u.setUserName("曹操");
            u.setMobile("44");
            u.setSex(Sex.MALE);
            u.setCreateTime(new Date());

            list.add(u);

            u = new User();
            u.setUserName("甄姬");
            u.setMobile("55");
            u.setSex(Sex.FEMALE);
            u.setCreateTime(new Date());

            list.add(u);

            int i = session.insert("tech.shann.entity.mapper.UserMapper.insertUser1", list);
            System.out.println(i);//2
        }
    }


    @Test
    public void testInsert(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            User u = new User();
//            u.setId(1l);
            u.setUserName("曹操");
            u.setMobile("44");
            u.setSex(Sex.MALE);
            u.setCreateTime(new Date());
            int i = session.insert("tech.shann.entity.mapper.UserMapper.insertUser0", u);
            System.out.println(i);//1
        }
    }

    @Test
    public void testSelectPage(){
        //假分页：
        //  0)，使用mybatis原生RowBounds参数，并注释掉mybatis-config.xml里的pagehelper插件
        //真分页：
        //  分页插件版本：com.github.pagehelper/pagehelper/5.0.3
        //  1)，使用mybatis原生RowBounds参数，配合分页插件pagehelper，
        //      结果List强转为Page
        //使用该插件必须注意这几点：https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Important.md
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            // 0)
//            List<User> users = session.selectList("tech.shann.entity.mapper.UserMapper.selectPage",
//                    null,new RowBounds(3, 2));
            // 1)
            Page<User> users = (Page)session.selectList("tech.shann.entity.mapper.UserMapper.selectPage",
                    null,new RowBounds(3, 2));
            System.out.println("p.getTotal(): "+users.getTotal());

            System.out.println("users.size(): "+users.size());//
            for(User u : users.getResult()){
                System.out.println(u.getId());
                System.out.println(u.getMobile());
                System.out.println(u.getSex());
                System.out.println(u.getUserName());
            }
        }
    }

    @Test
    public void testSelectLike(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<String,Object>(){{
                put("namePart","香");
            }};
            List<User> users = session.selectList("tech.shann.entity.mapper.UserMapper.selectBind",m);
            System.out.println(users.size());//
            for(User u : users){
                System.out.println(u.getMobile());
                System.out.println(u.getId());
                System.out.println(u.getUserName());
                System.out.println(u.getSex());
            }

        }
    }

    @Test
    public void testSelectIn(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            List<Integer> l = new ArrayList<Integer>(){{
                add(1);
                add(2);
            }};

            List<User> users = session.selectList("tech.shann.entity.mapper.UserMapper.selectIn",l);
            System.out.println(users.size());//
            for(User u : users){
                System.out.println(u.getId());
                System.out.println(u.getMobile());
                System.out.println(u.getUserName());
                System.out.println(u.getSex());
            }

        }
    }

    @Test
    public void testSelectIf(){
        try(SqlSession session = sqlSessionFactory.openSession(true)) {
            Map<String,Object> m = new HashMap<>();
            m.put("sex",Sex.FEMALE);
            m.put("mobile","222333444");

            List<User> users = session.selectList("tech.shann.entity.mapper.UserMapper.selectIf",m);
            System.out.println(users.size());//
            for(User u : users){
                System.out.println(u.getId());
                System.out.println(u.getUserName());
                System.out.println(u.getSex());
                System.out.println(u.getMobile());
            }

        }
    }

    @Test
    public void testSelectModel(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            UserModel u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUserModel0",1);
            System.out.println(u.getUserName());
            System.out.println(u.getCompanyId());

        }
    }

    @Test
    public void testSelectByBean(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            User up = new User();
            up.setId(2l);
            up.setSex(Sex.FEMALE);

            User u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUser2",up);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().getDesc());

        }
    }
    @Test
    public void testSelectByMap(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            User u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUser1",new HashMap<String,Object>(){{
                put("id",2);
                put("sex", Sex.FEMALE);
            }});
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().getDesc());

        }
    }

    @Test
    public void testSelectById(){
        try(SqlSession session = sqlSessionFactory.openSession()) {

            User u = session.selectOne("tech.shann.entity.mapper.UserMapper.selectUser0",1);
            System.out.println(u.getUserName());
            System.out.println(u.getSex());
            System.out.println(u.getSex().getValue());
            System.out.println(u.getSex().getDesc());

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
