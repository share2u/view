<%@ page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		.center-content{
			height: auto;
			display: block;
		}

	</style>
</head>
<body>
	<div id="wrapper">
		<div class="container" id="head">
			<div class="row">
				<div class="col-md-12">
					<header class="center-content">
						<h3>view</h3>
					</header>
				</div>
			</div>
		</div>
		<div class="container" id="content">
			<div class="row" id="caontent-title">
				<div class="col-md-1">仪表盘</div>
				<div class="col-md-1">新增仪表盘</div>
				<div class="col-md-8"></div>
				<div class="col-md-1">新增图表</div>
				<div class="col-md-1"></div>
			</div>
			<div class="row">
				<div class="col-md-2" id="content-left">
					<h3>仪表列表</h3>
				</div>
				<div class="col-md-10" id="content-right">
					<h3>仪表详情</h3>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
