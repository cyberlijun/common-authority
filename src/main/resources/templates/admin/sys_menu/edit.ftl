<!DOCTYPE HTML>

<html>
<head>
<#include "../include/header.ftl">
    <link href="${ctx}/webjars/fontIconPicker/2.0.0/css/jquery.fonticonpicker.min.css" rel="stylesheet">
    <link href="${ctx}/webjars/fontIconPicker/2.0.0/themes/bootstrap-theme/jquery.fonticonpicker.bootstrap.min.css" rel="stylesheet">
    <title>${property("systemInfo.name")}-添加菜单</title>
</head>
<body>
<div class="page-container">
    <form action="${adminPath}/menu/update.json" class="form form-horizontal" method="post">
    <#if menu.parent.id != 1>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                上级菜单：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${menu.parent.menuName!''}" disabled>
            </div>
        </div>
    </#if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>菜单名称：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="menuName" name="menuName" value="${menu.menuName!''}" placeholder="菜单名称">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                菜单URL：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="menuUrl" name="menuUrl" value="${menu.menuUrl!''}" placeholder="菜单URL">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                菜单图标：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" id="iconCls" name="iconCls" value="${menu.iconCls!''}">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                权限标识：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="permission" name="permission" value="${menu.permission!''}" placeholder="权限标识">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                是否显示：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <div class="skin-minimal">
                    <div class="radio-box">
                        <input id="display_yes" type="radio" name="display" value="1"<#if menu.display> checked</#if>>
                        <label for="display_yes">是</label>
                    </div>
                    <div class="radio-box">
                        <input id="display_no" type="radio" value="0" name="display"<#if !menu.display> checked</#if>>
                        <label for="display_no">否</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>排序：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="sort" name="sort" value="${menu.sort!''}" placeholder="排序">
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
        <input type="hidden" name="id" value="${menu.id}">
        <input type="hidden" name="parent.id" value="${menu.parent.id}">
    </form>
</div>
<#include "../include/footer.ftl">
<script src="${ctx}/webjars/fontIconPicker/2.0.0/jquery.fonticonpicker.min.js"></script>
<script>
    $().ready(function (e) {
        var $iconCls = $("#iconCls");
        var $radio = $('.skin-minimal input');
        var $form = $("form:first");
        var $btnSave = $("#btnSave");
        var $btnCancel = $("#btnCancel");

        var icons = getIcons();

        $iconCls.fontIconPicker({
            theme: 'fip-bootstrap',
            hasSearch: false,
            allCategoryText: '全部分类',
            source: icons
        });

        $radio.iCheck({
            radioClass: 'iradio_square-blue',
            increaseArea: '20%'
        });

        $form.validate({
            rules: {
                menuName: {
                    required: true
                },
                sort: {
                    required: true,
                    digits: true
                }
            },
            messages: {
                menuName: {
                    required: "菜单名不能为空！"
                },
                sort: {
                    required: "排序不能为空！",
                    digits: "排序必须为整数！"
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
                            successMsg('菜单修改成功！', function() {
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