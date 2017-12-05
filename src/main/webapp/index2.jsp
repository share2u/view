<%@ page language="Java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>图表编辑</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <meta name="applicable-device" content="mobile">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet"
          href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />
    <link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />
    <link rel="stylesheet" href="assets/css/ace.min.css"
          class="ace-main-stylesheet" id="main-ace-style" />
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-part2.min.css" class="ace-main-stylesheet" />
    <![endif]-->
    <link rel="stylesheet" href="assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="css/view.css" />

    <!--[if lte IE 9]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
    <![endif]-->

    <!-- inline styles related to this page -->

    <!-- ace settings handler -->
    <script src="assets/js/ace-extra.min.js"></script>

    <!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

    <!--[if lte IE 8]>
    <script src="assets/js/html5shiv.min.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->
    <style>
        body{
            color:#393939;
        }
        #container {
            width:100%;
            margin:0px auto;/*主面板DIV居中*/
        }
        #header {
            width:100%;
            height:50px;
            border:1px #F00 solid;
            background-color: #438eb9;
        }
        #main {
            width:100%;
            height:600px;
            border:1px #F00 solid;
            background-color: #f2f2f2;
        }
        #footer {
            width:100%;
            height:50px;
            border:1px #F00 solid;
        }
        .selectTitle{
            width:100%;
            height: 30px;
            border:1px black solid;
            background-color: #f2f2f2;

        }
        .cat {
            float:left;
            width:15%;
            height:100%;
            border:1px #F00 solid;
            background-color: #fff;
        }
        .content {
            float:left;
            width:85%;
            height:100%;
            border:1px #3eff3a solid;
            background-color: #fff;
        }
        #selectTableID{
            float: left;
            width: 75%;
        }
        #selectTableID select{
            width: 200px;
        }
        .newViewID{
            float: left;
            margin-right: 30px;
            width: 20%;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="header">顶部</div>
    <div id="main">
        <div class="selectTitle">
            <div id="selectTableID">
                <select name="number" id="number">
                    <option value="0">请选择数据源</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
            <div class="newViewID">
                <a href="##">新增图表</a>
            </div>

        </div>
        <div style="clear: both;"></div>
        <div>
            <div class="cat">
                <ul>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                    <li>aaaaa</li>
                </ul>
            </div>
            <div class="content">
                图表list
            </div>
        </div>
    </div>
    <div id="footer">底部</div>
</div>
<script src="assets/js/jquery-2.1.4.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script>
    $("#selectTableID select").change(function(){
        console.log($(this).val());
    })
</script>

</body>
</html>
