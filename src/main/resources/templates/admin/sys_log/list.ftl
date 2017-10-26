<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <title>${property("systemInfo.name")}-系统日志</title>
        <style>
            td.details-control {
                background: url('${ctx}/webjars/common-authority/1.0/images/details_open.png') no-repeat center center;
                cursor: pointer;
            }

            tr.shown td.details-control {
                background: url('${ctx}/webjars/common-authority/1.0/images/details_close.png') no-repeat center center;
            }
        </style>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-tongji-bing"></i>
            查询统计 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-feedback2"></i>
            系统日志
            <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
                <i class="Hui-iconfont">&#xe68f;</i>
            </a>
        </nav>
        <div class="page-container">
            <ul class="Huifold">
                <li class="item">
                    <h4>
                        <i class="Hui-iconfont Hui-iconfont-search1"></i>查询条件
                        <b>
                            <i class="Hui-iconfont Hui-iconfont-arrow2-bottom"></i>
                        </b>
                    </h4>
                    <div class="info">
                        <form id="queryForm" class="form form-horizontal">
                            <div class="row cl">
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">操作用户：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="username" name="username" type="text" class="input-text" placeholder="操作用户">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">请求URI：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="requestUri" name="requestUri" type="text" class="input-text" placeholder="请求URI">
                                    </div>
                                </div>
                            </div>
                            <div class="row cl">
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">提交方式：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
										<span class="select-box radius">
											<select class="select" name="method">
												<option value="" selected>请选择</option>
												<option value="GET">GET</option>
												<option value="POST">POST</option>
											</select>
										</span>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">日志类型：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
										<span class="select-box radius">
											<select class="select" name="type">
												<option value="" selected>请选择</option>
												<option value="ACCESS_LOG">访问日志</option>
												<option value="EXCEPTION_LOG">异常日志</option>
											</select>
										</span>
                                    </div>
                                </div>
                            </div>
                            <div class="row cl">
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">开始日期：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="startDate" name="startDate" type="text" class="input-text Wdate" placeholder="开始日期"
                                               onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" readonly>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">结束日期：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="endDate" name="endDate" type="text" class="input-text Wdate" placeholder="结束日期"
                                               onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="row cl">
                                <div class="col-xs-12 col-sm-12">
                                    <div class="text-c mt-20 mb-20">
                                        <button id="btnSearch" type="button" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i>查询</button>
                                        &nbsp;
                                        <button id="btnReset" type="button" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe66c;</i>重置</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </li>
            </ul>
            <div class="mt-20 panel panel-default">
                <div class="panel-header">
                    <div class="row cl">
                        <div class="col-xs-12 col-sm-12">
                            <i class="Hui-iconfont Hui-iconfont-feedback2"></i>
                            系统日志列表
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row cl">
                        <table id="listTable" class="table table-border table-bordered table-hover table-bg table-striped" width="100%">
                            <thead>
                                <tr class="text-c">
                                    <th>&nbsp;</th>
                                    <th>日志类型</th>
                                    <th>操作用户</th>
                                    <th>请求URI</th>
                                    <th>提交方式</th>
                                    <th>操作用户IP</th>
                                    <th>创建时间</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <#include "../include/footer.ftl">
        <script>
            $().ready(function (e) {
                var $queryForm = $('#queryForm');
                var $btnSearch = $('#btnSearch');
                var $btnReset = $('#btnReset');
                var $listTable = $('#listTable');

                $('.Huifold').Huifold({
                    titCell: ".item h4",
                    mainCell: ".item .info",
                    type: 1,
                    trigger: "click",
                    speed: "normal"
                });

                $queryForm.validate({
                    rules: {
                        ip: {
                            ip: true
                        }
                    },
                    messages: {
                        ip: {
                            ip: '操作用户IP地址格式错误！'
                        }
                    },
                    onkeyup: false,
                    focusCleanup: true
                })

                $listTable.dataTable({
                    ordering: false,
                    processing: true,
                    searching: false,
                    serverSide: true,
                    lengthMenu: [10, 30, 50, 100],
                    pageLength: 30,
                    pagingType: "full_numbers",
                    language: {
                        loadingRecords: "数据加载中，请稍后...",
                        processing: "数据加载中，请稍后...",
                        infoEmpty: "未找到符合条件的记录！",
                        emptyTable: "未找到符合条件的记录！",
                        paginate: {
                            first: "首页",
                            last: "尾页"
                        }
                    },
                    ajax: {
                        url: "${adminPath}/log/sys/list.json",
                        cache: false,
                        type: "POST",
                        data: function(d) {
                            var o = $queryForm.serializeObject();

                            for (var prop in o) {
                                d[prop] = o[prop];
                            }
                        },
                        dataType: "json",
                        error: handlerAjaxError
                    },
                    columns: [
                        { "data": null, "defaultContent": "" },
                        { "data": "type" },
                        { "data": "user.username" },
                        { "data": "requestUri" },
                        { "data": "method" },
                        { "data": "remoteAddr" },
                        { "data": "createDate" }
                    ],
                    rowCallback: function(row, data, index) {
                        $(row).addClass("text-c");
                    },
                    columnDefs: [{
                        "targets": 0,
                        "className": "details-control"
                    }, {
                        "targets": 2,
                        "render": function(data, type, row, meta) {
                            if (row.user) {
                                return row.user.username;
                            }

                            return "";
                        }
                    }]
                });

                $listTable.find("tbody").on("click", "td.details-control", function(e) {
                    var table = $listTable.DataTable();

                    var tr = $(this).closest('tr');

                    var row = table.row(tr);

                    if (row.child.isShown()) {
                        row.child.hide();

                        tr.removeClass('shown');
                    } else {
                        row.child(format(row.data())).show();

                        tr.addClass('shown');
                    }
                });

                $btnSearch.on('click', function (e) {
                    if ($queryForm.valid()) {
                        var table = $listTable.DataTable();

                        table.ajax.reload();
                    }
                });

                $btnReset.on('click', function (e) {
                    $queryForm.resetForm();
                });
            });

            function format(d) {
                var html = '';

                html += '用户代理：' + d.userAgent + '<br>';
                html += '提交参数：' + d.params;

                if (-1 != d.type.indexOf("异常")) {
                    html += '<br>异常信息：' + d.exception;
                }

                return html;
            }
        </script>
    </body>
</html>