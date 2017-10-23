<!DOCTYPE HTML>

<html>
    <head>
        <#include "../include/header.ftl">
        <link href="${ctx}/webjars/zTree/3.5.29/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
        <title>${property("systemInfo.name")}-添加区域</title>
    </head>
    <body>
        <div class="page-container">
            <form action="${adminPath}/org/add.json" class="form form-horizontal" method="post">
            <#if parent.id != 1>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        上级机构：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" value="${parent.name!''}" disabled>
                    </div>
                </div>
            </#if>
                <#if areas?size gt 0>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        所属区域：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <span class="btn-upload form-group">
  							<input id="areaName" class="input-text upload-url" type="text" readonly placeholder="所属区域">
  							<input id="areaId" name="area.id" type="hidden">
  							<a id="btnChangeArea" href="javascript:void(0);" class="btn btn-primary">
  								<i class="Hui-iconfont">&#xe665;</i>
  							</a>
                            <a id="btnCancelChangeArea" href="javascript:void(0);" class="btn btn-danger" title="撤销">
  								<i class="Hui-iconfont">&#xe66b;</i>
  							</a>
  						</span>
                    </div>
                </div>
                </#if>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>机构编码：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="code" name="code" placeholder="机构编码">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        <span class="c-red">*</span>机构名称：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="name" name="name" placeholder="机构名称">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        联系地址：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="address" name="address" placeholder="联系地址">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        邮编：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="zipCode" name="zipCode" placeholder="邮编">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        负责人：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="master" name="master" placeholder="负责人">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        联系电话：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="phone" name="phone" placeholder="联系电话">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        传真：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="fax" name="fax" placeholder="传真">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">
                        邮箱：
                    </label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="text" class="input-text" id="email" name="email" placeholder="邮箱">
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
                <input type="hidden" name="parent.id" value="${parent.id}">
            </form>
        </div>
        <div id="areaTreeContainer" class="page-container" style="display: none;">
            <div class="row cl">
                <div class="col-xs-12 col-sm-12">
                    <ul id="areaTree" class="ztree"></ul>
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

                <#if areas?size gt 0>
                    var $btnChangeArea = $('#btnChangeArea');
                    var $btnCancelChangeArea = $('#btnCancelChangeArea');
                    var $areaTreeContainer = $('#areaTreeContainer');
                    var $areaTree = $('#areaTree');
                    var $areaName = $('#areaName');
                    var $areaId = $('#areaId');

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
                                $areaName.prop("value", treeNode.name.replace(/<[^>]+>/g, ""));
                                $areaId.prop("value", treeNode.id);

                                if (layerIndex) {
                                    layer.close(layerIndex);
                                }
                            }
                        }
                    };

                    var treeNodes = [];

                    <#list areas as area>
                        treeNodes.push({
                            id: ${area.id},
                            pId: ${area.parent.id},
                            name: "<i class='Hui-iconfont Hui-iconfont-home'>${area.name}</i>"
                        });
                    </#list>

                    var tree = $.fn.zTree.init($areaTree, setting, treeNodes);

                    tree.expandAll(true);

                    $btnChangeArea.on("click", function (e) {
                        layerIndex = layer.open({
                            type: 1,
                            area: ['300px', '450px'],
                            fix: true,
                            maxmin: false,
                            move: false,
                            closeBtn: 1,
                            shade: 0.4,
                            title: '机构所属区域',
                            content: $areaTreeContainer
                        });
                    });

                    $btnCancelChangeArea.on('click', function (e) {
                        if ($areaId.prop('value')) {
                            var node = tree.getNodeByParam('id', $areaId.prop('value'));

                            if (node) {
                                tree.checkNode(node, false);
                            }

                            $areaName.prop('value', '');
                            $areaId.prop('value', '');
                        }
                    });
                </#if>

                $form.validate({
                    rules: {
                        code: {
                            required: true,
                            remote: {
                                url: '${adminPath}/org/check_code.json',
                                type: 'post',
                                cache: false,
                                data: {
                                    code: function () { return $('#code').prop('value'); }
                                }
                            }
                        },
                        name: {
                            required: true
                        },
                        zipCode: {
                            isZipCode: true
                        },
                        phone: {
                            isTel: true
                        },
                        fax: {
                            isPhone: true
                        },
                        email: {
                            email: true
                        }
                },
                messages: {
                    code: {
                        required: '机构编码不能为空！',
                        remote: '机构编码已存在'
                    },
                    name: {
                        required: "机构名称不能为空！"
                    },
                    fax: {
                        isPhone: "传真格式错误！"
                    },
                    email: {
                        email: "邮箱格式错误！"
                    }
                },
                onkeyup: false,
                focusCleanup: true
            });

            $btnSave.on("click", function(e) {
                if ($form.valid()) {
                    showMask();

                    $form.ajaxSubmit({
                        success: function(data) {
                            hideMask();

                            if ("success" == data.status) {
                                successMsg('机构添加成功！', function() {
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

            $btnCancel.on("click", function(e) {
                layer_close();
            });
        });
    </script>
    </body>
</html>