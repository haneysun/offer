package com.example.offer.exception;


import com.example.offer.core.CommonError;

/**
 * @Author lizhigang Date: 2019/3/6 15:53
 * @description:包装器业务异常实现
 */
public class BusinessException extends RuntimeException implements CommonError {
	public CommonError commonError;
	private String errMsg;
	private int errCode;
	public BusinessException(){
	}

	public BusinessException(Throwable cause){
		super(cause);
	}

	public BusinessException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BusinessException(CommonError commonError){
		super();
		this.commonError = commonError;
		this.errCode = commonError.getErrCode();
		this.errMsg = commonError.getErrMsg();
	}

	public BusinessException(CommonError commonError, String msg){
		this.commonError = commonError;
		this.commonError.setErrMsg(msg);
		this.errCode = commonError.getErrCode();
		this.errMsg = msg;
	}

	public BusinessException(String errMsg) {
		this.errMsg = errMsg;
	}

	public BusinessException(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	@Override
	public int getErrCode() {
		return commonError == null ? errCode : commonError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		return commonError == null ? errMsg : commonError.getErrMsg();
	}

	@Override
	public CommonError setErrMsg(String errMsg) {
		this.commonError.setErrMsg(errMsg);
		this.errMsg = errMsg;
		return this;
	}
}
