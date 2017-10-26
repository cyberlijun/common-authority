<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <title>${property("systemInfo.name")}-系统登录日志</title>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-tongji-bing"></i>
            查询统计 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-log"></i>
            系统登录日志
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
                                    <label class="form-label col-xs-12 col-sm-3">登录用户名：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="username" name="username" type="text" class="input-text" placeholder="登录用户名">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">最后登录IP地址：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="ip" name="ip" type="text" class="input-text" placeholder="最后登录IP地址">
                                    </div>
                                </div>
                            </div>
                            <div class="row cl">
                                <div class="row cl">
                                    <div class="col-xs-12 col-sm-6">
                                        <label class="form-label col-xs-12 col-sm-3">登录起始时间：</label>
                                        <div class="formControls col-xs-12 col-sm-9">
                                            <input id="startDate" name="startDate" type="text" class="input-text Wdate" placeholder="登录起始时间"
                                                   onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')||\'%y-%M-%d\'}'})" readonly>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-6">
                                        <label class="form-label col-xs-12 col-sm-3">登录结束时间：</label>
                                        <div class="formControls col-xs-12 col-sm-9">
                                            <input id="endDate" name="endDate" type="text" class="input-text Wdate" placeholder="登录结束时间"
                                                   onfocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',maxDate:'%y-%M-%d'})" readonly>
                                        </div>
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
                            <i class="Hui-iconfont Hui-iconfont-log"></i>
                            系统登录日志列表
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row cl">
                        <table id="listTable" class="table table-border table-bordered table-hover table-bg table-striped" width="100%">
                            <thead>
                                <tr class="text-c">
                                    <th>登录用户名</th>
                                    <th>所在机构</th>
                                    <th>角色</th>
                                    <th>登录次数</th>
                                    <th>最后登录IP</th>
                                    <th>最后登录时间</th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id="orgTreeContainer" class="page-container" style="display: none;">
            <div class="row cl">
                <div class="col-xs-12 col-sm-12">
                    <ul id="orgTree" class="ztree"></ul>
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
                            ip: '最后登录IP地址格式错误！'
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
                        url: "${adminPath}/log/login/list.json",
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
                        { 'data': null },
                        { 'data': null },
                        { 'data': null },
                        { 'data': 'loginCount' },
                        { 'data': 'ip' },
                        { 'data': 'lastLoginTime' }
                    ],
                    rowCallback: function(row, data, index) {
                        $(row).addClass("text-c");
                    },
                    columnDefs: [{
                        "targets": 0,
                        "render": function(data, type, row, meta) {
                            if (row.user) {
                                return row.user.username;
                            }

                            return '';
                        }
                    }, {
                        "targets": 1,
                        "render": function(data, type, row, meta) {
                            if (row.user && row.user.org) {
                                return row.user.org.name + '（机构编码：' + row.user.org.code + '）';
                            }

                            return '';
                        }
                    }, {
                        "targets": 2,
                        "render": function(data, type, row, meta) {
                            if (row.user && row.user.role) {
                                return row.user.role.name;
                            }

                            return '';
                        }
                    }]
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
        </script>
    </body>
</html>