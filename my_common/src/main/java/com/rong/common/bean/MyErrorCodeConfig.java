package com.rong.common.bean;
/**
 * 错误码
 * @author rongwq
 *
 */
public class MyErrorCodeConfig {
	// 错误代码
	public static final String ERROR_TOKEN_MISS = "301"; // token不正确
	public static final String ERROR_TOKEN_EXPIRE = "302"; // token过期
	public static final String ERROR_BAD_REQUEST = "400"; // 参数 不正确
	public static final String ERROR_FAIL = "500"; // 请求异常
	public static final String REQUEST_SUCCESS = "1"; // 请求成功
	public static final String REQUEST_FAIL = "0"; // 请求失败
	
	public static final String USER_NOT_EXIST = "USER_NOT_EXIST"; // 用户不存在
	public static final String USER_NO_MOBILE = "USER_NO_MOBILE"; // 用户没绑定手机
	public static final String ACCOUNT_SMS_NOT_RIGHT = "SMS_NOT_RIGHT"; // 验证码不正确
	public static final String ACCOUNT_SMS_CODE_EXPIRED = "SMS_CODE_EXPIRED"; // 验证码过期

    //支付相关
    public static final String ERROR_NOTSETPAYPASSWORD = "ERROR_NOTSETPAYPASSWORD"; //未设置支付密码
    public static final String ERROR_PAYPASSWORD = "ERROR_PAYPASSWORD"; // 支付密码错误
    public static final String ERROR_PAYPASSWORD_LOCKED = "ERROR_PAYPASSWORD_LOCKED"; // 支付密码锁定
    public static final String ERROR_PAYPASSWORD_LOCK_SOON = "ERROR_PAYPASSWORD_LOCK_SOON"; // 支付密码即将被锁定
    public static final String ERROR_NOTENOUGH = "ERROR_NOTENOUGH"; // 余额不够


}
