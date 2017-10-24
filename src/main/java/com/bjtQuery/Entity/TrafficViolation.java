package com.bjtQuery.Entity;

import java.io.Serializable;

/**
* 交通违法查询信息类
* @author 
* @create 2017-10-20 14:15
**/
public class TrafficViolation implements Serializable{

    private String carEngineNumber;//发动机号
    private String carFrameNumber;//车架号
    private String carType;//车辆类型
    private Integer carUsage;//车辆用途
    private String category;//违法分类，现场单、电子监控
    private Integer demeritPoints;//扣分
    private String demurrage;//滞纳金
    private String docNumber;//文书号
    private String forfeit;//罚金
    private Integer locationType;//违法城市定位类型:0户籍地，1违法发生地
    private String uniqueCode;//违法记录唯一号
    private String violationCity;//违法城市
    private String violationCode;//违法代码
    private String violationDesc;//违法描述
    private String violationLocation;//违法地点
    private String violationTime;//违法时间

    public String getCarEngineNumber() {
        return carEngineNumber;
    }

    public void setCarEngineNumber(String carEngineNumber) {
        this.carEngineNumber = carEngineNumber;
    }

    public String getCarFrameNumber() {
        return carFrameNumber;
    }

    public void setCarFrameNumber(String carFrameNumber) {
        this.carFrameNumber = carFrameNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Integer getCarUsage() {
        return carUsage;
    }

    public void setCarUsage(Integer carUsage) {
        this.carUsage = carUsage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getDemeritPoints() {
        return demeritPoints;
    }

    public void setDemeritPoints(Integer demeritPoints) {
        this.demeritPoints = demeritPoints;
    }

    public String getDemurrage() {
        return demurrage;
    }

    public void setDemurrage(String demurrage) {
        this.demurrage = demurrage;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getForfeit() {
        return forfeit;
    }

    public void setForfeit(String forfeit) {
        this.forfeit = forfeit;
    }

    public Integer getLocationType() {
        return locationType;
    }

    public void setLocationType(Integer locationType) {
        this.locationType = locationType;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getViolationCity() {
        return violationCity;
    }

    public void setViolationCity(String violationCity) {
        this.violationCity = violationCity;
    }

    public String getViolationCode() {
        return violationCode;
    }

    public void setViolationCode(String violationCode) {
        this.violationCode = violationCode;
    }

    public String getViolationDesc() {
        return violationDesc;
    }

    public void setViolationDesc(String violationDesc) {
        this.violationDesc = violationDesc;
    }

    public String getViolationLocation() {
        return violationLocation;
    }

    public void setViolationLocation(String violationLocation) {
        this.violationLocation = violationLocation;
    }

    public String getViolationTime() {
        return violationTime;
    }

    public void setViolationTime(String violationTime) {
        this.violationTime = violationTime;
    }
}
