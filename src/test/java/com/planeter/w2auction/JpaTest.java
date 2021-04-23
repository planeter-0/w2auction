package com.planeter.w2auction;

import com.planeter.w2auction.dao.SysRoleDao;
import com.planeter.w2auction.dao.UserInfoDao;
import com.planeter.w2auction.entity.SysPermission;
import com.planeter.w2auction.entity.SysRole;
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
    SysRoleDao sysRoleDao;
    @Test
    public void roleAdd(){
        SysRole role = new SysRole();
        List<SysPermission> permissions = new ArrayList<>();

        permissions.add(new SysPermission("item:view"));
        role.setName("user");
        role.setPermissions(permissions);
        sysRoleDao.save(role);
    }
}
