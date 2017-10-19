<!DOCTYPE HTML>

<html>
<head>
<#include "../include/header.ftl">
    <title>${property("systemInfo.name")}-编辑区域</title>
</head>
<body>
<div class="page-container">
    <form action="${adminPath}/area/update.json" class="form form-horizontal" method="post">
    <#if area.parent.id != 1>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                上级区域：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" value="${area.parent.name!''}" disabled>
            </div>
        </div>
    </#if>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>区域编码：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="code" name="code" value="${area.code!''}" placeholder="区域编码">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">
                <span class="c-red">*</span>区域名称：
            </label>
            <div class="formControls col-xs-8 col-sm-9">
                <input type="text" class="input-text" id="name" name="name" value="${area.name!''}" placeholder="区域名称">
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
        <input type="hidden" name="parent.id" value="${area.parent.id}">
        <input type="hidden" name="id" value="${area.id}">
    </form>
</div>
<#include "../include/footer.ftl">
<script>
    $().ready(function (e) {
        var $form = $("form:first");
        var $btnSave = $("#btnSave");
        var $btnCancel = $("#btnCancel");

        $form.validate({
            rules: {
                code: {
                    required: true,
                    remote: {
                        url: '${adminPath}/area/check_code.json',
                        type: 'post',
                        cache: false,
                        data: {
                            oldCode: function () { return '${area.code!''}'; },
                            code: function () { return $('#code').prop('value'); }
                        }
                    }
                },
                name: {
                    required: true
                }
            },
            messages: {
                code: {
                    required: '区域编码不能为空！',
                    remote: '区域编码已存在'
                },
                name: {
                    required: "区域名称不能为空！"
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
                            successMsg('区域修改成功！', function() {
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