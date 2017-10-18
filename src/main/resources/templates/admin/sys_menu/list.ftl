<!DOCTYPE html>
<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.css" rel="stylesheet">
        <link href="${ctx}/webjars/jquery-treetable/3.2.0/css/jquery.treetable.theme.default.css" rel="stylesheet">
            <title>${property("systemInfo.name")}-菜单管理</title>
    </head>
    <body>
        <nav class="breadcrumb">
            <i class="Hui-iconfont">&#xe67f;</i>
            首页 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-system"></i>
            系统管理 <span class="c-gray en">&gt;</span>
            <i class="Hui-iconfont Hui-iconfont-unordered-list"></i>
            菜单管理
            <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
                <i class="Hui-iconfont">&#xe68f;</i>
            </a>
        </nav>
        <div class="page-container">
            <div class="mt-20 panel panel-default">
                <div class="panel-header">
                    <div class="row cl">
                        <div class="col-xs-12 col-sm-12">
                            <i class="Hui-iconfont Hui-iconfont-unordered-list"></i>
                            系统菜单列表
                            <span class="r">
								<button id="btnAdd" type="button" class="btn btn-primary radius size-S">
									<i class="Hui-iconfont Hui-iconfont-add"></i>添加菜单
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
                                    <th>菜单名称</th>
                                    <th>链接</th>
                                    <th>菜单图标</th>
                                    <th>权限标识</th>
                                    <th>是否显示</th>
                                    <th>排序</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <#list menus as menu>
                                    <tr class="text-c" data-tt-id="${menu.id}" <#if menu.parent.id gt 1>data-tt-parent-id="${menu.parent.id}"</#if>>
                                        <td class="text-l">${menu.menuName!''}</td>
                                        <td class="text-c">${menu.menuUrl!''}</td>
                                        <td class="text-c">
                                            <i class="${menu.iconCls!''}"></i>
                                        </td>
                                        <td class="text-c">${menu.permission!''}</td>
                                        <td class="text-c">
                                            <#if menu.display>是<#else>否</#if>
                                        </td>
                                        <td class="text-c">${menu.sort!''}</td>
                                        <td class="text-c">
                                            <a title="添加" href="javascript:;" onclick="javascript:add(${menu.id}, '添加子菜单');" style="text-decoration:none">
                                                <i class="Hui-iconfont Hui-iconfont-add"></i>
                                            </a>
                                            <a title="编辑" href="javascript:;" onclick="javascript:edit(${menu.id});" style="text-decoration:none">
                                                <i class="Hui-iconfont Hui-iconfont-edit"></i>
                                            </a>
                                            <a title="删除" href="javascript:;" onclick="javascript:remove(${menu.childs?size}, ${menu.id});" style="text-decoration:none">
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
                    add('', '添加菜单');
                });
            });
            
            function add(pid, title) {
                var url = "${adminPath}/menu/add";

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
                var url = "${adminPath}/menu/edit?id=" + id;

                var index = layer.open({
                    type: 2,
                    title: '编辑菜单',
                    content: url
                });

                layer.full(index);
            }
            
            function remove(childsNum, id) {
                if (0 != childsNum) {
                    warningMsg('该菜单下还有子菜单，不能进行删除操作！');

                    return;
                }

                confirmMsg('确认要删除该菜单么？', function(index) {
                    layer.close(index);

                    ajaxSubmit("${adminPath}/menu/delete.json", {
                        id: id
                    }, function(returnData) {
                        if ("success" == returnData.status) {
                            successMsg('菜单已删除', function() {
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