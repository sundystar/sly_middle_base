/**
* 上海轩言网络信息科技有限公司
* Copyright (c) 2016, xuanyan All Rights Reserved.
*/
package com.sly.demo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class APIRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 
     * AB标识 
     */
    private String ABFlag;
    /**
     * 微信用户唯一标示 OpenID
     */
    private String wxOpenID;
    /**
     * 操作票据，用来验证重复提交
     */
    private String opTicket;
    /** 
     * 请求来源 
     */
    private String source;

    /** 
     * 发起请求时间戳</br>
     * 格式：timestamp
     */
    private String time;

    /** 
     * 请求数据
     */
    private String data;
    
    /**
     * map类型的数据
     */
    private Map<String,Object> dataMap;

    
    /**
     * 客户端请求IP
     */
    private String clientIP;
    /**
     * 请求协议
     */
    private String schema;

    /**
     * opTicket
     *
     * @return  the opTicket
     */
    
    public String getOpTicket() {
        return opTicket;
    }

    /**
     * @param opTicket the opTicket to set
     */
    public void setOpTicket(String opTicket) {
        this.opTicket = opTicket;
    }

    public APIRequest() {
        this.time = String.valueOf(System.currentTimeMillis());
    }

    public APIRequest(String data) {
        super();
        this.data = data;
        this.dataMap =JSONObject.parseObject (data);
        this.time = String.valueOf(System.currentTimeMillis());
    }
    
    public APIRequest(String source, String data) {
        super();
        this.source = source;
        this.dataMap = JSONObject.parseObject (data);
        this.data = data;
        this.time = String.valueOf(System.currentTimeMillis());
    }
    public APIRequest(String source,String time, String data) {
        super();
        this.source = source;
        this.dataMap = JSONObject.parseObject (data);
        this.data = data;
        this.time = time;
    }
    
    public String getABFlag() {
        return ABFlag;
    }

    public void setABFlag(String aBFlag) {
        ABFlag = aBFlag;
    }
    
    

    public String getWxOpenID() {
		return wxOpenID;
	}

	public void setWxOpenID(String wxOpenID) {
		this.wxOpenID = wxOpenID;
	}

	public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.dataMap =  JSONObject.parseObject (data);
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }
    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
        
        this.data =JSON.toJSONString(this.dataMap);
    }
    /**
     * 向datamap中设置值
     * putData <br/> 
     * @param k
     * @param v  void <br/>
     */
   public void put(String key, Object value){
       if( this.dataMap==null){
           this.dataMap = new HashMap<String, Object>();
       }
       this.dataMap.put(key, value);
       this.data = JSON.toJSONString(this.dataMap);
    }

	/**
     * clientIP
     *
     * @return  the clientIP
     */
    
    public String getClientIP() {
        return clientIP;
    }

    /**
     * @param clientIP the clientIP to set
     */
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }
    
    public String getSchema() {
        return schema;
    }
    
    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    
}
