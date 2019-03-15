
<form id="user-createform" method="post" class="field-form">
    <table>
        <tr>
            <td>用户名<span class="required-field-title">*</span></td>
            <td><input name="userName" class="easyui-validatebox" data-options="required:true"/></td>
            <td>密码<span class="required-field-title">*</span></td>
            <td><input name="userPwd" class="easyui-validatebox" data-options="required:true"/></td>
        </tr>
        <tr>
            <td>状态<span class="required-field-title">*</span></td>
            <td>
                <select name="userStatus" class="easyui-combobox"
                        data-options="required:true, panelHeight:'auto', editable:false" style="width: 165px;height: 22px">
                    <option value="0">禁用</option>
                    <option value="1">启用</option>
                </select>
            </td>
        </tr>
    </table>
</form>
