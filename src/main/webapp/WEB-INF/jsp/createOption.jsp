<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>图表编辑</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta name="format-detection" content="email=no">
    <meta name="applicable-device" content="mobile">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/view.css"/>
    <link rel="stylesheet" href="css/create.css">
    <script src="assets/js/jquery-2.1.4.min.js"></script>


</head>
<body>
<div class="view">
    <div class="topp">
        <div class="t_l">
            <h2 class="view-header-left-name">编辑图表</h2>
        </div>
        <div class="t_r">
            <h3 id="createOption">生成</h3>
            <h3 id="saveOption">保存</h3>
        </div>
    </div>
    <div class="content">
        <div class="content_l">
            <div class="l_top">
                <div class="select-head">
                    <h4>工作表： </h4>
                </div>
                <div class="dropdown" id="tableSelect">
                    <button type="button" class="btn dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">
                        <span id="tableName">请选择一个工作表</span>
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                        <c:forEach items="${tables}" var="table">
                            <li role="presentation">
                                <a role="menuitem" tabindex="-1">${table.tableName}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="l_content">
                <div class="dims-head">
                    <h4>维度列表：</h4>
                </div>
                <div id="ldiv">
                    <ul id="dims" class="list-group">
                    </ul>
                </div>
            </div>
        </div>
        <div class="content_c">
            <div class="c_top">
                <div class="rowDim">
                    <span class="axis-head">
                    维度
                    </span>
                    <div id="r1div"></div>
                </div>
                <div class="rowMes">
                    <span class="axis-head">
                    数值
                    </span>
                    <div id="r2div"></div>
                </div>

            </div>
            <div class="c_content">
                <div class="chartBox">
                    <div id="chart_content">

                    </div>

                </div>
            </div>
        </div>
        <div class="content_r">
            <div class="r_title">
                <div class="dims-head">
                    <h4>标题</h4>
                </div>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="输入标题名称" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="chart-type">
                <div class="dims-head">
                    <h4>图表库</h4>
                </div>
                <input id="chartType" value="type" hidden="true">
                <div class="block-body mt8">
                    <%--
                    1、支持的图表要变色
                    2、划过图标要提示
                    --%>
                    <ul>
                        <li >
                            <a >
                                <i class="chart-type-icon C200"></i>
                            </a>
                        </li><li >
                            <a class="disabled">
                                <i class="chart-type-icon C220"></i>
                            </a>
                        </li><li >
                            <a class="active">
                                <i class="chart-type-icon C220"></i>
                            </a>
                        </li><li >
                            <a class="disabled">
                                <i class="chart-type-icon C210"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C211"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C240"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C280"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C230"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C250"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C290"></i>
                            </a>
                        </li>
                        <li>
                            <a class="disabled">
                                <i class="chart-type-icon C330"></i>
                            </a>
                        </li>

                    </ul>
                </div>
            </div>
            <div class="xyAxis">
                <div class="dims-head">
                    <h4>坐标轴设置</h4>
                </div>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="X轴标签名称" aria-describedby="basic-addon1">
                </div>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="X轴标签名称" aria-describedby="basic-addon1">
                </div>
            </div>
        </div>
        <div style="clear: both;"></div>
    </div>
    <div class="bottomm"></div>
</div>
<div class="modal fade" id="mymodal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <p>正在生成中。。。。</p>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<input id="dashboardId" hidden="hidden" value="${dashboardId}">
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="js/echarts.min.js"></script>
<script src="js/createOption.js"></script>
</body>
</html>