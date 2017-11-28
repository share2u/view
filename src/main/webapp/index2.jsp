<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/29
  Time: 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>css</title>
    <style>
        #container {
            width:100%;
            margin:0px auto;/*主面板DIV居中*/
        }
        #header {
            width:100%;
            height:50px;
            border:1px #F00 solid;
        }
        #main {
            width:100%;
            height:600px;
            border:1px #F00 solid;
        }
        #footer {
            width:100%;
            height:50px;
            border:1px #F00 solid;
        }
        .selectTitle{
            width:100%;
            height: 25px;
            border:1px black solid;

        }
        .cat {
            float:left;
            width:15%;
            height:100%;
            border:1px #F00 solid;
        }
        .content {
            float:left;
            width:85%;
            height:100%;
            border:1px #3eff3a solid;
        }
    </style>
</head>
<body>
<div id="container">
    <div id="header">顶部</div>
    <div id="main">
        <div class="selectTitle">
            <div>下拉选择框与新建图表</div>
        </div>
        <div>
            <div class="cat">
                <div>仪表列表</div>
            </div>
            <div class="content">
                图表list
            </div>
        </div>
    </div>
    <div id="footer">底部</div>
</div>
</body>
</html>
