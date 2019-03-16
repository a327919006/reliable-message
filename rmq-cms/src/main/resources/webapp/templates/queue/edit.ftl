
<script type="text/javascript">
    // 处理修改
    function queue_handle_edit(dig) {

        parent.$.messager.progress({ // 打开进程对话框
            text: '正在执行，请稍后....',
            interval: 100
        });

        var editForm = $('#queue_edit_form');
        if (!editForm.form('validate')) {
            parent.$.messager.progress('close'); // 关闭进程对话框
            return;
        }

        editForm.form('submit', {
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

<form id="queue_edit_form" method="post" class="field-form">
    <input type="hidden" name="id"/>
    <input type="hidden" name="_method" value="PUT"/>

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
            <td>业务名称<span class="span_required">*</span></td>
            <td>
                <input name="consumerQueue" class="easyui-validatebox" maxlength="64" style="width: 165px;"
                       data-options="
                       required: true,
                       missingMessage: '请填写业务名称'
                       "/>
            </td>
        </tr>
    </table>
</form>

	
