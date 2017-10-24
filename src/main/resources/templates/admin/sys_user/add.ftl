<!DOCTYPE HTML>

<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/zTree/3.5.29/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-添加用户</title>
    </head>
    <body>
        <div class="page-container">
            <form action="${adminPath}/user/add.json" class="form form-horizontal" method="post">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>所属机构：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input id="orgName" name="orgName" class="input-text" style="cursor: pointer;" type="text" readonly placeholder="所属机构">
                        <input id="orgId" name="org.id" type="hidden">
                    </div>
                </div>
                <div id="roleDiv" class="row cl" style="display: none;">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>角色：
                    </label>
                    <div id="roleItemDiv" class="formControls col-xs-8 col-sm-9">

                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>登录用户名：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input id="username" name="username" class="input-text" type="text" placeholder="登录用户名">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>登录密码：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input id="password" name="password" class="input-text" type="password" placeholder="登录密码">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>邮箱：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input id="email" name="email" class="input-text" type="text" placeholder="邮箱">
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
                var $roleDiv = $('#roleDiv');
                var $roleItemDiv = $('#roleItemDiv');

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

                                showMask();

                                $.post('${adminPath}/role/load_roles.json', {
                                    orgId: treeNode.id
                                }, function (returnData) {
                                    hideMask();

                                    if (returnData) {
                                        if ('success' === returnData.status) {
                                            var html = '<div class="skin-minimal">';

                                            var roles = returnData.data;

                                            if (roles.length > 0) {
                                                roles.forEach(function (role) {
                                                    html += '   <div class="radio-box">';
                                                    html += '       <input id="role_' + role.id + '" type="radio" name="role.id" value="' + role.id + '">';
                                                    html += '       <label for="role_' + role.id + '">' + role.name + '</label>';
                                                    html += '   </div>'
                                                });

                                                html += '</div>'

                                                $roleItemDiv.empty()
                                                            .html(html)
                                                            .find('.skin-minimal input')
                                                            .iCheck({
                                                                radioClass: 'iradio_square-blue',
                                                                increaseArea: '20%'
                                                            });

                                                $roleDiv.show();
                                            } else {
                                                $roleItemDiv.empty();

                                                $roleDiv.hide();
                                            }
                                        } else {
                                            errorMsg('加载角色列表时发生错误！');
                                        }
                                    } else {
                                        errorMsg('加载角色列表时发生错误！');
                                    }
                                });
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
                        title: '用户所属机构',
                        content: $orgTreeContainer
                    });
                });
                </#if>

                $form.validate({
                    rules: {
                        orgName: {
                            required: true
                        },
                        username: {
                            required: true,
                            remote: {
                                url: '${adminPath}/user/check_username.json',
                                type: 'post',
                                cache: false,
                                data: {
                                    username: function () { return $('#username').prop('value'); }
                                }
                            }
                        },
                        password: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true,
                            remote: {
                                url: '${adminPath}/user/check_email.json',
                                type: 'post',
                                cache: false,
                                data: {
                                    email: function () { return $('#email').prop('value'); }
                                }
                            }
                        }
                    },
                    messages: {
                        orgName: {
                            required: '请选择用户所属机构！'
                        },
                        username: {
                            required: '登录用户名不能为空！',
                            remote: '登录用户名已存在！'
                        },
                        password: {
                            required: '登录密码不能为空！'
                        },
                        email: {
                            required: '邮箱不能为空！',
                            email: '邮箱格式错误！',
                            remote: '该邮箱已存在！'
                        }
                    },
                    onkeyup: false,
                    focusCleanup: true
                });

                $btnSave.on('click', function (e) {
                    if ($form.valid()) {
                        if ($roleDiv.is(':visible') && 0 === $roleItemDiv.find('.skin-minimal input:checked').size()) {
                            warningMsg('请选择要为用户分配的角色！');

                            return;
                        }

                        showMask();

                        $form.ajaxSubmit({
                            success: function(data) {
                                hideMask();

                                if ("success" == data.status) {
                                    successMsg('用户添加成功！', function() {
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