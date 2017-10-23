<!DOCTYPE HTML>

<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/zTree/3.5.29/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-添加角色</title>
    </head>
    <body>
        <div class="page-container">
            <form action="${adminPath}/role/add.json" class="form form-horizontal" method="post">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>所属机构：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input id="orgName" name="orgName" class="input-text" style="cursor: pointer;" type="text" readonly placeholder="所属机构">
                        <input id="orgId" name="org.id" type="hidden">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>角色名称：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="name" name="name" placeholder="角色名称">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>授权菜单：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <ul id="menuTree" class="ztree"></ul>
                    </div>
                </div>
                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
                        <button id="btnSave" class="btn btn-secondary radius" type="button">
                            <i class="Hui-iconfont Hui-iconfont-save"></i>保存
                        </button>
                        <button id="btnCancel" class="btn btn-default radius" type="button">
                            <i class="Hui-iconfont Hui-iconfont-chexiao"></i>取消
                        </button>
                    </div>
                </div>
            </form>
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
                var $form = $('form:first');
                var $btnSave = $('#btnSave');
                var $btnCancel = $('#btnCancel');

                <#if orgs?size gt 0>
                var $orgName = $('#orgName');
                var $orgId = $('#orgId');
                var $orgTreeContainer = $('#orgTreeContainer');
                var $orgTree = $('#orgTree');

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
                            $orgName.prop("value", treeNode.name.replace(/<[^>]+>/g, ""));
                            $orgId.prop("value", treeNode.id);

                            if (layerIndex) {
                                if ($orgName.prop('value')) {
                                    if ($orgName.hasClass('error')) {
                                        $orgName.removeClass('error');

                                        $orgName.siblings('.error').remove();
                                    }
                                }

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

                $orgName.on('click', function (e) {
                    layerIndex = layer.open({
                        type: 1,
                        area: ['300px', '450px'],
                        fix: true,
                        maxmin: false,
                        move: false,
                        closeBtn: 1,
                        shade: 0.4,
                        title: '角色所属机构',
                        content: $orgTreeContainer
                    });
                });
                </#if>

                <#if menus?size gt 0>
                var $menuTree = $('#menuTree');

                var menuTreeSetting = {
                    check: {
                        enable: true,
                        nocheckInherit: true
                    },
                    view: {
                        selectedMulti: false,
                        nameIsHTML: true,
                        showIcon: false,
                        showIcon: false,
                        nameIsHTML: true
                    },
                    data: {
                        simpleData:{
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: 1
                        }
                    },
                    callback: {
                        beforeClick: function(id, node){
                            tree.checkNode(node, !node.checked, true, true);

                            return false;
                        }
                    }
                };

                var menuTreeNodes = [];

                <#list menus as menu>
                    menuTreeNodes.push({
                        id: ${menu.id},
                        pId: ${menu.parent.id},
                        name: '<i class="${menu.iconCls!''}">${menu.menuName}</i>'
                    });
                </#list>

                var menuTree = $.fn.zTree.init($menuTree, menuTreeSetting, menuTreeNodes);

                menuTree.expandAll(true)
                </#if>

                $form.validate({
                    rules: {
                        orgName: {
                            required: true
                        },
                        name: {
                            required: true,
                            remote: {
                                url: '${adminPath}/role/check_name.json',
                                type: 'post',
                                cache: false,
                                data: {
                                    name: function () { return $('#name').prop('value'); },
                                    orgId: function () { return $orgId.prop('value'); }
                                }
                            }
                        }
                    },
                    messages: {
                        orgName: {
                            required: '所属机构不能为空！'
                        },
                        name: {
                            required: '角色名称不能为空！',
                            remote: '角色名称已存在！'
                        }
                    },
                    onkeyup: false,
                    focusCleanup: true
                });

                $btnSave.on('click', function (e) {
                    if ($form.valid()) {
                        var nodes = menuTree.getCheckedNodes(true);

                        if (0 == nodes.length) {
                            warningMsg("请选择要为角色分配的菜单！");

                            return;
                        }

                        var ids = [];

                        for (var i = 0; i < nodes.length; i++) {
                            ids.push(nodes[i].id);
                        }

                        showMask();

                        $form.ajaxSubmit({
                            data: {
                                menuIds: ids
                            },
                            success: function(data) {
                                hideMask();

                                if ("success" == data.status) {
                                    successMsg('角色添加成功！', function() {
                                        parent.location.reload();

                                        layer_close();
                                    });
                                } else {
                                    errorMsg(data.message, false);
                                }
                            },
                            error: handleAjaxSubmitError
                        });
                    }
                });

                $btnCancel.on('click', function (e) {
                    layer_close();
                });
            });
        </script>
    </body>
</html>