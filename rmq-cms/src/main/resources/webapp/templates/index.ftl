<#assign basePath=request.contextPath + "/" />

<#if Session["session_user"]?exists>
    <#assign username = Session["session_user"].userName />
</#if>
<html>
<head>
    <title>CMS</title>
    <#include "imports.ftl"/>
</head>
<body class="easyui-layout">
<div data-options="region:'north',border:false" style="height:50px">
    <div>
        <div class="header">
            <div class="header-user">
                <a><b>${username}，您好！</b></a>
                <img src="${basePath}images/icon/modify.png" style="margin-left: 20px">
                <a onclick="changePassword(); return false;" style="cursor:pointer;">修改密码</a>
                <img src="${basePath}images/icon/exit.png" style="margin-left: 20px">
                <a onclick="logout(); return false;" style="cursor:pointer;">安全退出</a>
            </div>
            <div class="header-brand">
                <a>CMS</a>
            </div>
        </div>
    </div>
</div>
<div id="index_west" data-options="region:'west',split:true" title="菜单" style="width:200px">
    <#include "index-menu.ftl"/>

</div>
<div id="index_center" data-options="region:'center', border:false">
    <div id="center_tabs_layout" class="easyui-tabs" data-options="border:false,fit:true"
         style="width: auto; height: auto"/>
</div>
</div>
</body>
<script type="text/javascript">

    function logout() {
        $.messager.confirm('系统提示', '您确定要退出系统吗？', function (result) {
            if (result) {
                location.href = "logout"
            }
        });
    }

    function changePassword() {
        var dg = $('<div/>').dialog({
            title: '修改密码',
            width: 330,
            height: 180,
            modal: true,
            href: 'sys_user/page?name=change-pwd',
            buttons: [
                {
                    text: '修改',
                    iconCls: 'icon-ok',
                    handler: function () {
                        parent.$.messager.progress({
                            text: '正在执行，请稍后....',
                            interval: 100
                        });
                        if (!$('#password-changeform').form('validate')) {
                            parent.$.messager.progress('close');
                        }
                        $('#password-changeform').form('submit', {
                            url: 'sys_user/password/change',
                            success: function (ret) {
                                parent.$.messager.progress('close');
                                var rspJson = $.parseJSON(ret);
                                if(rspJson.code == 0){
                                    dg.dialog('close');
                                    $.messager.show({
                                        title: '成功提示',
                                        msg: rspJson.msg
                                    });
                                }else{
                                    $.messager.alert('错误提示', rspJson.msg, 'error');
                                }
                            },
                            error: function () {
                                parent.$.messager.progress('close');
                                $.messager.alert('错误提示', '修改密码失败', 'error');
                            }
                        });
                    }
                },
                {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dg.dialog('close');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy');
            }
        });
    }
</script>
</html>