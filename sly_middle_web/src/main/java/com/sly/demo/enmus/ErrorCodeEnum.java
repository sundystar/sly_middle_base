package com.sly.demo.enmus;

/**
 * 
 * <b>Description：</b> 业务错误异常码 <br/>
 * <b>ClassName：</b> ErrorCodeEnum <br/>
 * <b>@author：</b> jackyshang <br/>
 * <b>@date：</b> 2016年7月14日 下午3:11:45 <br/>
 * <b>@version: </b>  <br/>
 */
public enum ErrorCodeEnum {
	
	SYSTEM_ERROR( "P-0001","系统出错"),
	
	TOCKEN_ERROR( "P-0002","token验证失败"),
	
	DAL_ERROR( "P-0003","数据访问层异常"),
	
	PARAM_CHECK_ERROR( "P-0004","参数校验失败异常"),
	
	EXCLE_HEADER_EMPTY( "P-0005","列名为空"),
	
	PARAM_IS_NULL( "P-0006","参数为空验证失败"),
	
	IO_ERROR( "P-0007","请求资源找不到"),
	
	DUBBO_ERROR( "P-0008","Dubbo服务调用异常"),
	
//	REMOTING_ERROR( "P-0008","Dubbo服务调用异常"),
	
	JSON_CHECK_ERROR( "P-0009","Json格式不正确"),
	
	WEB_REQUEST_ERROR( "P-0010","Web请求异常"),
	
	WX_OPENID_ERROR( "P-0011","微信OPENID异常"),
	
	TICKET_ERROR( "P-0012","请求重复提交"),
	;
	String code;
	String msg;

	ErrorCodeEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }



    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
