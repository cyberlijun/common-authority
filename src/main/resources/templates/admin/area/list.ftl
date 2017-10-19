<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.css" rel="stylesheet">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.theme.default.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-区域管理</title>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-system"></i>
            系统管理 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-home"></i>
            区域管理
            <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
                <i class="Hui-iconfont">&#xe68f;</i>
            </a>
        </nav>
        <div class="page-container">
            <div class="mt-20 panel panel-default">
                <div class="panel-header">
                    <div class="row cl">
                        <div class="col-xs-12 col-sm-12">
                            <i class="Hui-iconfont Hui-iconfont-home"></i>
                            区域列表
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
                        <table id="listTable" class="table table-border table-bordered table-hover table-bg table-striped">
                            <thead>
                                <tr class="text-c">
                                    <th>区域编码</th>
                                    <th>区域名称</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list areas as area>
                                <tr class="text-c" data-tt-id="${area.id}" <#if area.parent.id gt 1>data-tt-parent-id="${area.parent.id}"</#if>>
                                    <td class="text-l">${area.code!''}</td>
                                    <td class="text-c">${area.name!''}</td>
                                    <td class="text-c">
                                        <a title="添加" href="javascript:;" onclick="javascript:add(${area.id}, '添加下级区域');" style="text-decoration:none">
                                            <i class="Hui-iconfont Hui-iconfont-add"></i>
                                        </a>
                                        <a title="编辑" href="javascript:;" onclick="javascript:edit(${area.id});" style="text-decoration:none">
                                            <i class="Hui-iconfont Hui-iconfont-edit"></i>
                                        </a>
                                        <a title="删除" href="javascript:;" onclick="javascript:remove(${area.childs?size}, ${area.id});" style="text-decoration:none">
                                            <i class="Hui-iconfont Hui-iconfont-del3"></i>
                                        </a>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <#include "../include/footer.ftl">
        <script src="${ctx}/webjars/jquery-treetable/3.2.0/jquery.treetable.js"></script>
        <script>
            $().ready(function (e) {
                var $listTable = $('#listTable');
                var $btnAdd = $('#btnAdd');

                $listTable.treetable({
                    expandable: true,
                    initialState: 'expanded'
                });

                $btnAdd.on('click', function (e) {
                    add('', '添加区域');
                });
            });

            function add(pid, title) {
                var url = "${adminPath}/area/add";

                if (pid) {
                    url += "?parentId=" + pid;
                }

                var index = layer.open({
                    type: 2,
                    title: title,
                    content: url
                });

                layer.full(index);
            }

            function edit(id) {
                var url = "${adminPath}/area/edit?id=" + id;

                var index = layer.open({
                    type: 2,
                    title: '编辑区域',
                    content: url
                });

                layer.full(index);
            }

            function remove(childsNum, id) {
                if (0 != childsNum) {
                    warningMsg('该区域下还有下级区域，不能进行删除操作！');

                    return;
                }

                confirmMsg('确认要删除该区域么？', function(index) {
                    layer.close(index);

                    ajaxSubmit("${adminPath}/area/delete.json", {
                        id: id
                    }, function(returnData) {
                        if ("success" == returnData.status) {
                            successMsg('区域已删除', function() {
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