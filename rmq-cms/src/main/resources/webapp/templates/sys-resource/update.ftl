
<form id="resource-updateform" method="post" class="field-form">
    <input name="resourceId" type="hidden"/>
    <table>
        <tr>
            <td>名称<span class="required-field-title">*</span></td>
            <td>
                <input name="name" class="easyui-validatebox" data-options="required:true"/>
            </td>
            <td>类型<span class="required-field-title">*</span></td>
            <td>
                <select name="type" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false" style="width: 165px;height: 22px">
                    <option value=0>菜单</option>
                    <option value=1>按钮</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>状态<span class="required-field-title">*</span></td>
            <td>
                <select name="status" class="easyui-combobox" data-options="required:true,panelHeight:'auto',editable:false" style="width: 165px;height: 26px">
                    <option value=0>禁用</option>
                    <option value=1>启用</option>
                </select>
            </td>
            <td>排序<span class="required-field-title">*</span></td>
            <td><input name="priority" class="easyui-validatebox" data-options="required:true"/></td>
        </tr>
        <tr>
            <td>URL</td>
            <td colspan="3"><input name="url" class="easyui-validatebox" style="width:386px"/></td>
        </tr>
        <tr>
            <td>权限</td>
            <td colspan="3"><input name="permission" class="easyui-validatebox" style="width:386px"/></td>
        </tr>
        <tr>
            <td>图标<span class="required-field-title">*</span></td>
            <td colspan="3"><input name="icon" class="easyui-validatebox" data-options="required:true" style="width:386px"/></td>
        </tr>
        <tr>
            <td>上级资源</td>
            <td colspan="3">
                <input name="parentId" id="editResourceParentId" type="hidden"/>
                <input name="parentName" id="editResourceParentName" type="text" class="easyui-validatebox" readonly="readonly" style="width: 165px;"/>
                <input type="button" value="选择" onClick="selectParentMenu($('#resource-updateform #editResourceParentId'), $('#resource-updateform #editResourceParentName'));" style="width: 50px"/>
                <input type="button" value="清空" onClick="clearParentMenu($('#resource-updateform #editResourceParentId'),$('#resource-updateform #editResourceParentName'));" style="width: 50px"/>
            </td>
        </tr>
    </table>
</form>
