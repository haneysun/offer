package com.example.offer.core;

/**
 * @Author lizhigang Date: 2019/3/6 15:37
 * @description:业务异常枚举信息
 *
 */
public enum BusinessErrorEnum implements CommonError{
	//10001通用错误类型
	FAIL(500,"操作失败"),
	SUCCESS(200,"操作成功"),
	EMPTY(1001,"非法空值"),
	PARAMETER_VALIDATION_ERROR(FAIL.getErrCode(),"参数不合法"),
	UNKNOWN_ERROR(FAIL.getErrCode(),"未知错误,请联系管理员"),


	//20000开头为用户信息相关的错误码
	USER_NOT_EXIST(FAIL.getErrCode(), "用户不存在"),
	USER_ID_IS_NULL(FAIL.getErrCode(), "用户id不能为空"),
	ID_IS_NULL(FAIL.getErrCode(), "id不能为空"),
    USER_NOT_REGISTER(FAIL.getErrCode(), "用户未注册"),
	PHONE_IS_REGISTER(FAIL.getErrCode(), "手机号已注册"),
	USER_OLDPASSWORD_ERROR(FAIL.getErrCode(), "输入的原密码错误"),
	USER_PASSWORD_NOT_SAME(FAIL.getErrCode(), "新密码不能和原密码相同"),
	USER_PASSWORD_IS_NULL(FAIL.getErrCode(), "密码不能为空"),
	LOGIN_NOT_MATCH(FAIL.getErrCode(), "用户名或密码错误"),
	USER_DISABLED(FAIL.getErrCode(), "用户禁用"),
	ALREADY_LOGIN(FAIL.getErrCode(), "用户已登录"),
	FILE_TOO_LARGE(FAIL.getErrCode(), "您上传的图片超过限制，请控制图片大小在50K以内"),
	USER_VERIFICATION_ERROR(FAIL.getErrCode(),"验证码已过期或无效！"),
	PHONE_IS_NULL(FAIL.getErrCode(), "手机号不可为空"),
	PHONE_REGEX_ERROR(FAIL.getErrCode(), "手机号格式错误"),
	CAPTCHA_IS_NULL(FAIL.getErrCode(), "图形验证码不可为空"),
	CAPTCHA_IS_ERROR(FAIL.getErrCode(), "图形验证码不正确"),
	CAPTCHA_IS_REPEAT(FAIL.getErrCode(), "请勿重复获取验证码，一分钟后再次获取"),
	SEND_SMS_NUM_TRANSFINITE(FAIL.getErrCode(), "短信验证码获取次数过多"),
	USER_INVITER_IS_NULL(FAIL.getErrCode(), "邀请码不能为空"),
	USER_INVITER_ID_IS_NULL(FAIL.getErrCode(), "邀请码ID不能为空"),
	USER_INVITER_IS_ERROR(FAIL.getErrCode(), "邀请码错误"),
	NOT_INVITE_YOURSELF(FAIL.getErrCode(), "不能邀请自己哦,分享给好友吧"),
	USER_IS_EXIST_ZJN(FAIL.getErrCode(), "您已是住建鸟用户,不能领取新人红包"),
	CODE_IS_ERROR(FAIL.getErrCode(), "验证码不正确"),
	CODE_IS_NULL(FAIL.getErrCode(), "验证码不能为空"),
	USER_IS_BOUND(FAIL.getErrCode(), "用户已经绑定"),
	INVITE_USER_IS_NULL(FAIL.getErrCode(), "邀请人不存在"),
	RED_ISSUE_FAILURE(FAIL.getErrCode(), "红包发放失败"),
	VERSION_IS_NULL(FAIL.getErrCode(), "版本必传"),

	SERVICE_PROVIDER_CITY_IS_NULL(FAIL.getErrCode(), "该服务商没有所在站"),

	INSERT_DATA_IS_FAIL(FAIL.getErrCode(), "数据插入失败"),

	INSERT_DATA_IS_EXIST(FAIL.getErrCode(),"插入数据重复!"),

	//30000开头为工友和业主信息相关的错误码
	WORKER_IS_NULL(FAIL.getErrCode(),"工友不存在"),
	HOWNER_IS_NULL(FAIL.getErrCode(),"业主不存在"),
	WORKER_APPOINT_ERROR(FAIL.getErrCode(),"预约失败"),
	WORKER_NEWS_NOT_EXIST(FAIL.getErrCode(), "当前工友新闻信息不存在"),
	HOWNER_NEWS_NOT_EXIST(FAIL.getErrCode(), "当前业主新闻信息不存在"),


	//40000开头的为订单信息相关错误码
	ORDER_IS_NULL(FAIL.getErrCode(),"订单号不能为空"),
	ORDER_NOT_NUM(FAIL.getErrCode(),"无此单号"),
	ORDER_DIFF_NUM(FAIL.getErrCode(),"无权操作"),
	;


	 private BusinessErrorEnum(int errCode, String errMsg){
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	private int errCode;
	private String errMsg;

	@Override
	public int getErrCode() {
		return errCode;
	}

	@Override
	public String getErrMsg() {
		return errMsg;
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}
}
