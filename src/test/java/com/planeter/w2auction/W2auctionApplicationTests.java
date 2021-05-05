package com.planeter.w2auction;

import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.dao.UserDao;
import com.planeter.w2auction.entity.Role;
import com.planeter.w2auction.entity.User;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ServletComponentScan//启用 XssFilter
class W2auctionApplicationTests {
    @Resource
    RoleDao roleDao;
    @Resource
    UserDao userDao;
    @Test
    void addAdmin() {
        User user;
        try {
            //Bcrypt加密
            String encodedPassword = BCrypt.hashpw("123456", BCrypt.gensalt());
            //创建user
            List<Role> roles = new ArrayList<>();
            roles.add(roleDao.findByName("admin"));//
            user = new User("admin", encodedPassword, 1, roles);
            //写入数据库
            userDao.save(user);//插入
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
