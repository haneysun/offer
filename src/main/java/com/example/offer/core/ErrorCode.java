package com.example.offer.core;

/**
 * create by 春春
 *  2019/4/26/9/12.
 */
@SuppressWarnings("ALL")
public class ErrorCode {
    //用户名不存在
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final  String noUser="0001";
    //验证码错误
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final  String getcode="0002";
    //密码错误
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final  String pass="0003";
    //数据不存在
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final  String listnull="0006";
    //w未登录
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String unlogin="0004";
    //未授权
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String unAuth="0005";
    //添加失败
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String addError="0006";
    //更新失败
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String updateError="0007";
    //删除失败
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String deleteError="0008";
    //锁定或者解锁失败
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String lockAndUnLockError="0009";
    //参数错误
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String paramError="0010";
    // 登录次数过多
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String  loginXimes ="0011";
    // 账号已经存在
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String   AccountExists  ="0012";
    // 查询失败
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String   queryCode  ="0013";
    // 账号冻结
    @SuppressWarnings("AlibabaConstantFieldShouldBeUpperCase")
    public static final String   FrozenCode  ="0014";
    //支付签名验证失败
    public static final String signFail ="0015";
}
