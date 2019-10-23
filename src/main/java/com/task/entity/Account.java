package com.task.entity;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Account {
    private Integer id;

    private String accountId;

    private String accountName;

    private Integer accountType;

    private BigDecimal cashBalance;

    private BigDecimal freezeBalance;

    private BigDecimal accountBalance;

    private BigDecimal dayDealAmountMax;

    private BigDecimal dayDealAmountMin;

    private BigDecimal freezeT1;

    private BigDecimal freezeD1;

    private BigDecimal sumDealAmount;

    private BigDecimal sumDealToDayAmount;

    private Date createTime;

    private Date submitTime;

    private String submitSystem;

    private Integer status;

    private Integer isDeal;

    private Integer isDpay;

    private String bankCard;

    private BigDecimal maxDeal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(BigDecimal freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getDayDealAmountMax() {
        return dayDealAmountMax;
    }

    public void setDayDealAmountMax(BigDecimal dayDealAmountMax) {
        this.dayDealAmountMax = dayDealAmountMax;
    }

    public BigDecimal getDayDealAmountMin() {
        return dayDealAmountMin;
    }

    public void setDayDealAmountMin(BigDecimal dayDealAmountMin) {
        this.dayDealAmountMin = dayDealAmountMin;
    }

    public BigDecimal getFreezeT1() {
        return freezeT1;
    }

    public void setFreezeT1(BigDecimal freezeT1) {
        this.freezeT1 = freezeT1;
    }

    public BigDecimal getFreezeD1() {
        return freezeD1;
    }

    public void setFreezeD1(BigDecimal freezeD1) {
        this.freezeD1 = freezeD1;
    }

    public BigDecimal getSumDealAmount() {
        return sumDealAmount;
    }

    public void setSumDealAmount(BigDecimal sumDealAmount) {
        this.sumDealAmount = sumDealAmount;
    }

    public BigDecimal getSumDealToDayAmount() {
        return sumDealToDayAmount;
    }

    public void setSumDealToDayAmount(BigDecimal sumDealToDayAmount) {
        this.sumDealToDayAmount = sumDealToDayAmount;
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

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

    public Integer getIsDpay() {
        return isDpay;
    }

    public void setIsDpay(Integer isDpay) {
        this.isDpay = isDpay;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public BigDecimal getMaxDeal() {
        return maxDeal;
    }

    public void setMaxDeal(BigDecimal maxDeal) {
        this.maxDeal = maxDeal;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}