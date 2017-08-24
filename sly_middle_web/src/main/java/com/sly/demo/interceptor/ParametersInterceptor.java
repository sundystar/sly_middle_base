package com.sly.demo.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ParametersInterceptor extends HandlerInterceptorAdapter{

	/** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */ 
	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
			String source = request.getParameter("source");
			String time = request.getParameter("time");
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
		    String retResult = "{\"status\":\"0\",\"errorCode\":\"\",\"msg\":\"来源或时间不能为null\",\"data\":{}}";
		    String sourceResult = "{\"status\":\"0\",\"errorCode\":\"\",\"msg\":\"来源不正确\",\"data\":{}}";
		   try {
			   
			   System.err.println(request.getMethod());
			   if(request.getMethod().equals("GET")){
				  if(null == source){
			    	   response.getWriter().write(retResult);
			    	   return false;
			       }else{
			    	  if(!source.equals("101")){
				    	   response.getWriter().write(sourceResult);
			    		  return false;
			    	  }
			       }
			       if(null == time){
			    	   response.getWriter().write(retResult);
			    	   return false;
			       }
			   }
			} catch (Exception e) {
				e.printStackTrace();
			}
		   return true;
    }
	
	
}
