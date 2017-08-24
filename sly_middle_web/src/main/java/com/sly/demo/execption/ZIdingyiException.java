package com.sly.demo.execption;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONObject;
import com.sly.demo.enmus.ErrorCodeEnum;
import com.sly.demo.execption.BizException;
import com.sly.demo.model.APIResult;
/**
 * 
 * @ClassName: ZIdingyiException
 * @Description: 全局的异常
 * @author: sly
 * @date: 2017年8月23日 上午11:13:24
 */
public class ZIdingyiException  implements HandlerExceptionResolver{
	
	@ResponseBody
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		APIResult result = null;
		if (ex instanceof RemotingException) {
//			logger.info("dubbo服务Remoting异常,具体错误为：" + ex.getMessage());
			result = new APIResult(ErrorCodeEnum.DUBBO_ERROR.getCode(), ErrorCodeEnum.DUBBO_ERROR.getMsg());
		} else if (ex instanceof RpcException) {
//			logger.info("dubbo服务Rpc异常,具体错误为：" + ex.getMessage());
			result = new APIResult(ErrorCodeEnum.DUBBO_ERROR.getCode(), ErrorCodeEnum.DUBBO_ERROR.getMsg());
		} else if (ex instanceof IOException) {
//			logger.info("请求资源找不到,具体错误为：" + ex.getMessage());
			result = new APIResult(ErrorCodeEnum.IO_ERROR.getCode(), ErrorCodeEnum.IO_ERROR.getMsg());
		} else if (ex instanceof BizException) {
			BizException bizException = (BizException) ex;
//			logger.info("业务异常，请稍后再试！具体错误为：" + bizException.getMessage());

			result = new APIResult(bizException.getCode(), bizException.getMsg());
		} else {
//			logger.info("系统错误，请稍后再试！具体错误为：" + ex.getMessage());
			result = new APIResult(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMsg());
		}
		
		System.err.println("------");
		responseOutWithJson(response, result);
		return null;
	}
	/**     
	 * 返回json数据 <br/> 
	 * responseOutWithJson <br/> 
	 * @param response
	 * @param result  void <br/>   
	 */
	protected void responseOutWithJson(HttpServletResponse response, APIResult result) {
		String jsonStr = JSONObject.toJSONString(result);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(jsonStr);
		} catch (IOException e) {

		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}