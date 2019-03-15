
<form id="role-updateform" method="post" class="field-form">
    <input name="roleId" type="hidden"/>
    <table>
        <tr>
            <td>名称<span class="required-field-title">*</span></td>
            <td><input name="roleName" class="easyui-validatebox" data-options="required:true"/></td>
            <td>状态<span class="required-field-title">*</span></td>
            <td>
                <select name="status" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false" style="width: 165px; height: 26px" >
                    <option value=0>禁用</option>
                    <option value=1>启用</option>
                </select>
            </td>
        </tr>
    </table>
</form>
