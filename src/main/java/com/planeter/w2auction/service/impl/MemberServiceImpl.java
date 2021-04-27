package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.RoleDao;
import com.planeter.w2auction.entity.Role;
import com.planeter.w2auction.entity.User;
import com.planeter.w2auction.service.MemberService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberDao memberDao;
    @Resource
    RoleDao roleDao;
    @Override
    public Member registerMember(String username,String password) {
        Member member = new Member();
        try {
            //md5加盐迭代两次，生成加密密码
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
            //创建userinfo
            List<Role> roles = new ArrayList<>();
            roles.add(roleDao.findByName("user"));//角色user
            User userInfo = new User(username, encodedPassword, salt, 1, roles);
            //将member与userinfo连表写入数据库
            member.setUserInfo(userInfo);
            memberDao.save(member);//插入
        } catch (Exception e){
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public Member getMember(Integer id) {
        return memberDao.getOne(id.longValue());
    }

    @Override
    public void updateMember(Member member) {
        if (member.getAddress() != null&&member.getPhone()!=null){
            List<Role> roles= member.getUserInfo().getRoles();
            roles.add(roleDao.findByName("member"));
            member.getUserInfo().setRoles(roles);
        }
    }

    @Override
    public void saveMember(Member member) {
        memberDao.save(member);
    }
}
