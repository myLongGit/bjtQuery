package com.bjtQuery.util;



import com.bjtQuery.Entity.ArbRuleCity;
import com.bjtQuery.Entity.ArbRuleProvince;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class ArbQueryApiUtil {

    private static Logger logger = Logger.getLogger(ArbQueryApiUtil.class);

   /**
     * get方式
     * @param url
     * @author www.yoodb.com
     * @return
     */
    public static String getHttp(String url) {
        String responseMsg = "";
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        try {
            httpClient.executeMethod(getMethod);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = getMethod.getResponseBodyAsStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while((len=in.read(buf))!=-1){
                out.write(buf, 0, len);
            }
            responseMsg = out.toString("UTF-8");
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放连接
            getMethod.releaseConnection();
        }
        return responseMsg;
    }

    /**
     * post方式
     * @param url
     * @param appId
     * @param requestsn
     * @param sign
     *@author 查询汽车交通违章情况
     * @return
     */
    public static JSONObject queryCarTraffic(String url, String appId, String requestsn, String sign, String timestamp, CarQueryInfo carQueryInfo) throws Exception{
        JSONObject jsonObject=null;
        StringBuffer buffer = new StringBuffer();
        try {

            // 创建url资源
           URL url1 = new URL(url);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);

            conn.setDoInput(true);

            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("appId", appId);
            conn.setRequestProperty("requestsn", requestsn);
            conn.setRequestProperty("timestamp", timestamp);
            conn.setRequestProperty("sign", sign);
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            //转换为字节数组
            byte[] data = (GsonUtil.toJson(carQueryInfo).toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));

            // 设置文件类型:
            conn.setRequestProperty("Content-type", "application/json; charset=UTF-8");

            // 开始连接请求
            conn.connect();
            OutputStream out = conn.getOutputStream();
            // 写入请求的字符串
            out.write((GsonUtil.toJson(carQueryInfo).toString()).getBytes());
            out.flush();
            out.close();
            // 请求返回的状态
            System.out.println(conn.getResponseCode());
            if(conn.getResponseCode()==200) {
                // 将返回的输入流转换成字符串
                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                // 释放资源
                inputStream.close();
                inputStream = null;
                conn.disconnect();
                jsonObject = JSONObject.fromObject(buffer.toString());

            }

        } catch (Exception e) {

         logger.info("查询第三方接口出现异常"+e.getMessage());
        }
         return jsonObject;


   }

        public static JSONObject sendPostQueryTraffic(String URL, String appId, String requestsn, String sign, String timestamp, CarQueryInfo carQueryInfo) throws Exception{

            // 创建默认的httpClient实例.
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(URL);
            JSONObject jsonObject = null;

            post.setHeader("Content-Type", "application/json");
            post.addHeader("appId", appId);
            post.addHeader("requestsn", requestsn);
            post.addHeader("timestamp", timestamp);
            post.addHeader("sign", sign);
            String result = "";

            try {

                StringEntity s = new StringEntity(GsonUtil.toJson(carQueryInfo).toString(), "utf-8");
                s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                post.setEntity(s);

                // 发送请求
                HttpResponse httpResponse = client.execute(post);


                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                    // 获取响应输入流
                    InputStream inStream = httpResponse.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                    StringBuilder strber = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                        strber.append(line + "\n");
                    inStream.close();

                    result = strber.toString();

                }
            } catch (Exception e) {
                logger.info("查询交通违法第三方接口发生异常:"+e.getMessage());
            }

            return JSONObject.fromObject(result);
        }




    /**
     * post方式
     * @param requestUrl
     * @param appId
     * @param requestsn
     * @param sign
     *@author 车辆违法信息支持省份查询接口
     * @return
     */
    public static JSONObject queryCarTrafficRule(String requestUrl, String appId, String requestsn, String sign,String timestamp) throws Exception{
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {

            URL url = new URL(requestUrl);
            // http协议传输
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setRequestProperty("appId", appId);
            httpUrlConn.setRequestProperty("requestsn", requestsn);
            httpUrlConn.setRequestProperty("timestamp", timestamp);
            httpUrlConn.setRequestProperty("sign", sign);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("POST");
            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            httpUrlConn.setRequestProperty("Content-type", "application/json");

            httpUrlConn.connect();
            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询交通违法规则支持的城市接口出现异常:"+e.getMessage());
        }
        return jsonObject;
    }

    public static void main(String[] args) throws Exception{
        String appId="339376329726099456";
        String appKey="27d7cf4b438d413a9daff57f3c4bae58";
        String apiUrl="http://api.acarbang.com/api/";
        String requestsn=Tools.getCurrTime()+"arbapi";
        String timestamp=Tools.getCurrTime();
        String sign=MD5.encryption((appId+appKey+requestsn+timestamp).trim());
        CarQueryInfo carQueryInfo=new CarQueryInfo();
        carQueryInfo.setCarNumber("粤B3CP89");
        carQueryInfo.setCarFrameNumber("642684");
        carQueryInfo.setCarEngineNumber("642684");
        carQueryInfo.setCarType("02");
//        carQueryInfo.setCarUsage(2);
        JSONObject jsonObject=queryCarTraffic(apiUrl+"/car/violation/query",appId,requestsn,sign,timestamp,carQueryInfo);
        System.out.println(jsonObject);


//        String appId="339376329726099456";
//        String appKey="27d7cf4b438d413a9daff57f3c4bae58";
//        String apiUrl="http://api.acarbang.com/api/";
//        String requestsn= Tools.getCurrTime()+"arbapi";
//        String timestamp= Tools.getCurrTime();
//        String sign= MD5.encryption((appId+appKey+requestsn+timestamp).trim());
//        JSONObject jsonObject=queryCarTrafficRule(apiUrl+"/car/violation/rule",appId,requestsn,sign,timestamp);
//        JSONArray jsonArray=jsonObject.getJSONArray("data");
//        JSONObject object=jsonArray.optJSONObject(0);
//        JSONArray jsonArray1=object.getJSONArray("cities");
//
//        List<ArbRuleProvince> arbRuleProvinceList=new ArrayList<ArbRuleProvince>();
//        for (int i = 0; i < jsonArray.size(); i++) {
//            JSONObject detailObj = jsonArray.optJSONObject(i);
//            ArbRuleProvince detail = GsonUtil.fromJson(detailObj.toString(), ArbRuleProvince.class);
//            arbRuleProvinceList.add(detail);
//        }
//
//        for (ArbRuleProvince arbRuleProvince:arbRuleProvinceList){
//            List<ArbRuleCity> arbRuleCityList=arbRuleProvince.getCities();
//            for (ArbRuleCity a:arbRuleCityList) {
//                System.out.println(a.getPrefix());
//            }
//        }


    }
}
