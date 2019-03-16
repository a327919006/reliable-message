
<script type="text/javascript">
    /**
     * 查询
     */
    function searchMessage() {
        $('#message_datagrid').datagrid('load', serializeObject($('#message_searchform')));
    }

    /**
     * 清空
     */
    function clearSearchMessageForm() {
        $('#message_searchform').form('clear'); // 清空数据
        $('#message_datagrid').datagrid('load', {}); // 重新加载
    }
</script>

<form id="message_searchform" class="field-form" style="margin-top:5px;margin-bottom: 0px;">
    <table>
        <tr>
            <td>消息ID</td>
            <td><input name="id" class="easyui-validatebox"/></td>
            <td>消费队列</td>
            <td><input name="consumerQueue" class="easyui-validatebox"/></td>
            <td>消息状态</td>
            <td>
                <select name="status" class="easyui-combobox" style="width: 165px;"
                        data-options="panelHeight:'auto', editable:false" >
                    <option value="" selected="selected">全部</option>
                    <option value="0">待确认</option>
                    <option value="1">发送中</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>是否死亡</td>
            <td>
                <select name="alreadyDead" class="easyui-combobox" style="width: 165px;"
                        data-options="panelHeight:'auto', editable:false" >
                    <option value="" selected="selected">全部</option>
                    <option value="0">未死亡</option>
                    <option value="1">已死亡</option>
                </select>
            </td>
            <td>创建时间</td>
            <td colspan="3">
                <input name="createStartTime" class="easyui-datetimebox"/>
                至
                <input name="createEndTime" class="easyui-datetimebox"/>
            </td>
        </tr>
        <tr>
            <td style="text-align:right" colspan="3">
                <a onclick="searchMessage()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
            <td style="text-align:left" colspan="3">
                <a onclick="clearSearchMessageForm()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">重置</a>
            </td>
        </tr>
    </table>
</form>