 /**
 * 上海轩言网络信息科技有限公司
 * Copyright (c) 2016, xuanyan All Rights Reserved.
 */
package com.sly.demo.execption;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.sly.demo.enmus.ErrorCodeEnum;

/**
 * 
 * <b>Description：</b> 自定义业务异常 <br/>
 * <b>ClassName：</b> DALException <br/>
 * <b>@author：</b> jackyshang <br/>
 * <b>@date：</b> 2016年7月12日 上午9:51:19 <br/>
 * <b>@version: </b>  <br/>
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = -5875371379845226068L;

	/**
	 * 数据访问层
	 */
	public static final BizException DB_OPERATE_EXCEPTION = new BizException(ErrorCodeEnum.DAL_ERROR.getCode(), "数据库操作异常");

	/**
     * 参数校验异常
     */
    public static final BizException PARAM_INVALIDE_EXCEPTION = new BizException(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode(), "参数校验失败异常");
    
    /**
     * 参数校验异常
     */
    public static final BizException EXCLE_HEADER_EMPTY = new BizException(ErrorCodeEnum.PARAM_CHECK_ERROR.getCode(), "列名为空");
    

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected String code;

	public BizException(String code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}
	

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public  BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}


    @Override
    public String getMessage() {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("code", code);
        map.put("msg", msg);
        return JSONObject.toJSONString(map);
    }
	
	
	
}
