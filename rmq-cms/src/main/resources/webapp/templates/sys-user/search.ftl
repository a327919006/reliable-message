
<script type="text/javascript">
    /**
     * 查询
     */
    function searchUsers() {
        $('#user-datagrid').datagrid('load', serializeObject($('#user-searchform')));
    }

    /**
     * 清空
     */
    function clearSearchUserForm() {
        $('#user-searchform').form('clear'); // 清空数据
        $('#user-datagrid').datagrid('load', {}); // 重新加载
    }
</script>

<form id="user-searchform" class="field-form" style="margin-top:5px;margin-bottom: 0px;">
    <table>
        <tr>
            <td>用户名</td>
            <td><input name="userName" class="easyui-validatebox"/></td>
            <td>状态</td>
            <td>
                <select name="userStatus" class="easyui-combobox"
                        data-options="panelHeight:'auto',editable:false" style="width: 165px;height: 26px">
                    <option value=-1 selected>全部</option>
                    <option value=0>禁用</option>
                    <option value=1>启用</option>
                </select>
            </td>
            <td >
                <a onclick="searchUsers()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
            <td >
                <a onclick="clearSearchUserForm()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">清空</a>
            </td>
        </tr>
    </table>
</form>