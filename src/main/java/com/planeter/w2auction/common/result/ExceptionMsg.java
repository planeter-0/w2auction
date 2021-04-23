
package com.planeter.w2auction.common.result;
//异常信息的枚举类
public enum ExceptionMsg {
	SUCCESS("000200", "操作成功"),
	FAILED("000999","操作失败"),
    // 权限相关
    UserNameUsed("100100","该用户名已被使用"),
    UserNameWrong("100100","用户名错误"),
    PasswordWrong("100200","密码错误"),
    NoSuchRole("100300","没有相应角色"),
    NoSuchPermission("100400","没有相应权限"),
    LimitSize("000310","超出网盘容量限制"),
    UNPASSED("000300","未审核或审核未通过"),
    FileEmpty("000400","上传文件为空"),
    ;
   private ExceptionMsg(String code, String msg) {
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

