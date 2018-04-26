/**
 * Created by Administrator on 2018/4/14.
 */
var option = "";
var jsonArrayDim = new Array();
var jsonArrayMea = new Array();
$(function () {
    $("#r1div,#r2div").attr({ondrop: "drop(event)", ondragover: "allowDrop(event)"});
    $("#dims").on('click', 'li', function () {
        $(this).attr({draggable: "true", ondragstart: "drag(event)"});
    });
    $('#r1div,#r2div').on('click', '.dropdown-menu li a', function () {
        $(this).parent().parent().parent().find('button span[class="dropdowntext"]').html($(this).text());
        recommend();
    });
    $('#r1div,#r2div').on('click', '.remove', function (event) {
        event.cancelBubble = true;
        event.stopPropagation();

        $(this).parent().parent().remove();
        recommend();

    });
    /*
     * table选择，
     * agix 获取字段
     * 更新维度字段
     * */
    $('#tableSelect').on('click', '.dropdown-menu li a', function () {
        $('#tableName').html($(this).text());
        $('#r1div,#r2div,#chart_content').html("");
        $.ajax({
            url: "schema/tables/" + $(this).text(),
            dataType: "json",
            type: "GET",
            success: function (data) {
                if (data.reasonCode == "200") {
                    var dims = data.data;
                    $('#dims li').remove();
                    for (var i = 0; i < dims.length; i++) {
                        var dataTypetmp = dims[i].dataType;
                        var iconClass = "bdp-icon ico-type-1";
                        if (dataTypetmp == 'varchar') {
                            iconClass = "bdp-icon ico-type-2";
                        }else if(dataTypetmp == 'timestamp'){
                            iconClass = "bdp-icon ico-type-3";
                        }
                        $('#dims').append('<li id="' + dims[i].columnName + '" class="list-group-item"> <i class="' + iconClass + '"></i> <span>' + dims[i].columnName + '</span> </li>');
                    }
                }
            }
        });


    });
    $(".chart-type a[class != 'disabled']").bind('click', function () {
        $('#chart_content').html("");
        /*出现重新放置的图不显示*/
        $('#chart_content').removeAttr("style");
        $('#chart_content').removeAttr("_echarts_instance_");
        $(".active").removeAttr("class");
        $(this).attr("class", "active");
        var t = $(this).children("i").attr("class").substr(16);
        if (t == 'C200' || t == 'C230' || t == "C330" || t == 'C250' || t == "SOM") {
            $(".xyAxis").hide();
        } else {
            $(".xyAxis").show();
        }
        if (t == "SOM") {
            $(".Kmeans").show();
        } else {
            $(".Kmeans").hide();
        }
        $('#chartType').val(t);
    });


    $("#createOption").bind('click', function () {
        /*
         * 需要的数据包括：1、工作表2、维度组合3、图表类型。。。。
         * */
        var tableName = $('#tableName').text();
        var chartType = $('#chartType').val();
        var titleText = $("#titleText").val() || "标题";
        var xAxisName = $('#xAxisName').val() || "x轴";
        var yAxisName = $('#yAxisName').val() || "y轴";
        var jsonData = {
            "seriesType": chartType,
            "tableName": tableName + "@@" + titleText + "@@" + xAxisName + "@@" + yAxisName,
            "dimensions": jsonArrayDim,
            "measures": jsonArrayMea
        };
        $("#mymodal").modal({backdrop: 'static', keyboard: true, show: 'true'});
        console.log(jsonData);
        $.ajax({
            url: 'chart/option/create',
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            success: function (msg) {
                option = msg.data;
                if (chartType != 'C200') {
                    $("#titleText").val(option.title.text);
                }
                if (chartType == 'C200' || chartType == 'C230' || chartType == "C330" || chartType == 'C250') {

                } else {
                    $("#xAxisName").val(option.xAxis[0].name);
                    $("#yAxisName").val(option.yAxis[0].name);
                }
                if (chartType == 'SOM') {
                    var titletmp = option.title.text.split("@@")
                    option.title.text = titletmp[0];
                    console.log(titletmp);
                    $('#pathName').val(titletmp[1]);
                    $("#titleText").val(option.title.text);

                }
                if (chartType == "C200") {
                    option = JSON.parse(option);
                    var tableData = "";
                    for (var i = 0; i < option.length; i++) {
                        tableData += "<tr>"
                        for (var j = 0; j < option[i].length; j++) {
                            tableData += "<td>";
                            tableData += option[i][j];
                            tableData += "</td>";
                        }
                        tableData += "</tr>";
                    }
                    console.log(tableData);
                    $('#chart_content').append("<table id='tableTmp' class='table  table-condensed'>" + tableData + "</table>");

                } else {
                    var myChart = echarts.init(document.getElementById("chart_content"));
                    myChart.setOption(option);
                }

                /*隐藏模态框*/
                $("#mymodal").modal('hide');

            }
        })


    });
    $('#saveOption').bind('click', function () {
        /*
         * 保存option,,1option 2.维度组合
         * dashboard_id
         * table_name
         * content---option
         * chart_type
         *
         *option_id
         * dim_name
         * dim_oper  0---sum
         *
         * */
        var chartType = $('#chartType').val();
        if(chartType =="C200"){
            alert("表格不支持保存");
            return false;
        }
        var jsonData = {
            "dashboardId": $("#dashboardId").val(),
            "tableName": $('#tableName').html(),
            "seriesType": $('#chartType').val(),
            "option1": JSON.stringify(option),
            "dimensions": jsonArrayDim,
            "measures": jsonArrayMea,
            "pathName":$('#pathName').val()
        };
        $.ajax({
            url: 'chart/option/save',
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            success: function (msg) {
                console.log(msg);
                alert("保存成功");
                window.close();

            }
        });
    });
    $('#beginK').bind('click', function () {
        var k = $('#k').val() || 3;
        var pathName = $('#pathName').val();
        $.ajax({
            url: 'chart/option/kmeanScatter',
            type: 'POST',
            data: {"k": k, "pathName": pathName,"titleText":$("#titleText").val() || "标题"},
            dataType: 'json',
            //生成一个平行坐标图，和聚类图
            success: function (msg) {
                var options = msg.data;
                $('#chart_content').html("");
                $("#chart_content").append("<div id='SOMScatter' style='width: 100%;height: 50%'></div>");
                var myChart1 = echarts.init(document.getElementById("SOMScatter"));
                myChart1.setOption(options[0]);
                option = options[1];
                console.log(option);
                $("#chart_content").append("<div id='SOMFunner' style='width: 100%;height: 50%'></div>");
                var myChart2 = echarts.init(document.getElementById("SOMFunner"));
                myChart2.setOption(options[1]);
            }
        });
    });
});
function recommend() {
    $('#chart_content').html("");
    /*出现重新放置的图不显示*/
    $('#chart_content').removeAttr("style");
    $('#chart_content').removeAttr("_echarts_instance_");
    var btnArray1 = $('#r1div').find('.btnValue');
    var btnArray2 = $('#r2div').find('.btnValue');
    jsonArrayDim = new Array();
    btnArray1.each(function (i, o) {
        var name = $(o).text();
        var dim = {"name": name, "dataType": 1};
        /*放入的时候应该是数字放到哪里都可以*/
        jsonArrayDim.push(dim);
    });
    jsonArrayMea = new Array();
    btnArray2.each(function (i, o) {
        var name = $(o).text();
        var dim = {"name": name, "dataType": 2, "method": $(this).parent().find('.dropdowntext').text()};
        jsonArrayMea.push(dim);
    });
    console.log(btnArray2);
    console.log(jsonArrayMea);
    $(".chart-type-icon").parent().attr("class", "disabled");
    if(jsonArrayDim.length>0 && jsonArrayMea.length>0){
        var jsonData = {
            "tableName":  $('#tableName').text(),
            "dimensions": jsonArrayDim,
            "measures": jsonArrayMea
        };

        $.ajax({
            url:"chart/recommend",
            type: 'POST',
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(jsonData),
            dataType: 'json',
            success:function(msg){
                console.log(msg.data);
                if(msg.reasonCode =='200'){
                    $(msg.data.join(",")).parent().removeAttr("class");
                }
            }
        });
    }

};
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    var data = ev.target.id;
    ev.dataTransfer.setData("Text", data);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("Text");
    //拖拽的div
    var typeClass = $.trim($('#' + data + '> i').attr("class"));
    var btnValue = $("#" + data + "> span").text();
    //目标的div
    var targetDiv = $(ev.target).attr('id');
    var placeFlag=false;
    if (targetDiv == 'r2div' && typeClass == 'bdp-icon ico-type-1' ) {
        placeFlag =true;
    }
    if( targetDiv == 'r1div'){
        placeFlag=true;
    }

    if (placeFlag) {
        /*避免重复*/
        var btnArray = $("#" + $(ev.target).attr('id')).find('.btnValue');
        var flag = true;
        btnArray.each(function (i, o) {
            if ($(o).text() == btnValue) {
                flag = false;
            }
        });
        if (flag) {
            if (targetDiv == 'r1div') {
                $(ev.target).append('<div class="dropdown"><button class="btn btn-default dropdown-toggle" type="button"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="btnValue">' + btnValue + '</span><span class="glyphicon glyphicon-remove remove" aria-hidden="true"></span></button></div>')
            } else {
                $(ev.target).append('<div class="dropdown"><button class="btn btn-default dropdown-toggle" type="button"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="caret"></span>  <span class="btnValue">' + btnValue + '</span> （<span class="dropdowntext">count</span>）<span class="glyphicon glyphicon-remove remove" aria-hidden="true"></span></button><ul class="dropdown-menu" aria-labelledby="dropdownMenu2"><li><a >sum</a></li><li><a >max</a></li><li><a>count</a></li></ul></div>')
            }

        } else {
            console.log("位置重复");
        }
        recommend();
    } else {
        console.log("请添加到对应位置");
    }

}

