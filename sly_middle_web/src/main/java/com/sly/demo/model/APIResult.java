/**
* 上海轩言网络信息科技有限公司
* Copyright (c) 2016, xuanyan All Rights Reserved.
*/
package com.sly.demo.model;

import java.io.Serializable;

import com.sly.demo.enmus.APIStatusEnum;

/**
 * 
 * @author sly
 *
 */
public class APIResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 返回状态
     */
    private String status;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 接口返回信息
     */
    private String msg;

    /**
     * 接口返回值
     */
    private Object data;

    public static APIResult fail(String errorCode, String msg, Object data) {
        return new APIResult(errorCode, msg, data);
    }

    public static APIResult fail(String errorCode, String msg) {
        return new APIResult(errorCode, msg);
    }

    public static APIResult success() {
        return new APIResult();
    }

    public static APIResult success(Object data) {
        return new APIResult(data);
    }

    public APIResult() {
        super();
        this.status = APIStatusEnum.SUCCESS.getValue();
    }

    public APIResult(Object data) {
        super();
        this.status = APIStatusEnum.SUCCESS.getValue();
        this.data = data;
    }

    public APIResult(String errorCode, String msg) {
        super();
        this.status = APIStatusEnum.FAIL.getValue();
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public APIResult(String errorCode, String msg, Object data) {
        super();
        this.status = APIStatusEnum.FAIL.getValue();
        this.errorCode = errorCode;
        this.msg = msg;
        this.data = data;
    }

    /**
     * errorCode
     *
     * @return the errorCode
     */

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode
     *            the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.status = APIStatusEnum.FAIL.getValue();
        this.errorCode = errorCode;
    }

    /**
     * status
     *
     * @return the status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
        // 如果返回状态是成功，清空错误码和消息
        if (status != null && status.equals(APIStatusEnum.SUCCESS.getValue())) {
            this.errorCode = "";
            this.msg = "";
        }

    }

    /**
     * msg
     *
     * @return the msg
     */

    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.status = APIStatusEnum.FAIL.getValue();
        this.msg = msg;
    }

    /**
     * data
     *
     * @return the data
     */

    public Object getData() {
        return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

}
