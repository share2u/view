<%--
  Created by IntelliJ IDEA.
  User: 10769
  Date: 2017/11/26
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        * {
            margin:0;
            padding:0;
        }
        #header {
            width:776px;
            height:100px;
            margin:0 auto;
            background:#06f;
        }
        #main {
            width:776px;
            margin:0 auto;
        }
        #main #sidebar {
            width:200px;
            float:left;
            background:#f93;
        }
        #main #containe {
            width:576px;
            float:right;
            background:#dceafc;
        }
        #footer {
            width:776px;
            height:60px;
            margin:0 auto;
            background:#666;
        }
        #clearfloat {
            clear:both;
            height:1px;
            overflow:hidden;
            margin-top:-1px;
        }
    </style>
</head>
<body>
<div id="header"></div>
<div id="main">
    <div id="sidebar">
        <div>hello</div>
        <div>hello</div>
    </div>
    <div id="containe">
        <div>world</div>
        <div>world</div>
        <div>world</div>
        <div>world</div>
    </div>
    <div id="clearfloat"></div>
</div>
<div id="footer"></div>
</body>
</html>
