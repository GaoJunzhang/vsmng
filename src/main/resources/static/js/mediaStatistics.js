var table;
$(document).ready(function () {
    $('.selectData').datepicker({
        autoclose: true, //自动关闭
        language: 'zh-CN',
        beforeShowDay: $.noop,    //在显示日期之前调用的函数
        calendarWeeks: false,     //是否显示今年是第几周
        clearBtn: true,          //显示清除按钮
        daysOfWeekDisabled: [],   //星期几不可选
        endDate: Infinity,        //日历结束日期
        startDate: -Infinity,        //日历结束日期
        forceParse: true,         //是否强制转换不符合格式的字符串
        format: 'yyyy-mm-dd',     //日期格式
        keyboardNavigation: true, //是否显示箭头导航
        language: 'zh-CN',           //语言
        minViewMode: 0,
        orientation: "auto",      //方向
        rtl: false,
        startView: 0,             //开始显示
        todayHighlight: true,    //今天高亮
        weekStart: 0              //星期几是开始
    });
    $.ajax({
        type: "GET",
        url: "/userMedias/yearMediaData",
        data: {year: 2018},
        dataType: "json",
        success: function (res) {
            var d1 = [];
            if (res.statisList.length > 0) {
                d1.push([1, parseInt(res.statisList[0].Jans)]);
                d1.push([2, parseInt(res.statisList[0].Febs)]);
                d1.push([3, parseInt(res.statisList[0].Mars)]);
                d1.push([4, parseInt(res.statisList[0].Aprs)]);
                d1.push([5, parseInt(res.statisList[0].Mays)]);
                d1.push([6, parseInt(res.statisList[0].Juns)]);
                d1.push([7, parseInt(res.statisList[0].Juls)]);
                d1.push([8, parseInt(res.statisList[0].Augs)]);
                d1.push([9, parseInt(res.statisList[0].Septs)]);
                d1.push([10, parseInt(res.statisList[0].Octs)]);
                d1.push([11, parseInt(res.statisList[0].Novs)]);
                d1.push([12, parseInt(res.statisList[0].Dects)]);
            }

            var data = new Array();
            data.push({
                data: d1,
                bars: {
                    show: true,
                    barWidth: 0.4,
                    order: 1,
                }
            });


            //Display graph
            var bar = $.plot($(".bars"), data, {
                legend: true
            });
        }
    });
    table = $('#datatable').DataTable({
        "dom": '<"top"i>rt<"bottom"flp><"clear">',
        "searching": false,
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "serverSide": true,//开启服务器模式，使用服务器端处理配置datatable
        "processing": true,//开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
        //"ajax": '${ss}/user/userList.do',
        ajax: function (data, callback, settings) {
            //封装请求参数
            var param = getQueryCondition(data);
            $.ajax({
                type: "GET",
                url: '/userMedias/userMediaStatistics',
                cache: false,  //禁用缓存
                data: param,    //传入已封装的参数
                dataType: "json",
                success: function (result) {
                    //封装返回数据  如果参数相同，可以直接返回result ，此处作为学习，先这么写了。
                    var returnData = {};
                    returnData.draw = result.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.recordsTotal;//总记录数
                    returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.data;
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("查询失败");
                }
            });
        },
        "columns": [

            // {"data": "id"},
            {"data": "username"},
            {"data": "realyname"},
            {"data": "usedPlayCount"},
            {"data": "vaildPlayCount"},
            {"data": "sumcount"},
            {"data": "playprogress"}
        ],
        columnDefs: [
            {"orderable": false, "targets": 1},
            {
                "orderable": false,
                "render": function (data, type, row) {
                    if (data > 0) {
                        return "<a href='/userMediaInfo?id="+row.id+"&username="+row.username+"'><span class='label label-important'>" + data + "</span></a>";
                    } else {
                        return "<span class='badge'>" + data + "</span>";
                    }
                },
                "targets": 2
            },{
                "orderable": false,
                "render": function (data, type, row) {
                    if (data > 0) {

                        return "<span class='badge badge-success'>" + data + "</span>";
                    } else {
                        return "<span class='badge'>" + data + "</span>";
                    }
                },
                "targets": 3
            }, {
                "orderable": false,
                "render": function (data, type, row) {
                    if (row.sumcount>0){
                        var vp = (row.vaildPlayCount / row.sumcount).toFixed(2)*100;
                        if (vp < 30) {
                            return "<div class='progress progress-striped progress-danger active'><div class='bar' style='width: " + vp + "%;'></div></div>";
                        }
                        if (vp >= 30 && vp < 50) {
                            return "<div class='progress progress-striped progress-warning active'><div class='bar' style='width: " + vp + "%;'></div></div>";
                        }
                        if (vp >= 60) {
                            return "<div class='progress progress-striped progress-success active'><div class='bar' style='width: " + vp + "%;'></div></div>";
                        }
                    }else {
                        return data;
                    }

                },
                "targets": 5
            },
        ],

    });


});
function search() {
    table.ajax.reload();
}

//封装查询参数
function getQueryCondition(data) {
    var param = {};
    //组装排序参数
    param.username = $("#name-search").val();//查询条件
    param.realyname = $("#realyname-search").val();
    param.startTime = $("#startTime-search").val();//查询条件
    param.endTime = $("#endTime-search").val();//查询条件
    //组装分页参数
    param.start = data.start;
    param.length = data.length;
    param.draw = data.draw;
    return param;
}//封装查询参数
function getQueryCondition1(data) {
    var param = {};
    //组装排序参数
    param.username = $("#name-search").val();//查询条件
    param.startTime = $("#startTime-search").val();//查询条件
    param.endTime = $("#endTime-search").val();//查询条件
    //组装分页参数
    param.start = data.start;
    param.length = data.length;
    param.draw = data.draw;
    return param;
}

