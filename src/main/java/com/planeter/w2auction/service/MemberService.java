package com.planeter.w2auction.service;

public interface MemberService {
    /** 注册会员 */
    public Member registerMember(String username,String password);
    public Member getMember(Integer id);
    /** 更新信息,若有address和phone授予角色member */
    public void updateMember(Member member);
    /** 保存 */
    public void saveMember(Member member);
}
