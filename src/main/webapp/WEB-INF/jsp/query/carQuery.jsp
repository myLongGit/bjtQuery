<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/jsp/query/base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctxsta}/css/main.css"/>

</head>
<body>
<div class="container">
    <div class="search_warp">
        <div class="search_title">违章查询</div>
        <div class="search_info clearfix">
            <ul>
                <li>
                    <span>车辆类型</span>
                    <select name="carType" id="carType" class="select_type">
                        <option value="01A1">A1 大型客车</option>
                        <option value="01A2">A2 牵引货车</option>
                        <option value="01B1">B1 中型客车</option>
                        <option value="01B2">B2 大型货车</option>
                        <option value="02">小型汽车</option>
                        <option value="07">两三轮摩托车</option>
                        <option value="06">外籍汽车</option>
                        <option value="15">挂车</option>
                    </select>
                </li>
                <li>
                    <span>车辆用途</span>
                    <select name="carUsage" id="carUsage" class="select_type">
                        <option value="1">公司车</option>
                        <option value="2">私家车</option>
                    </select>
                </li>
                <li>
                    <span>车牌号码</span>
                    <select name="province" id="province" class="select_type" onchange="ChangeProvinceSelect(this.selectedIndex)">
                        <option value="粤">粤</option>
                    </select>
                    <select name="cities" id="cities" class="select_type" onchange="ChangeCitiesSelect(this.selectedIndex)">
                        <option value="粤A">粤A</option>
                    </select>
                    <input type="text" name="carNumber" id="carNumber" class="input_type" value="" placeholder="车牌号后5位" />
                </li>
                <li>
                    <span>车架号</span>
                    <input type="text" name="frameNumber" id="frameNumber" class="input_type" value="" placeholder="请输入车架号后6位" />
                </li>
                <li>
                    <span>发动机</span>
                    <input type="text" name="engineNumber" id="engineNumber" class="input_type" value="" placeholder="请输入发动机号后6位" />
                </li>
            </ul>
        </div>
        <div class="search_content">
            <div id="searchDetail" class="search_detail">
                <div class="detail_title">查询结果：<span id="carNumShow" style="font-size: 16px; font-weight: bold;"></span>&nbsp;&nbsp;共 <span id="detailNum" style="color: #ef133c;">0</span> 条未处理结果</div>
                <div class="detail_content">
                    <div class="content_title">违章信息</div>
                    <table id="detailTable" class="detail_table">
                        <tr>
                            <th width="10%">违章时间</th>
                            <th width="10%">违章地点</th>
                            <th width="30%">违章内容</th>
                            <th width="10%">文书号</th>
                            <th width="5%">扣分</th>
                            <th width="10%">罚金</th>
                            <th width="10%">滞纳金</th>
                            <th width="10%">违章城市</th>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="search_btn">
                <a><button id="search" class="search" onclick="javascript:query();">立即查询</button></a>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${ctxsta}/js/jquery-1.9.1.min.js" type="text/javascript" charset=utf-8></script>
<script src="${ctxsta}/js/placeholder.js" type="text/javascript" charset=utf-8></script>

<script type="text/javascript" charset=utf-8>
    var province = [];
    var cities = [];
    var pro_index = 0;
    var city_detail = null;
    var _engineNumber = 0;
    var _frameNumber = 0;
    window.onload = function () {
        $.ajax({
            type:"POST",
            url:"./getProvinces",
            dataType: 'json',
            data: {
                "paramter": 1,
            },
            cache: false,
            success:function(e){
                if(e.resultCode=="0"){
                    var pro_data = e.data;
                    for (var i=0; i < pro_data.length; i++) {
                        province[i] = pro_data[i].prefix;
                        cities[i] = pro_data[i].cities;
                    }
                    setData();
                    ChangeProvinceSelect(0);
                    ChangeCitiesSelect(0);
                }else{
                    alert("第三方服务器查询出现异常");
                }
            },
            error:function(e){
                alert("查询出现异常");
                window.location.reload();
            }
        });
    }
    function setData() {
        var _provin = document.getElementById("province");
        //申请空间
        _provin.length = province.length;
        //添加元素
        for (var i = 0; i < province.length; i++) {
            _provin.options[i].text = province[i];
            _provin.options[i].value = province[i];
        }
    }

    function ChangeProvinceSelect(index){
        pro_index = index;
        var _cities = document.getElementById("cities");

        _cities.length = cities[index].length;

        for (var i = 0; i < cities[index].length; i++)
        {
            _cities.options[i].text = cities[index][i].prefix;
            _cities.options[i].value = cities[index][i].prefix;
        }
        _cities.options[0].selected = true;
        ChangeCitiesSelect(0);
    }

    function ChangeCitiesSelect(index){
        city_detail = cities[pro_index][index];
        _engineNumber = city_detail.engineNumber;
        _frameNumber = city_detail.frameNumber;
        switch(_frameNumber){
            case 99:
                $("#frameNumber").attr({'placeholder':'请输入完整车架号','disabled':false});
                break;
            case 0:
                $("#frameNumber").attr({'placeholder':'无需输入车架号','disabled':'disabled'});
                break;
            default:
                $("#frameNumber").attr({'placeholder':'请输入车架号后'+_frameNumber+'位','disabled':false});
        }

        switch(_engineNumber){
            case 99:
                $("#engineNumber").attr('placeholder','请输入完整发动机号');
                break;
            default:
                $("#engineNumber").attr('placeholder','请输入发动机号后'+_engineNumber+'位');
        }
    }

    //查询
    function query(){
        $("#detailTable").find("tr").eq(0).nextAll().remove();
        $("#carNumShow").text("");
        $("#detailNum").text(0);
        var carType=$("#carType").val();//车辆类型
        var carUsage=$("#carUsage").val();//车辆用途
        var carNumber=$("#carNumber").val();//车牌号
        var frameNumber=$("#frameNumber").val();//支架好
        var engineNumber=$("#engineNumber").val();//发动机
        var cities=$("#cities").val();//车牌前缀
        if($.trim(carNumber).length==0){
            alert("请正确车牌号!");
            return false;
        }
        if($.trim(carNumber).length != 5){
            alert("请输入车牌号后5位!");
            return false;
        }
        if($.trim(frameNumber).length==0){
            alert("请输入汽车支架号");
            return false;
        }
        if($.trim(frameNumber).length != 6){
            alert("请输入汽车支架号后6位");
            return false;
        }
        if($.trim(engineNumber).length==0){
            alert("请输入汽车发动机号");
            return false;
        }
        if($.trim(engineNumber).length != 6){
            alert("请输入汽车发动机号后6位");
            return false;
        }
        $.ajax({
            url: "<%=request.getContextPath()%>/TrafficQuery",
            type: "POST",
            dataType: "json",
            data: {
                "carType": carType,
                "carUsage":carUsage,
                "carNumber":cities+carNumber,
                "carFrameNumber":frameNumber,
                "carEngineNumber":engineNumber
            },

            success: function(data) {
                if(data.resultCode=='0'){
                    var _data = data.data;
                    var temp = "";
                    var _carTitle = $("#cities option:selected").text();
                    var _carLater = $("#carNumber").val();
                    var _carNumShow = _carTitle + _carLater;
                    $("#carNumShow").text(_carNumShow);
                    $("#detailNum").text(_data.length);
                    for (var i=0; i<_data.length; i++) {
                        /*switch(_data[i].carType){
                         case '01A1':
                         _data[i].carType = "A1 大型客车";
                         break;
                         case '01A2':
                         _data[i].carType = "A2 牵引货车";
                         break;
                         case '01B1':
                         _data[i].carType = "B1 中型客车";
                         break;
                         case '01B2':
                         _data[i].carType = "B2 大型货车";
                         break;
                         case '02':
                         _data[i].carType = "小型汽车";
                         break;
                         case '07':
                         _data[i].carType = "两三轮摩托车";
                         break;
                         case '06':
                         _data[i].carType = "外籍汽车";
                         break;
                         case '15':
                         _data[i].carType = "挂车";
                         break;
                         }

                         switch(_data[i].carUsage){
                         case 1:
                         _data[i].carUsage = "公司车";
                         break;
                         case 2:
                         _data[i].carUsage = "私家车";
                         break;
                         }

                         switch(_data[i].locationType){
                         case 0:
                         _data[i].locationType = "户籍地";
                         break;
                         case 1:
                         _data[i].locationType = "违法发生地";
                         break;
                         }*/

                        temp ="<tr>"
                            +"<td>"+_data[i].violationTime+"</td>"
                            +"<td>"+_data[i].violationLocation+"</td>"
                            +"<td>"+_data[i].violationDesc+"</td>"
                            +"<td>"+_data[i].docNumber+"</td>"
                            +"<td>"+_data[i].demeritPoints+"</td>"
                            +"<td>"+_data[i].forfeit+"</td>"
                            +"<td>"+_data[i].demurrage+"</td>"
                            +"<td>"+_data[i].violationCity+"</td>"
                            +"</tr>";
                        $("#detailTable").append(temp);
                        temp = "";
                    }
                    $("#searchDetail").show();
                    pushHistory();
                    //window.location.reload();
                }

            },
            error: function() {
                alert("查询出现异常!");
            }
        });
    }

    //监听点击返回按钮
    window.addEventListener("popstate", function(e) {
        window.location.reload();
    }, false);
    //为页面增加一级链接
    function pushHistory() {
        var state = {
            title: "",
            url: "#"
        };
        window.history.pushState(state, "", "#");
    }
</script>
</html>