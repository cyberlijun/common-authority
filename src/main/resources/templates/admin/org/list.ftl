<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.css" rel="stylesheet">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.theme.default.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-机构管理</title>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-system"></i>
            系统管理 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-gongsi"></i>
            机构管理
            <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
                <i class="Hui-iconfont">&#xe68f;</i>
            </a>
        </nav>
        <div class="page-container">
            <div class="mt-20 panel panel-default">
                <div class="panel-header">
                    <div class="row cl">
                        <div class="col-xs-12 col-sm-12">
                            <i class="Hui-iconfont Hui-iconfont-gongsi"></i>
                            机构列表
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
                                <th>所属区域</th>
                                <th>机构编码</th>
                                <th>机构名称</th>
                                <th>联系地址</th>
                                <th>邮编</th>
                                <th>负责人</th>
                                <th>联系电话</th>
                                <th>传真</th>
                                <th>邮箱</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list orgs as org>
                                <tr class="text-c" data-tt-id="${org.id}" <#if org.parent.id gt 1>data-tt-parent-id="${org.parent.id}"</#if>>
                                    <td class="text-c">
                                        <#if org.area??>${org.area.name!''}</#if>
                                    </td>
                                    <td class="text-c">${org.code!''}</td>
                                    <td class="text-c">${org.name!''}</td>
                                    <td class="text-c">${org.address!''}</td>
                                    <td class="text-c">${org.zipCode!''}</td>
                                    <td class="text-c">${org.master!''}</td>
                                    <td class="text-c">${org.phone!''}</td>
                                    <td class="text-c">${org.fax!''}</td>
                                    <td class="text-c">${org.email!''}</td>
                                    <td class="text-c">
                                        <a title="添加" href="javascript:;" onclick="javascript:add(${org.id}, '添加下级机构');" style="text-decoration:none">
                                            <i class="Hui-iconfont Hui-iconfont-add"></i>
                                        </a>
                                        <a title="编辑" href="javascript:;" onclick="javascript:edit(${org.id});" style="text-decoration:none">
                                            <i class="Hui-iconfont Hui-iconfont-edit"></i>
                                        </a>
                                        <a title="删除" href="javascript:;" onclick="javascript:remove(${org.childs?size}, ${org.id});" style="text-decoration:none">
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
                    add('', '添加机构');
                });
            });

            function add(pid, title) {
                var url = "${adminPath}/org/add";

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
                var url = "${adminPath}/org/edit?id=" + id;

                var index = layer.open({
                    type: 2,
                    title: '编辑机构',
                    content: url
                });

                layer.full(index);
            }

            function remove(childsNum, id) {
                if (0 != childsNum) {
                    warningMsg('该机构下还有下级机构，不能进行删除操作！');

                    return;
                }

                confirmMsg('确认要删除该机构么？', function(index) {
                    layer.close(index);

                    ajaxSubmit("${adminPath}/org/delete.json", {
                        id: id
                    }, function(returnData) {
                        if ("success" == returnData.status) {
                            successMsg('机构已删除', function() {
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