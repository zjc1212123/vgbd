<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能蔬菜运输车</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="full-screen" content="yes">
    <meta name="browsermode" content="application">
    <meta name="x5-fullscreen" content="true">
    <meta name="x5-page-mode" content="app">
    <link rel="stylesheet" type="text/css" href="css/lxs_index.css"/>
    <link rel="stylesheet" type="text/css" href="css/lxsHeadFoot.css">
    <link rel="stylesheet" type="text/css" href="css/order_new.css"/>
    <link rel="stylesheet" href="layui/css/layui.css">

</head>

<body>

<div class="content">
    <div class="headTop">
        <a href="javascript:history.go(-1)" class="back"><i class="iconBack"></i></a>
        <span>信息填写</span>
        <a class="more"><i class="iconDian"></i><i class="iconDian"></i><i class="iconDian"></i></a>
    </div>
</div>

<div class="j_main m-main">
    <form method="post" id="usage">
        <div class="txt">
            <dl>
                <dt>智能车编号</dt>
                <input class="line30" name="carNumber" value="${carNumber}">
            </dl>
        </div>
        <div class="tit"><i></i>填写用户信息</div>
        <div class="txt">
            <!-- 用户姓名 -->
            <dl>
                <dt>用户姓名</dt>
                <dd><input type="text" name="username" class="o_man" placeholder="真实姓名（必填）" value="">
                </dd>
            </dl>
            <!-- 联系电话 -->
            <dl>
                <dt>联系电话</dt>
                <dd class="pd0"><input type="tel" name="phone" class="o_number" placeholder="手机号码（必填）" value=""></dd>
            </dl>
            <!-- 使用开始时间 -->
            <dl>
                <dt>使用开始时间</dt>
                <dd>
                    <input type="text" name="startTime" class="o_man">
                </dd>
            </dl>

        </div>
        <div class="tourist_box">
            <button id="queryGreenhouse" class="layui-btn layui-btn-sm"
                    style="background-color:#00BF49;float:right;margin: 10px">查询
            </button>
            <div class="tit"><i></i>蔬菜大棚信息</div>
            <div class="txt">
                <dl>
                    <dt>户主</dt>
                    <dd>
                        <input type="text" name="householder" class="o_man" placeholder="请填写户主">
                    </dd>
                </dl>
                <dl>
                    <dt>大棚编号</dt>
                    <dd>
                        <input type="text" name="greenhouseNumber" class="o_man" placeholder="请填写大棚编号">
                    </dd>
                </dl>
                <dl>
                    <dt>年份</dt>
                    <dd>
                        <input type="text" name="createTime" class="o_man" placeholder="请填写年份">
                    </dd>
                </dl>
                <!-- 大棚信息 -->
                <div id="greenhouseInfo"></div>
            </div>
        </div>
    </form>
    <!-- 提交按钮 -->
    <div class="submitFix">
        <dl>
            <dt></dt>
            <dd class="sbmFix">
                <button type="button" id="save">提交</button>
            </dd>
        </dl>
    </div>
</div>
</body>
<script src="js/jquery-2.1.4.min.js"></script>
<script src="layui/layui.js"></script>
<script src="js/min_com.js"></script>
<script src="js/dateformat.js"></script>
<script>
    //一般直接写在一个js文件中
    layui.use(['layer', 'form'], function () {
        var layer = layui.layer,
            form = layui.form;

        /*监听查询大棚信息*/
        $("#queryGreenhouse").on("click", function () {
            //获取表单的值
            var formData = {};
            formData.householder = $("input[name='householder']").val();
            formData.greenhouseNumber = $("input[name='greenhouseNumber']").val();
            formData.createTime = $("input[name='createTime']").val();
            console.log(formData);
            $.post("../mobile/getGreenhouse", {greenhouse: JSON.stringify(formData)}, function (result) {
                console.log(result);
                var greenhouse = result.data;
                if (greenhouse !== null) {
                    $("#greenhouseInfo").html("<input type='text' style='display: none' name='greenhouseCreateTime' value='" + greenhouse.createTime + "'>" +
                        "                        <dl><dt>省</dt>\n" +
                        "                            <dd><span>" + greenhouse.province + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>市</dt>\n" +
                        "                            <dd><span>" + greenhouse.city + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>县/区</dt>\n" +
                        "                            <dd><span>" + greenhouse.county + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>村</dt>\n" +
                        "                            <dd><span>" + greenhouse.village + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>地址</dt>\n" +
                        "                            <dd><span>" + greenhouse.address + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>大棚面积</dt>\n" +
                        "                            <dd><span>" + greenhouse.area + "</span></dd>\n" +
                        "                        </dl>\n" +
                        "                        <dl><dt>品种面积</dt>\n" +
                        "                            <dd id='vars'></dd>\n" +
                        "                        </dl>");
                    var variety = JSON.parse(greenhouse.variety);
                    console.log(variety);
                    for (var key in variety) {
                        console.log(key)
                        value = variety[key];
                        if (key === "01") {
                            key = "白菜"
                        } else if (key === "02") {
                            key = "西红柿";
                        } else if (key === "03") {
                            key = "黄瓜";
                        } else if (key === "04") {
                            key = "土豆";
                        }
                        $("#vars").append("<span>" + key + " " + value + "</span>")
                    }
                } else {
                    $("#greenhouseInfo").html("<dl>\n" +
                        "                            <dd>\n" +
                        "                                <p style='text-align: center;'>未查询到信息</p>\n" +
                        "                            </dd>\n" +
                        "                        </dl>")
                }
            });
            return false;
        });

        /*监听表单提交*/
        $("#save").on("click", function () {
            //获取表单的值
            var formData = $("#usage").serializeJson();
            console.log(formData);
            //表单校验
            if (formData.username === "") {
                //用户名
                layer.msg("请输入用户名", {icon: 5});
            } else if (formData.userphone === "") {
                //联系电话
                layer.msg("请输入联系电话", {icon: 5});
            } else {
                //提交请求
                $.post("../mobile/saveUsage", {usage: JSON.stringify(formData)}, function (data) {
                    console.log(data);
                });
            }
        })
    });
    //设置当前时间
    $("input[name='startTime']").val(new Date().format("yyyy-MM-dd HH:mm:ss"))

    //表单序列化json
    $.fn.serializeJson = function () {
        var serializeObj = {};
        $(this.serializeArray()).each(function () {
            serializeObj[this.name] = this.value;
        });
        return serializeObj;
    };
</script>

</html>