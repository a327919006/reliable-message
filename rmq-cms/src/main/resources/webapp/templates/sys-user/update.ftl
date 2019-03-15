
<form id="user-updateform" method="post" class="field-form">
    <input name="sysUserId" type="hidden"/>
    <input name="userPwd" type="hidden"/>
    <table>
        <tr>
            <td>用户名<span class="required-field-title">*</span></td>
            <td>
                <input name="userName" class="easyui-validatebox" data-options="required:true"/>
            </td>

            <td>状态<span class="required-field-title">*</span></td>
            <td>
                <select name="userStatus" id="status" class="easyui-combobox"
                        data-options="required:true, panelHeight:'auto', editable:false" style="width: 165px;height: 22px">
                    <option>请选择</option>
                    <option value=0>禁用</option>
                    <option value=1>启用</option>
                </select>
            </td>
        </tr>
    </table>
</form>
