@{
Layout = null;
}
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>下拉框 - Ace Admin</title>
    @* 这个控制宽度(只是控制div的) *@
    <link rel="stylesheet" href="/assets/css/bootstrap.min.css" />

    @* 以下两个对下拉框的样式有影响 *@
    <link rel="stylesheet" href="/assets/css/chosen.css" />
    <link rel="stylesheet" href="/assets/css/ace.min.css" />
    　　 <link rel="stylesheet" href="/assets/css/css.css"/>
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css" />

    <script src="/assets/js/jquery-2.1.4.min.js"></script>
    <![endif]-->
    <script src="/assets/js/chosen.jquery.min.js"></script>

    <script type="text/javascript">
        $(function ($) {
            $('.chosen-select').chosen({ allow_single_deselect: true });
            //窗口大小改变时，下拉框跟着缩放
            $(window).on('resize.chosen', function () {
                var w = $('.chosen-select').parent().width();
                $('.chosen-select').next().css({ 'width': w });
            }).trigger('resize.chosen');
        });
    </script>
</head>

<body>
<div class="modal-body">
    <div class="row">
        <div class="col-xs-12 col-sm-4">
            <!-- Html示例 -->
            <select class="chosen-select" name="Hero" data-placeholder="请选择英雄...">
                <option value=""></option>    <!-- 默认的Text里不要放东西，否则不会选择(请选择) -->
                <option value="1">刘备</option>
                <option value="2">关羽</option>
                <option value="3">张飞</option>
            </select>

            <div class="space-4"></div>

            <!-- MVC版示例 -->
            @*对于AceAdmin很多-，在MVC中要用_代替*@
            @Html.DropDownList("SelectListItem", null, null, new { @class = "chosen-select", data_placeholder="请选择英雄...", name = "Hero" })
        </div>
    </div>
</div>
</body>
</html>