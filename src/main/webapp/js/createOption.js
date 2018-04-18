/**
 * Created by Administrator on 2018/4/14.
 */
$(function () {

    var option = "";
    var jsonArrayDim = new Array();
    var jsonArrayMea = new Array();
    $("#r1div,#r2div").attr({ondrop: "drop(event)", ondragover: "allowDrop(event)"});
    $("#dims").on('click', 'li', function () {
        $(this).attr({draggable: "true", ondragstart: "drag(event)"});
    });
    $('#r1div,#r2div').on('click', '.dropdown-menu li a', function () {
        $(this).parent().parent().parent().find('button span[class="dropdowntext"]').html($(this).text());
    });
    $('#r1div,#r2div').on('click', '.remove', function (event) {
        event.cancelBubble = true;
        event.stopPropagation();
        $(this).parent().remove();

    });
    /*
     * table选择，
     * agix 获取字段
     * 更新维度字段
     * */
    $('#tableSelect').on('click', '.dropdown-menu li a', function () {
        $('#tableName').html($(this).text());
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
                        if (dataTypetmp == 'varchar' || dataTypetmp == 'timestamp') {
                            iconClass = "bdp-icon ico-type-2";
                        }
                        $('#dims').append('<li id="' + dims[i].columnName + '" class="list-group-item"> <i class="' + iconClass + '"></i> <span>' + dims[i].columnName + '</span> </li>');
                    }
                }
            }
        });


    });
    $(".chart-type a[class != 'disabled']").bind('click', function () {
        $(".active").removeAttr("class");
;        $(this).attr("class","active");
        $('#chartType').val(($(this).attr("class").substr(16)));
    });

    $("#createOption").bind('click', function () {
        /*
         * 需要的数据包括：1、工作表2、维度组合3、图表类型。。。。
         * */
        var tableName = $('#tableName').text();
        var btnArray1 = $('#r1div').find('.btnValue');
        var btnArray2 = $('#r2div').find('.btnValue');
        var chartType = $('#chartType').val();
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
            var dim = {"name": name, "dataType": 2, "method": "count"};
            jsonArrayMea.push(dim);
        });
        var jsonData = {
            "seriesType": chartType,
            "tableName": tableName,
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
                console.log(option);
                if (chartType == "C200") {
                    option = JSON.parse(option);
                    var tableData = "";
                    for(var i=0;i< option.length;i++){
                        tableData +="<tr>"
                        for(var j =0;j< option[i].length;j++){
                            tableData +="<td>";
                            tableData +=option[i][j];
                            tableData +="</td>";
                        }
                        tableData +="</tr>";
                    }
                    console.log(tableData);
                    $('#chart_content').append("<table id='tableTmp' class='table  table-condensed'>"+tableData+"</table>");

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
        var jsonData = {
            "dashboardId": $("#dashboardId").val(),
            "tableName": $('#tableName').html(),
            "seriesType": $('#chartType').val(),
            "option1": JSON.stringify(option),
            "dimensions": jsonArrayDim,
            "measures": jsonArrayMea
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
});
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
    var typeClass = $('#' + data + '> i').attr("class");
    var btnValue = $("#" + data + "> span").text();
    var typeflag = 'bdp-icon ico-type-2'
    if ($(ev.target).attr('id') == 'r2div') {
        typeflag = 'bdp-icon ico-type-1';
    }
    if (typeflag != 'bdp-icon ico-type-1' || $.trim(typeClass) == typeflag) {
        /*避免重复*/
        var btnArray = $("#" + $(ev.target).attr('id')).find('.btnValue');
        var flag = true;
        btnArray.each(function (i, o) {
            if ($(o).text() == btnValue) {
                flag = false;
            }
        });
        if (flag) {
            if($(ev.target).attr('id') == 'r1div'){
                $(ev.target).append('<div class="dropdown"><button class="btn btn-default dropdown-toggle" type="button"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="btnValue">' + btnValue + '</span><span class="glyphicon glyphicon-remove remove" aria-hidden="true"></span></button></div>')
            }else{
                $(ev.target).append('<div class="dropdown"><button class="btn btn-default dropdown-toggle" type="button"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="caret"></span>  <span class="btnValue">' + btnValue + '</span> （<span class="dropdowntext">count</span>）<span class="glyphicon glyphicon-remove remove" aria-hidden="true"></span></button><ul class="dropdown-menu" aria-labelledby="dropdownMenu2"><li><a >sum</a></li><li><a >max</a></li><li><a>count</a></li></ul></div>')
            }


        } else {
            console.log("位置重复");
        }

    } else {
        console.log("请添加到对应位置");
    }

}