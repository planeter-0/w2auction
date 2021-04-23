package com.planeter.w2auction.service;

import com.planeter.w2auction.entity.Member;
import com.planeter.w2auction.entity.UserInfo;

import java.util.List;

public interface MemberService {
    /** 注册会员 */
    public Member registerMember(String username,String password);
    public Member getMember(Integer id);
    /** 更新信息,若有address和phone授予角色member */
    public Member updateMember(Member member);

}
