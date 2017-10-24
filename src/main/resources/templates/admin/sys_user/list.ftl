<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/zTree/3.5.29/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-用户管理</title>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-system"></i>
            系统管理 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-user"></i>
            用户管理
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
                                    <label class="form-label col-xs-12 col-sm-3">用户名：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="username" name="username" type="text" class="input-text" placeholder="用户名">
                                    </div>
                                </div>
                            <#if orgs?size gt 0>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">所属机构：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <span class="btn-upload form-group">
                                            <input id="orgName" class="input-text upload-url" type="text" readonly placeholder="所属机构">
                                            <input id="orgId" name="org.id" type="hidden">
                                            <a id="btnChangeOrg" href="javascript:void(0);" class="btn btn-primary">
                                                <i class="Hui-iconfont">&#xe665;</i>
                                            </a>
                                        </span>
                                    </div>
                                </div>
                            </#if>
                            </div>
                            <div class="row cl">
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">邮箱：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <input id="email" name="email" type="text" class="input-text" placeholder="邮箱">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-6">
                                    <label class="form-label col-xs-12 col-sm-3">状态：</label>
                                    <div class="formControls col-xs-12 col-sm-9">
                                        <div class="skin-minimal">
                                            <div class="radio-box">
                                                <input id="status_normal" type="radio" name="status" value="NORMAL">
                                                <label for="status_normal">正常</label>
                                            </div>
                                            <div class="radio-box">
                                                <input id="status_disabled" type="radio" name="status" value="DISABLED">
                                                <label for="status_disabled">停用</label>
                                            </div>
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
                            <i class="Hui-iconfont Hui-iconfont-user"></i>
                            用户列表
                            <span class="r">
                                <button id="btnAdd" type="button" class="btn btn-primary radius size-S">
                                    <i class="Hui-iconfont Hui-iconfont-add"></i>添加
                                </button>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row cl">
                        <table id="listTable" class="table table-border table-bordered table-hover table-bg table-striped" width="100%">
                            <thead>
                            <tr class="text-c">
                                <th>所属机构</th>
                                <th>角色</th>
                                <th>登录用户名</th>
                                <th>邮箱</th>
                                <th>状态</th>
                                <th>操作</th>
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
        <script src="/webjars/zTree/3.5.29/js/jquery.ztree.all.min.js"></script>
        <script>
            $().ready(function (e) {
                var $queryForm = $('#queryForm');
                var $btnSearch = $('#btnSearch');
                var $btnReset = $('#btnReset');
                var $btnAdd = $('#btnAdd');
                var $listTable = $('#listTable');
                var $radio = $('.skin-minimal input');

                $('.Huifold').Huifold({
                    titCell: ".item h4",
                    mainCell: ".item .info",
                    type: 1,
                    trigger: "click",
                    speed: "normal"
                });

                $radio.iCheck({
                    radioClass: 'iradio_square-blue',
                    increaseArea: '20%'
                });

                $queryForm.validate({
                    rules: {
                        email: {
                            email: true
                        }
                    },
                    messages: {
                        email: {
                            email: '邮箱格式错误！'
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
                        url: "${adminPath}/user/list.json",
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
                        { 'data': 'username' },
                        { 'data': 'email' },
                        { 'data': 'status' },
                        { 'data': null }
                    ],
                    rowCallback: function(row, data, index) {
                        $(row).addClass("text-c");
                    },
                    columnDefs: [{
                        "targets": 0,
                        "render": function(data, type, row, meta) {
                            if (row.org && !row.superAdmin) {
                                var org = row.org;

                                return org.name + '（机构编码：' + org.code + '）';
                            }

                            return '';
                        }
                    }, {
                        "targets": 1,
                        "render": function(data, type, row, meta) {
                            if (row.role) {
                                return row.role.name;
                            }

                            return '';
                        }
                    }, {
                        "targets": 5,
                        "render": function(data, type, row, meta) {
                            var html = '';

                            if (row.superAdmin) {
                                html += '-';
                            } else {
                                html += '<a style="text-decoration:none" href="javascript:void(0);" title="编辑" onclick="javascript:edit(' + row.id + ');"><i class="Hui-iconfont Hui-iconfont-edit"></i></a>';
                                html += '<a class="ml-5" style="text-decoration:none" href="javascript:void(0);" title="删除" onclick="javascript:del(' + row.id + ');"><i class="Hui-iconfont Hui-iconfont-del3"></i></a>';
                            }

                            return html;
                        }
                    }]
                });

                $btnSearch.on('click', function (e) {
                    if ($queryForm.valid()) {
                        var table = $listTable.DataTable();

                        table.ajax.reload();
                    }
                });

                $btnAdd.on('click', function (e) {
                    var url = "${adminPath}/user/add";

                    var index = layer.open({
                        type: 2,
                        title: "添加用户",
                        content: url
                    });

                    layer.full(index);
                });

                <#if orgs?size gt 0>
                var $orgName = $('#orgName');
                var $orgId = $('#orgId');
                var $orgTreeContainer = $('#orgTreeContainer');
                var $orgTree = $('#orgTree');
                var $btnChangeOrg = $('#btnChangeOrg');

                var layerIndex = false;

                var setting = {
                    check: {
                        enable: true,
                        chkStyle: "radio",
                        radioType: "all"
                    },
                    view: {
                        dblClickExpand: false,
                        selectedMulti: false,
                        showLine: false,
                        showIcon: false,
                        nameIsHTML: true
                    },
                    data: {
                        simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: 1
                        }
                    },
                    callback: {
                        onCheck: function(event, treeId, treeNode) {
                            $orgName.prop("value", treeNode.name.replace(/<[^>]+>/g, ''));
                            $orgId.prop("value", treeNode.id);

                            if (layerIndex) {
                                layer.close(layerIndex);
                            }
                        }
                    }
                };

                var treeNodes = [];

                <#list orgs as org>
                    treeNodes.push({
                        id: ${org.id},
                        pId: ${org.parent.id},
                        name: '<i class="Hui-iconfont Hui-iconfont-gongsi">${org.name}</i>'
                    });
                </#list>

                var tree = $.fn.zTree.init($orgTree, setting, treeNodes);

                tree.expandAll(true);

                $btnChangeOrg.on('click', function (e) {
                    layerIndex = layer.open({
                        type: 1,
                        area: ['300px', '450px'],
                        fix: true,
                        maxmin: false,
                        move: false,
                        closeBtn: 1,
                        shade: 0.4,
                        title: '用户所属机构',
                        content: $orgTreeContainer
                    });
                });
                </#if>

                $btnReset.on('click', function (e) {
                    $radio.iCheck('uncheck');

                    $queryForm.resetForm();

                    <#if orgs?size gt 0>
                        if ($orgId.prop('value')) {
                            var node = tree.getNodeByParam('id', $orgId.prop('value'));

                            if (node) {
                                tree.checkNode(node, false);
                            }

                            $orgId.prop('value', '');
                        }
                    </#if>
                });
            });

            function edit(id) {
                var url = "${adminPath}/user/edit?id=" + id;

                var index = layer.open({
                    type: 2,
                    title: '编辑用户',
                    content: url
                });

                layer.full(index);
            }

            function del(id) {
                confirmMsg('确认要删除该用户么？', function(index) {
                    ajaxSubmit("${adminPath}/user/delete.json", {
                        id: id
                    }, function(returnData) {
                        layer.close(index);

                        if ("success" == returnData.status) {
                            successMsg('用户已删除', function() {
                                location.replace(location.href);
                            });
                        } else {
                            errorMsg(returnData.message, false);
                        }
                    });
                }, null);
            }
        </script>
    </body>
</html>