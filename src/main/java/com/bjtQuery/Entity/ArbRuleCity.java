package com.bjtQuery.Entity;

import java.io.Serializable;

/**
* 违法支持省份信息城市类
* @author 
* @create 2017-10-20 14:01
**/
public class ArbRuleCity implements Serializable{
    private String cityName;//城市名字
    private String engineNumber;////需要发动机号位数 99表示完事发动机号，其他表示需要的具体位数
    private String frameNumber;////需要车架号位数，99表示完整车架号，其他表示需要的具体位数
    private String prefix;////车牌前缀
    private String weight;//


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
