package com.weixindev.micro.serv.common.bean.secrity;

import java.util.Date;

public class UpmGroups {
    private Integer id;

    private String groupname;

    private Integer createBy;

    private Date createDate;

    private Integer updateBy;

    private Date updateDate;

    private Date enableFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Date enableFlag) {
        this.enableFlag = enableFlag;
    }
}