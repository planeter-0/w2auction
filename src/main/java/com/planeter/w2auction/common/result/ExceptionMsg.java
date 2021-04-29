
package com.planeter.w2auction.common.result;
/**
 * @description: 异常信息的枚举类
 * @author Planeter
 * @date 2021/4/29 20:48
 * @status dev
 */
public enum ExceptionMsg {
	SUCCESS("000200", "操作成功"),
	FAILED("000999","操作失败"),
    UserNameUsed("100100","该用户名已被使用"),
    UserNameWrong("100100","用户名错误"),
    PasswordWrong("100200","密码错误"),
    NoSuchRole("100300","没有相应角色"),
    NoSuchPermission("100400","没有相应权限"),
    UnPassed("000300","审核未通过"),
    UploadFailed("000400","上传图片失败"),
    ;
   ExceptionMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    private String code;
    private String msg;
    
	public String getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}

    
}

