
package com.github.binarywang.demo.wechat.service.util.StringUtil;

import java.io.Serializable;

import com.github.binarywang.demo.wechat.service.common.SystemConstants;


/**
 * RestAPIResult2 <br/>
 * Function: REST API接口统一响应接口实体. <br/>
 * 
 */
//@ApiModel(value = "REST API接口统一响应接口实体")
public class RestAPIResult2<T> implements Serializable {

	/**
	 * serialVersionUID:
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@ApiModelProperty(value = "respCode : 返回代码，1表示成功，其它的都有对应问题")
    private int respCode = 1;

//    @ApiModelProperty(value = "respMsg : 如果code!=1,错误信息")
    private String respMsg="成功！";
    
    private String dataCode;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

	public RestAPIResult2(String errorMsg){
		this.respMsg = errorMsg;
		this.respCode =SystemConstants.Code.error;
	}
	
	public RestAPIResult2(){
	}
 
	public void success(T object){
		this.respCode = SystemConstants.Code.success;
		this.respMsg = SystemConstants.Code.SUCCESS;
	}
	public void error(){
		this.respCode = SystemConstants.Code.error;
		this.respMsg = SystemConstants.Code.FAIL;
	}
	public void error(String message){
		this.respCode = SystemConstants.Code.error;
		this.respMsg = message;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	
}
