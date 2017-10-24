package com.bjtQuery.Entity;

import java.io.Serializable;
import java.util.List;

/**
* 违章查询支持省份信息类
* @author 
* @create 2017-10-20 14:07
**/
public class ArbRuleProvince implements Serializable{
    private String provinceName;//省份名字
    private  String prefix;//车牌
    private List<ArbRuleCity> cities;//所在省份的城市信息

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public List<ArbRuleCity> getCities() {
        return cities;
    }

    public void setCities(List<ArbRuleCity> cities) {
        this.cities = cities;
    }
}
