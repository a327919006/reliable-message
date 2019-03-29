<script type="text/javascript">
    $(function () {//初始化
        $('#queue_datagrid').datagrid({
            border: false,
            fit: true,
            striped: true,
            url: 'queue/page',
            method: 'get',
            pagination: true,//显示分页
            fitColumns: true,//自动计算列的宽度
            pageSize: 10,//每页显示几条
            pageList: [10, 20, 30, 40, 50],//页面设置几条数据
            //rownumbers:true,不要设置，否则在IE6速度放慢
            //checkOnSelect : false,
            //selectOnCheck : false,
            singleSelect: true,
            columns: [[
                {
                    field: 'businessName',
                    title: '业务名称',
                    width: 30
                },
                {
                    field: 'consumerQueue',
                    title: '消费队列',
                    width: 30
                },
                {
                    field: 'checkUrl',
                    title: '消息确认url',
                    width: 80
                },
                {
                    field: 'checkDuration',
                    title: '确认条件(毫秒)',
                    width: 30
                },
                {
                    field: 'checkTimeout',
                    title: '确认超时时长(毫秒)',
                    width: 30
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 30
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    width: 30
                },
                {
                    field: 'createUser',
                    title: '创建人',
                    width: 20
                },
                {
                    field: 'updateUser',
                    title: '操作人',
                    width: 20
                },
                {
                    field: 'operation',
                    title: '操作',
                    width: 30,
                    formatter: function (value, row, index) {
                        var operators = "<span style='color: blue'>";
                        operators += "<a href='javascript:void(0)' onclick=\"queueDelete(\'" + row.id + "\')\">删除</a>";

                        operators += " | ";
                        operators += "<a href='javascript:void(0)' onclick=\"queueResendDead(\'" + row.id + "\')\">重发死亡消息</a>";
                        operators += "</span>";
                        return operators;
                    }
                }
            ]],
            toolbar: [
                {
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: function () {
                        queueAdd();
                    }
                },'-',{
                    text: '修改',
                    iconCls: 'icon-edit',
                    handler: function () {
                        queueEdit();
                    }
                }, '-']
        });
    });

    /**
     * 添加
     */
    function queueAdd(){
        var dig = $('<div />').dialog({
            href : 'page/queue/add',
            width : 600,
            height : 200,
            modal : true,
            title : '新增',
            buttons : [ {
                text : '保存',
                iconCls : 'icon-save',
                handler : function() {
                    queue_handle_add(dig);
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    dig.dialog('close'); //关闭窗口
                }
            } ],
            onClose: function () {
                $(this).dialog('destroy'); // 关闭后回收内存
            }
        });
    }

    /**
     * 修改
     */
    function queueEdit() {
        var rows = $('#queue_datagrid').datagrid('getChecked');
        if (rows.length === 1) {
            var dig = $('<div />').dialog({
                href : 'page/queue/edit',
                width : 600,
                height : 200,
                modal : true,
                title : '修改',
                buttons : [ {
                    text : '保存',
                    iconCls : 'icon-save',
                    handler : function() {
                        queue_handle_edit(dig);
                    }
                }, {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dig.dialog('close'); // 关闭窗口
                    }
                }],
                onClose: function () {
                    $(this).dialog('destroy'); // 关闭后回收内存
                },
                onLoad: function () {
                    $('#queue_edit_form').form('load', rows[0]); // 必须在窗体打开之前加载数据
                }
            });
        } else if (rows.length > 1) {
            $.messager.alert('错误提示', '请勾选一项，请勿多选', 'error');
        } else {
            $.messager.alert('错误提示', '请勾选要修改的记录', 'error');
        }
    }

    /**
     * 删除
     */
    function queueDelete(id) {
        $.messager.confirm('确认', '您是否要删除当前的记录？', function (ret) {
            if (ret) {
                parent.$.messager.progress({
                    text: '正在执行，请稍后....',
                    interval: 100
                });
                $.ajax({
                    url: 'queue/' + id,
                    data: {
                        _method: "DELETE"
                    },
                    type: "POST",//默认以get提交，使用get提交如果有中文后台会出现乱码
                    dataType: 'json',
                    success: function (rsp) {
                        parent.$.messager.progress('close'); // 关闭进程对话框
                        var dataGrid = $('#queue_datagrid');
                        dataGrid.datagrid('load');
                        dataGrid.datagrid('unselectAll');//取消选中
                        if (rsp.code === 0) {
                            $.messager.show({
                                title: '成功提示',
                                msg: rsp.msg
                            });
                        } else {
                            $.messager.alert('错误提示', rsp.msg, 'error');
                        }
                    }
                });
            }
        });
    }

    /**
     * 重发死亡消息
     */
    function queueResendDead(id) {
        $.messager.confirm('确认', '您是否要该队列所有死亡消息？', function (ret) {
            if (ret) {
                parent.$.messager.progress({
                    text: '正在执行，请稍后....',
                    interval: 100
                });
                $.ajax({
                    url: 'queue/' + id + "/resend",
                    type: "POST",//默认以get提交，使用get提交如果有中文后台会出现乱码
                    dataType: 'json',
                    success: function (rsp) {
                        parent.$.messager.progress('close'); // 关闭进程对话框
                        var dataGrid = $('#queue_datagrid');
                        dataGrid.datagrid('load');
                        dataGrid.datagrid('unselectAll');//取消选中
                        if (rsp.code === 0) {
                            $.messager.show({
                                title: '成功提示',
                                msg: "重新发送" + rsp.data +"条消息"
                            });
                        } else {
                            $.messager.alert('错误提示', rsp.msg, 'error');
                        }
                    }
                });
            }
        });
    }


</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 100px;">
         <#include "search.ftl"/>
    </div>
    <div data-options="region:'center',border:false">
        <div id="queue_datagrid"></div>
    </div>
</div>