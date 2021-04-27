package com.planeter.w2auction.controller;

import com.planeter.w2auction.common.result.ExceptionMsg;
import com.planeter.w2auction.common.result.ResponseData;
import com.planeter.w2auction.dto.UserInfo;
import com.planeter.w2auction.service.MemberService;
import com.planeter.w2auction.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;

@RestController
/**
 * 会员控制器
 */
public class MemberController {
    @Resource
    MemberService memberService;
    @Resource
    UserService userInfoService;

    /**
     * 会员注册
     *
     * @param info
     * @return
     */
    @PostMapping("/register")
    public ResponseData register(@RequestBody UserInfo info) {
        //取得注册用户的用户名和密码
        String username = info.getUsername();
        String password = info.getPassword();
        //避免html
        username = HtmlUtils.htmlEscape(username);
        //检查用户名是否已经存在
        if (userInfoService.isValid(username)) {
            return new ResponseData(ExceptionMsg.UserNameUsed);
        }
        memberService.registerMember(username,password);//存入数据库
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
    /**
     * 登陆, DbRealm处理
     * @param info
     * @return
     */
    @PostMapping("/login")
    public ResponseData login(@RequestBody UserInfo info) {
        String username = info.getUsername();
        String password = info.getPassword();
        Subject subject = SecurityUtils.getSubject();
        String jwtToken = null;
        try {
            //UsernamePasswordToken交给DbShiroRealm进行凭证匹配
            subject.login(new UsernamePasswordToken(username, password));
            //生成JwtToken
            jwtToken = userInfoService.generateJwtToken(username);
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            return new ResponseData(ExceptionMsg.UserNameWrong);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return new ResponseData(ExceptionMsg.PasswordWrong);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseData(ExceptionMsg.SUCCESS,jwtToken);
    }
    /**
     * 会员更新信息
     * @param frontMember
     * @return
     */
    @PutMapping("/member/update")
    public ResponseData updateMember(@RequestBody FrontMember frontMember){
        Member member = memberService.getMember(frontMember.getId());
        member.setAddress(frontMember.getAddress());
        member.setPhone(frontMember.getPhone());
        member.setBirthday(frontMember.getBirthday());
        member.setGender(frontMember.getGender());
        memberService.updateMember(member);//更新信息,若有address和phone授予角色member
        return new ResponseData(ExceptionMsg.SUCCESS);
    }

    /**
     * 上传图片后,会员设置头像
     * @param frontMember 需要id和imageId
     * @return
     */
    @PutMapping("/member/uploadIcon")
    public ResponseData uploadIcon(@RequestBody FrontMember frontMember){
        Member member = memberService.getMember(frontMember.getId());
        member.setImageId(frontMember.getImageId());
        memberService.saveMember(member);
        return new ResponseData(ExceptionMsg.SUCCESS);
    }
}
