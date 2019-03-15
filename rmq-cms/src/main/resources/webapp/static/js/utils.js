serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

/**
 * 判断是否为未定义
 *
 * @param value 待判断内容
 * @returns {boolean}
 */
function isUndefined(value){
    if(value != undefined && value != ''){
        return false;
    }
    return true;
}

function changePassword() {
    var changePasswordDialog = $('<div/>').dialog({
        title: '修改密码',
        width: 330,
        height: 210,
        modal: true,
        href: 'views/changePassword.jsp',
        buttons: [
            {
                text: '修改',
                iconCls: 'icon-ok',
                handler: function () {
                    parent.$.messager.progress({
                        text: '正在执行，请稍后....',
                        interval: 100
                    });
                    if (!$('#changePasswordForm').form('validate')) {
                        parent.$.messager.progress('close');
                    }
                    $('#changePasswordForm').form('submit', {
                        url: 'manager/user/password/change',
                        success: function (ret) {
                            parent.$.messager.progress('close');
                            var httpRsp = $.parseJSON(ret);
                            changePasswordDialog.dialog('close');
                            $.messager.show({
                                title: '成功提示',
                                msg: httpRsp.msg
                            });
                        },
                        error: function (ret) {
                            $.messager.alert('错误提示', httpRsp.msg, 'error');
                        }
                    });
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    changePasswordDialog.dialog('close');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}

