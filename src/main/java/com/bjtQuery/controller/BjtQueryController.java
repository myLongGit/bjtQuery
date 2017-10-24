package com.bjtQuery.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.bjtQuery.Entity.ArbRuleProvince;
import com.bjtQuery.Entity.TrafficViolation;
import com.bjtQuery.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import static com.bjtQuery.util.ArbApiConstant.queryUrl;
import static com.bjtQuery.util.ArbQueryApiUtil.queryCarTraffic;
import static com.bjtQuery.util.ArbQueryApiUtil.queryCarTrafficRule;
import static com.bjtQuery.util.ArbQueryApiUtil.sendPostQueryTraffic;


@Controller
public class BjtQueryController {

    @RequestMapping("/carQuery")
    public ModelAndView carQuery(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("query/carQuery");
        return mv;
    }

    //获取支持查询的省份
    @RequestMapping(value = "/getProvinces" , method = RequestMethod.POST)
    @ResponseBody
    public void getProvinces(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        ResultBean rb = new ResultBean();
        try {
            String appId= ArbApiConstant.appId;
            String appKey=ArbApiConstant.appKey;
            String apiUrl=ArbApiConstant.ServerUrl;
            String requestsn= Tools.getCurrTime()+"arbapi";
            String timestamp=Tools.getCurrTime();
            String sign= MD5.encryption((appId+appKey+requestsn+timestamp).trim());
            JSONObject jsonObject=queryCarTrafficRule(apiUrl+ArbApiConstant.ruleUrl,appId,requestsn,sign,timestamp);
            if(jsonObject!=null&&jsonObject.get("code").equals("100000")){
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                List<ArbRuleProvince> arbRuleProvinceList=new ArrayList<ArbRuleProvince>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject detailObj = jsonArray.optJSONObject(i);
                    ArbRuleProvince detail = GsonUtil.fromJson(detailObj.toString(), ArbRuleProvince.class);
                    arbRuleProvinceList.add(detail);
                }

                rb.setResultCode("0");
                rb.setDescription("SUCCESS");
                rb.setData(arbRuleProvinceList);
            }else{
                rb.setResultCode("1");//出现异常
            }
            out = response.getWriter();
            out.append(GsonUtil.toJson(rb).toString());
        }catch (Exception e){
            rb.setResultCode("2");//查询出现异常
        }finally {
            if (out != null) {
                out.close();
            }
        }

    }


    /**
     * 违章查询

     * @throws Exception
     */
    @RequestMapping(value = "/TrafficQuery" )
    @ResponseBody
    public void TrafficQuery(HttpServletRequest request,HttpServletResponse response,CarQueryInfo carQueryInfo) throws Exception{
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        ResultBean rb = new ResultBean();

        String appId = ArbApiConstant.appId;
        String appKey = ArbApiConstant.appKey;
        String apiUrl = ArbApiConstant.ServerUrl;
        String requestsn = Tools.getCurrTime() + "arbapi";
        String timestamp = Tools.getCurrTime();
        String sign = MD5.encryption((appId + appKey + requestsn + timestamp).trim());

        JSONObject jsonObject = sendPostQueryTraffic(apiUrl + queryUrl, appId, requestsn, sign, timestamp, carQueryInfo);
        if (jsonObject != null && jsonObject.get("code").equals("100000")) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            List<TrafficViolation> trafficViolationList = new ArrayList<TrafficViolation>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject detailObj = jsonArray.optJSONObject(i);
                TrafficViolation detail = GsonUtil.fromJson(detailObj.toString(), TrafficViolation.class);
                trafficViolationList.add(detail);
            }

            for (TrafficViolation t:trafficViolationList) {
                if(t.getCarUsage()==null){
                   t.setCarUsage(Integer.parseInt(carQueryInfo.getCarUsage()));
                }
                if(t.getDemeritPoints()==null){
                    t.setCarUsage(0);
                }
                if(t.getCarType()==null){
                    t.setCarType(carQueryInfo.getCarType());
                }
                if(t.getCarFrameNumber()==null){
                    t.setCarFrameNumber(carQueryInfo.getCarFrameNumber());
                }
                if(t.getCarEngineNumber()==null){
                    t.setCarEngineNumber(carQueryInfo.getCarEngineNumber());
                }
                if(t.getCategory()==null){
                    t.setCategory("");
                }
                if(t.getDemurrage()==null){
                    t.setDemurrage("");
                }
                if(t.getDocNumber()==null){
                   t.setDocNumber("");
                }
                if(t.getForfeit()==null){
                    t.setForfeit("");
                }
                if(t.getLocationType()==null){
                    t.setLocationType(1);
                }
                if(t.getViolationCity()==null){
                    t.setViolationCity("");
                }
                if(t.getViolationCode()==null){
                    t.setViolationCode("");
                }
                if(t.getViolationDesc()==null){
                    t.setViolationDesc("");
                }
                if(t.getViolationLocation()==null){
                  t.setViolationLocation("");
                }
                if(t.getViolationTime()==null){
                  t.setViolationTime("");
                }
                if(t.getUniqueCode()==null){
                    t.setUniqueCode("");
                }

            }
            rb.setResultCode("0");
            rb.setDescription("SUCCESS");
            rb.setData(trafficViolationList);
        } else {
            rb.setResultCode("1");//出现异常
        }

        out = response.getWriter();
        out.append(GsonUtil.toJson(rb).toString());

        }
}