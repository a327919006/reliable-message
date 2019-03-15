
<script type="text/javascript">
    /**
     * 查询
     */
    function searchResources() {
        $('#resource-datagrid').datagrid('load', serializeObject($('#resource-searchform')));
    }

    /**
     * 清空
     */
    function clearSearchResourceForm() {
        $('#resource-searchform').form('clear'); // 清空数据
        $('#resource-datagrid').datagrid('load', {}); // 重新加载
    }
</script>

<form id="resource-searchform" class="field-form" style="margin-top:5px;margin-bottom: 0px;">
    <table>
        <tr>
            <td>名称</td>
            <td><input name="name" class="easyui-validatebox"/></td>
            <td>类型</td>
            <td>
                <select name="type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 165px;height: 22px">
                    <option value="">全部</option>
                    <option value=0>菜单</option>
                    <option value=1>按钮</option>
                </select>
            </td>
            <td>URL</td>
            <td><input name="url" class="easyui-validatebox"/></td>
            <td>权限</td>
            <td><input name="permission" class="easyui-validatebox"/></td>
            <td>
                <a onclick="searchResources()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
            <td>
                <a onclick="clearSearchResourceForm()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">清空</a>
            </td>
        </tr>
    </table>
</form>