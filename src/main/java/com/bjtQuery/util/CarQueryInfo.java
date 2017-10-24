package com.bjtQuery.util;

import java.io.Serializable;

/**
* 查询交通违法实体类
* @author 
* @create 2017-10-20 10:06
**/
public class CarQueryInfo implements Serializable{

    private String carNumber;//车牌号长度8位
    private String carFrameNumber;//车架号,详见2.2
    private String carEngineNumber;//发动机号
    private String carType;//车类型
    private String carUsage;//车辆用途，1公司车，2私家车

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarFrameNumber() {
        return carFrameNumber;
    }

    public void setCarFrameNumber(String carFrameNumber) {
        this.carFrameNumber = carFrameNumber;
    }

    public String getCarEngineNumber() {
        return carEngineNumber;
    }

    public void setCarEngineNumber(String carEngineNumber) {
        this.carEngineNumber = carEngineNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarUsage() {
        return carUsage;
    }

    public void setCarUsage(String carUsage) {
        this.carUsage = carUsage;
    }
}
