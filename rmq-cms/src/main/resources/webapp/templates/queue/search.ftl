
<script type="text/javascript">
    /**
     * 查询
     */
    function searchQueue() {
        $('#queue_datagrid').datagrid('load', serializeObject($('#queue_searchform')));
    }

    /**
     * 清空
     */
    function clearSearchQueueForm() {
        $('#queue_searchform').form('clear'); // 清空数据
        $('#queue_datagrid').datagrid('load', {}); // 重新加载
    }
</script>

<form id="queue_searchform" class="field-form" style="margin-top:5px;margin-bottom: 0px;">
    <table>
        <tr>
            <td>业务名称</td>
            <td><input name="businessName" class="easyui-validatebox"/></td>
            <td>消费队列</td>
            <td><input name="consumerQueue" class="easyui-validatebox"/></td>
            <td>创建时间</td>
            <td >
                <input name="createStartTime" class="easyui-datetimebox"/>
                至
                <input name="createEndTime" class="easyui-datetimebox"/>
            </td>
        </tr>
        <tr>
            <td style="text-align:right" colspan="3">
                <a onclick="searchQueue()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
            </td>
            <td style="text-align:left" colspan="3">
                <a onclick="clearSearchQueueForm()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">重置</a>
            </td>
        </tr>
    </table>
</form>