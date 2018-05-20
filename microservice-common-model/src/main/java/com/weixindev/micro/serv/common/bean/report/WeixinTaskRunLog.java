package com.weixindev.micro.serv.common.bean.report;
import com.weixindev.micro.serv.common.base.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
*WeixinTaskRunLog
*/
@ApiModel(value = "WeixinTaskRunLog")
public class WeixinTaskRunLog extends BaseEntity{
	
	/**
	 * ID  id
	 */
	@ApiModelProperty(value = "ID")
	private java.lang.Integer id;
	/**
	 * 执行结果描述  logs_desc
	 */
	@ApiModelProperty(value = "执行结果描述")
		private String logsDesc = "";
	
	/**
	 * 定时任务名称  task_name
	 */
	@ApiModelProperty(value = "定时任务名称")
		private String taskName = "";

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public String getLogsDesc() {
		return logsDesc;
	}

	public void setLogsDesc(String logsDesc) {
		this.logsDesc = logsDesc;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}

