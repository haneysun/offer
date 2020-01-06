package com.example.offer.core;

/**
 * @Author lizhigang Date: 2019/3/6 15:30
 * @description:
 */
public interface CommonError {
	public int getErrCode();
	public String getErrMsg();
	public CommonError setErrMsg(String errMsg);
}
