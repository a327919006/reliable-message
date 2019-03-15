
<script type="text/javascript">
    /**
     * 查询
     */
    function searchRoles() {
        $('#role-datagrid').datagrid('load', serializeObject($('#role-searchform')));
    }

    /**
     * 清空
     */
    function clearSearchForm() {
        $('#role-searchform').form('clear'); // 清空数据
        $('#role-datagrid').datagrid('load', {}); // 重新加载
    }
</script>
<form id="role-searchform" class="field-form" style="margin-top:5px;margin-bottom: 4px;">
    <table>
        <tr>
            <td>名称</td>
            <td><input name="roleName" maxlength="50" class="easyui-validatebox" style="width: 165px;"/></td>
            <td>状态</td>
            <td>
                <select name="status" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 165px;height: 26px">
                    <option value="">全部</option>
                    <option value=0>禁用</option>
                    <option value=1>启用</option>
                </select>
            </td>
            <td style="text-align:right" colspan="4">
                <a onclick="searchRoles()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
            <td>
                <a onclick="clearSearchForm()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">清空</a>
            </td>
        </tr>
    </table>
</form>