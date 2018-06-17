package com.weixindev.micro.serv.common.bean.report;

import java.util.Date;

public class WeixinSendHistory {
    private Integer id;

    private Date createDate;

    private String createByUname;

    private Date updateDate;

    private String mediaCategory;

    private Integer imgtextid;

    private String mediaId;

    private Integer userId;

    private String authorizerappid;

    private Integer category;

    private Integer status;

    private String executeResult;

    private String toUserName;

    private String errorUserName;

    private String errorUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateByUname() {
        return createByUname;
    }

    public void setCreateByUname(String createByUname) {
        this.createByUname = createByUname == null ? null : createByUname.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getMediaCategory() {
        return mediaCategory;
    }

    public void setMediaCategory(String mediaCategory) {
        this.mediaCategory = mediaCategory == null ? null : mediaCategory.trim();
    }

    public Integer getImgtextid() {
        return imgtextid;
    }

    public void setImgtextid(Integer imgtextid) {
        this.imgtextid = imgtextid;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuthorizerappid() {
        return authorizerappid;
    }

    public void setAuthorizerappid(String authorizerappid) {
        this.authorizerappid = authorizerappid == null ? null : authorizerappid.trim();
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(String executeResult) {
        this.executeResult = executeResult == null ? null : executeResult.trim();
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName == null ? null : toUserName.trim();
    }

    public String getErrorUserName() {
        return errorUserName;
    }

    public void setErrorUserName(String errorUserName) {
        this.errorUserName = errorUserName == null ? null : errorUserName.trim();
    }

    public String getErrorUserId() {
        return errorUserId;
    }

    public void setErrorUserId(String errorUserId) {
        this.errorUserId = errorUserId == null ? null : errorUserId.trim();
    }
}