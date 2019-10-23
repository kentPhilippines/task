package com.task.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DayAll {
    private Integer id;

    private Date reqDate;

    private Integer reqYear;

    private Integer reqMonth;

    private Integer reqDay;

    private Integer reqWeek;

    private Integer reqdayType;

    private Date createTime;

    private Date submitTime;

    private String submitSystem;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public Integer getReqYear() {
        return reqYear;
    }

    public void setReqYear(Integer reqYear) {
        this.reqYear = reqYear;
    }

    public Integer getReqMonth() {
        return reqMonth;
    }

    public void setReqMonth(Integer reqMonth) {
        this.reqMonth = reqMonth;
    }

    public Integer getReqDay() {
        return reqDay;
    }

    public void setReqDay(Integer reqDay) {
        this.reqDay = reqDay;
    }

    public Integer getReqWeek() {
        return reqWeek;
    }

    public void setReqWeek(Integer reqWeek) {
        this.reqWeek = reqWeek;
    }

    public Integer getReqdayType() {
        return reqdayType;
    }

    public void setReqdayType(Integer reqdayType) {
        this.reqdayType = reqdayType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitSystem() {
        return submitSystem;
    }

    public void setSubmitSystem(String submitSystem) {
        this.submitSystem = submitSystem == null ? null : submitSystem.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}