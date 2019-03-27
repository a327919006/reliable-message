
<script type="text/javascript">
    $(function () {
        $("#message_detail_form input").attr("readonly", "readonly");
        $("#message_detail_form select").attr("readonly", "readonly");
        $("#message_detail_form textarea").attr("readonly", "readonly");
    });

</script>

<form id="message_detail_form" method="post" class="field-form">
    <table>
        <tr>
            <td>消息ID</td>
            <td><input name="id" class="easyui-validatebox" style="width: 250px;"/></td>
            <td>消费队列</td>
            <td><input name="consumerQueue" class="easyui-validatebox" style="width: 250px;"/></td>
        </tr>
        <tr>
            <td>消息状态</td>
            <td><input name="statusName" class="easyui-validatebox" style="width: 250px;"/></td>
            <td>重发次数</td>
            <td><input name="resendTimes" class="easyui-validatebox" style="width: 250px;"/></td>
        </tr>
        <tr>
            <td>是否死亡</td>
            <td><input name="alreadyDeadName" class="easyui-validatebox" style="width: 250px;"/></td>
            <td>创建时间</td>
            <td><input name="createTime" class="easyui-validatebox" style="width: 250px;"/></td>
        </tr>
        <tr>
            <td>确认时间</td>
            <td><input name="confirmTime" class="easyui-validatebox" style="width: 250px;"/></td>
            <td>更新时间</td>
            <td><input name="updateTime" class="easyui-validatebox" style="width: 250px;"/></td>
        </tr>
        <tr>
            <td>消息内容</td>
            <td colspan="3">
                <textarea name="messageBody" class="easyui-textbox" data-options="multiline:true"
                          style="width: 700px; height:250px; resize:none"></textarea>
            </td>
        </tr>
    </table>
</form>
