package com.example.offer.core;

/**
 * @Author lizhigang Date: 2019/3/18 14:28
 * @description:
 */
public enum ResultCodeEnum {

    FAIL(500, "操作失败"),
    SUCCESS(200, "操作成功"),
    TOKEN_NOT_EXIST("登录信息已失效，请重新登录"),
    LOGIN_SUCCESS(CommonConstant.SUCCESS_CODE, "登陆成功"),
    LOGIN_FAIL(CommonConstant.FAIL_CODE, "登陆失败"),

    MSG_RESUBMIT(CommonConstant.FAIL_CODE,"请求处理中!请稍后再试!"),

    MSG_PARAM_ISVALID(CommonConstant.FAIL_CODE,"参数不合法"),

    /****************************短信登录************************************/
    MSG_ENTRYINFO_NX(CommonConstant.FAIL_CODE,"抱歉,您不是公司员工,无法登录"),

    MSG_MOBILE_NON_EMPTY(CommonConstant.FAIL_CODE,"手机号码不能为空"),

    MSG_CODE_GENERATE_ERROR(CommonConstant.FAIL_CODE,"验证码生成失败"),
    MSG_CODE_EMPTY_OR_TIME_OUT(CommonConstant.FAIL_CODE,"验证码不存在或已过期"),

    MSG_CODE_IMG_NON_EMPTY(CommonConstant.FAIL_CODE,"图片验证码不能为空"),
    MSG_CODE_IMG_ERROR(CommonConstant.FAIL_CODE,"图片验证码错误"),
    MSG_CODE_IMG_UUID_NON_EMPTY(CommonConstant.FAIL_CODE,"图片验证码UUID不能为空"),

    MSG_CODE_NON_EMPTY(CommonConstant.FAIL_CODE,"短信验证码不能为空"),
    MSG_CODE_ERROR(CommonConstant.FAIL_CODE,"短信验证码错误"),
    MSG_SMS_DAY_SEND_EXCEED(CommonConstant.FAIL_CODE,"短信验证码发送次数已达当天上限"),
    MSG_SMS_HOUR_SEND_EXCEED(CommonConstant.FAIL_CODE,"验证码超过每小时发送最大次数"),

    /****************************人员信息************************************/
    MSG_CARDNUM_YX(CommonConstant.FAIL_CODE,"证件号码已存在"),
    MSG_ENTRYNO_NON_EMPTY(CommonConstant.FAIL_CODE,"员工编号不能为空"),
    MSG_ARCHIVESNO_GENERATE_ERROR(CommonConstant.FAIL_CODE,"人才档案编号获取失败"),

    /****************************培训考核课程************************************/
    MSG_TRAINNINGNO_NON_EMPTY(CommonConstant.FAIL_CODE,"培训课程编号不能为空"),

    /****************************员工提奖记录************************************/
    MSG_TJCONFIG_NX(CommonConstant.FAIL_CODE,"提奖比例不存在"),
    ;

    private String message;
    private int code;

    ResultCodeEnum(String message) {
        this.message = message;
    }

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
