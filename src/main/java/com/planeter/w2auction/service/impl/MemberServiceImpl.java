package com.planeter.w2auction.service.impl;

import com.planeter.w2auction.dao.MemberDao;
import com.planeter.w2auction.dao.SysRoleDao;
import com.planeter.w2auction.entity.Member;
import com.planeter.w2auction.entity.SysRole;
import com.planeter.w2auction.entity.UserInfo;
import com.planeter.w2auction.service.MemberService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    MemberDao memberDao;
    @Resource
    SysRoleDao roleDao;
    @Override
    public Member registerMember(String username,String password) {
        Member member = new Member();
        try {
            //md5加盐迭代两次，生成加密密码
            String salt = new SecureRandomNumberGenerator().nextBytes().toString();
            String encodedPassword = new SimpleHash("md5", password, salt, 2).toString();
            //创建userinfo
            List<SysRole> roles = new ArrayList<>();
            roles.add(roleDao.findByName("user"));//角色user
            UserInfo userInfo = new UserInfo(username, encodedPassword, salt, 1, roles);
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
        return null;
    }

    @Override
    public Member updateMember(Member member) {
        return null;
    }

}
