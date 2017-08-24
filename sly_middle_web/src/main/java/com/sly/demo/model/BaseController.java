package com.sly.demo.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sly.demo.execption.BizException;

public abstract class BaseController {
	
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

	
		protected APIRequest apiRequest;
	    /**
	     * HttpServletRequest
	     */
	    private HttpServletRequest request;

	    /**
	     * HttpServletResponse
	     */
	    private HttpServletResponse response;
	    
	    /**
	     * request的map参数
	     */
	    @SuppressWarnings("rawtypes")
	    protected Map paramMap = null;
	    
	    /**
	     * jsonp回调函数名称
	     */
	    protected String jsonpCallback = null;
	    
	    /**
	     * 设置request和response
	     * 
	     * @param request
	     * @param response
	     */
	    @ModelAttribute
	    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
	        this.request = request;
	        this.response = response;
	    }
	    JSONObject retJson;
		public void initParams() {
	        //获取apirequest
	        apiRequest = getAPIRequestByParamenters();
//	        accessLogger.info("begin access url:" + getRequest().getRequestURL() + 
//	                          ",来源："+ apiRequest.getSource()+
//	                          ",访问用户："+ (getCurrentUser()==null? "游客" :getCurrentUser().getLoginUserName()) +
//	                          ",访问IP:"+ getIpAddr());         
	       
	        // 初始化返回json对象
	        this.retJson = new JSONObject();
	        //设置登录用户信息
	        apiRequest.setSchema(getRequest().getScheme());
	        //获取跨域的jsonpCallback
	        this.paramMap = this.getRequestParamters();
	        if (null != this.paramMap.get("params")) {
	            this.jsonpCallback = (String) this.paramMap.get("jsonpCallback");
	        } 
	        
	        Cookie[] cookies= getRequest().getCookies();
	        if(cookies!=null && cookies.length >0){
	            for(Cookie cookie : cookies){
	                String abFlag =cookie.getName();// get the cookie name
	                if("AB-flag".equalsIgnoreCase(abFlag)){
	                    apiRequest.setABFlag(cookie.getValue());
	                }
	                
	               
	            }
	        }
	    }
		
		private Map getRequestParamters() {
			// 获取所有的请求参数
	        Map properties = this.getRequest().getParameterMap();
	        // 返回值Map
	        Map returnMap = new HashMap();
	        Iterator entries = properties.entrySet().iterator();
	        Map.Entry entry;
	        String name = "";
	        String value = "";
	        // 读取map中的值
	        while (entries.hasNext()) {
	            entry = (Map.Entry) entries.next();
	            name = (String) entry.getKey();
	            Object valueObj = entry.getValue();
	            if (null == valueObj) {
	                value = " ";
	                returnMap =JSONObject.parseObject(name);
	            } else if (valueObj instanceof String[]) {
	                String[] values = (String[]) valueObj;
	                for (String value2 : values) {
	                    value = value2 + ",";
	                }
	                value = value.substring(0, value.length() - 1);
	            } else {
	                value = valueObj.toString();
	            }
	            // 将读取到的值存入map中
	            returnMap.put(name, value);
	        }
	        return returnMap;
		}

		private APIRequest getAPIRequestByParamenters() {
			
			 APIRequest apirequest =null;
			 
			 String submitMehtod = request.getMethod();
			 
			 if(submitMehtod.equals("GET")){
				 String queryString = request.getQueryString();

				 try {
					 	JSONObject.parse(queryString);
						queryString = queryString ==null?"{}": urlDecode(queryString);
	                    apirequest= JSON.parseObject(queryString,APIRequest.class) ;
						
					} catch (Exception e) {
	                    Map properties = getRequestParamters(request);
	                    if(properties==null){
	                        properties = new HashMap();
	                    }
	                    Map<String, Object> paramMap= new HashMap<String, Object>();
	                    paramMap.put("source", properties.get("source"));
	                    paramMap.put("time", properties.get("time"));
	                    properties.remove("source");
	                    properties.remove("time");
	                    paramMap.put("data", properties);
	                    
	                    apirequest= JSON.parseObject(JSON.toJSONString(paramMap), APIRequest.class);

		                return apirequest;
					}
	                return apirequest;
			 }else{
				 try{
					 	try{
					 		String postJsonStr = getRequestPostStr(request);
					 		JSONObject.parse(postJsonStr);
					 		apirequest = JSON.parseObject(postJsonStr, APIRequest.class);
					 	}catch(Exception e){
					 		 Map properties = getRequestParamters(request);
			                   if(properties==null){
			                       properties = new HashMap();
			                   }
			                   Map<String, Object> paramMap= new HashMap<String, Object>();
			                   paramMap.put("source", properties.get("source"));
			                   paramMap.put("time", properties.get("time"));
			                   properties.remove("source");
			                   properties.remove("time");
			                   paramMap.put("data", properties);
			                   apirequest= JSON.parseObject(JSON.toJSONString(paramMap), APIRequest.class);
					 	}
		                return apirequest;
				 }catch(Exception e){
					 throw new BizException("", "");
				 }

			 }			 
		}

		private String getRequestPostStr(HttpServletRequest request2) throws IOException {
			 byte buffer[] = getRequestPostBytes(request);
		        if(buffer==null){
		            return null;
		        }
		        
		        String charEncoding = request.getCharacterEncoding();
		        if (charEncoding == null) {
		            charEncoding = "UTF-8";
		        }
		        return new String(buffer, charEncoding);
		}
		/**
	     * 描述:获取 post 请求的 byte[] 数组
	     * @param request
	     * @return
	     * @throws IOException
	     */
	    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
	        int contentLength = request.getContentLength();
	        if (contentLength < 0) {
	            return null;
	        }
	        byte buffer[] = new byte[contentLength];
	        for (int i = 0; i < contentLength;) {

	            int readlen = request.getInputStream().read(buffer, i, contentLength - i);
	            if (readlen == -1) {
	                break;
	            }
	            i += readlen;
	        }
	        return buffer;
	    }
		/**
	     * URL 解码, Encode默认为UTF-8.
	     */
	    public static String urlDecode(String input) {
	        try {
	            return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
	        } catch (UnsupportedEncodingException e) {
	            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
	        }
	    }

	    
	    /**
	     * 获得所有请求的参数
	     * 
	     * @param request
	     */
	    @SuppressWarnings({ "rawtypes", "unchecked" })
	    public static Map getRequestParamters(HttpServletRequest request) {
	        // 获取所有的请求参数
	        Map properties = request.getParameterMap();
	        // 返回值Map
	        Map returnMap = new HashMap();
	        Iterator entries = properties.entrySet().iterator();
	        Map.Entry entry;
	        String name = "";
	        String value = "";
	        // 读取map中的值
	        while (entries.hasNext()) {
	            entry = (Map.Entry) entries.next();
	            name = (String) entry.getKey();
	            Object valueObj = entry.getValue();
	            if (null == valueObj) {
	                value = " ";
	             	                
	                returnMap = JSON.parseObject(name,Map.class);
	            } else if (valueObj instanceof String[]) {
	                String[] values = (String[]) valueObj;
	                for (String value2 : values) {
	                    value = value2 + ",";
	                }
	                value = value.substring(0, value.length() - 1);
	            } else {
	                value = valueObj.toString();
	            }
	            // 将读取到的值存入map中
	            returnMap.put(name, value);
	        }
	        return returnMap;
	    }
	    
	    
	    
		private HttpServletRequest getRequest() {
			return this.request;
		}
		
		/**
	     * 获取response对象
	     * 
	     * @return
	     */
	    public HttpServletResponse getResponse() {
	        return this.response;
	    }
}
