package com.planeter.w2auction;

import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.dao.UserInfoDao;
import com.planeter.w2auction.entity.Permission;
import com.planeter.w2auction.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class JpaTest {
    @Resource
    UserInfoDao userInfoDao;
    @Autowired
    RoleDao sysRoleDao;
    @Test
    public void roleAdd(){
        Role role = new Role();
        List<Permission> permissions = new ArrayList<>();

        permissions.add(new Permission("item:view"));
        role.setName("user");
        role.setPermissions(permissions);
        sysRoleDao.save(role);
    }
}
