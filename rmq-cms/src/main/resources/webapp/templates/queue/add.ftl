
<script type="text/javascript">

    // 处理添加
    function queue_handle_add(dig) {

        parent.$.messager.progress({ // 打开进程对话框
            text: '正在执行，请稍后....',
            interval: 100
        });

        var addForm = $('#queue_add_form');
        if (!addForm.form('validate')) {
            parent.$.messager.progress('close'); // 关闭进程对话框
            return;
        }

        addForm.form('submit', {
            url: 'queue',
            success: function (retJson) {
                parent.$.messager.progress('close');  // 关闭进程对话框
                var retObj = $.parseJSON(retJson);
                if (retObj.code === 0) {
                    $('#queue_datagrid').datagrid('load'); // 动态刷新
                    dig.dialog('close'); // 关闭窗口
                    $.messager.show({title: '成功提示', msg: retObj.msg});
                } else {
                    $.messager.alert('错误提示', retObj.msg, 'error');
                }
            }
        });


    }
</script>

<form id="queue_add_form" method="post" class="field-form">
    <table>
        <tr>
            <td>业务名称<span class="span_required">*</span></td>
            <td>
                <input name="businessName" class="easyui-validatebox" maxlength="64" style="width: 165px;"
                       data-options="
                       required: true,
                       missingMessage: '请填写业务名称'
                       "/>
            </td>
            <td>消费队列<span class="span_required">*</span></td>
            <td>
                <input name="consumerQueue" class="easyui-validatebox" maxlength="64" style="width: 165px;"
                       data-options="
                       required: true,
                       missingMessage: '请填写消费队列'
                       "/>
            </td>
        </tr>
    </table>
</form>
