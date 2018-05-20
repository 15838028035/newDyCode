
package com.lj.cloud.weixin.entity;

import java.io.Serializable;

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
}
