<script type="text/javascript">
    $(function () {//初始化
        $('#message_datagrid').datagrid({
            border: false,
            fit: true,
            striped: true,
            url: 'message/page',
            method: 'get',
            pagination: true,//显示分页
            fitColumns: true,//自动计算列的宽度
            pageSize: 10,//每页显示几条
            pageList: [10, 20, 30, 40, 50],//页面设置几条数据
            //rownumbers:true,不要设置，否则在IE6速度放慢
            //checkOnSelect : false,
            //selectOnCheck : false,
            singleSelect: true,
            onDblClickRow: function (rowIndex, rowData) {
                message_showDetail(rowData);
            },
            frozenColumns: [[ //冻结列
                {
                    field: 'ck',
                    checkbox: true
                }
            ]],
            columns: [[
                {
                    field: 'id',
                    title: '消息ID',
                    width: 50
                },
                {
                    field: 'consumerQueue',
                    title: '消费队列',
                    width: 30
                },
                {
                    field: 'statusName',
                    title: '消息状态',
                    width: 30
                },
                {
                    field: 'resendTimes',
                    title: '重发次数',
                    width: 30
                },
                {
                    field: 'alreadyDeadName',
                    title: '是否死亡',
                    width: 30,
                    formatter: function (value, row, index) {
                        if (row.alreadyDead === 1) {
                            return "<span style='color: red'>" + row.alreadyDeadName + "</span>";
                        } else {
                            return "<span style='color: dodgerblue'>" + row.alreadyDeadName + "</span>";
                        }
                    }
                },
                {
                    field: 'createTime',
                    title: '创建时间',
                    width: 30
                },
                {
                    field: 'confirmTime',
                    title: '确认时间',
                    width: 30
                },
                {
                    field: 'updateTime',
                    title: '更新时间',
                    width: 30
                },
                {
                    field: 'operation',
                    title: '操作',
                    width: 30,
                    formatter: function (value, row, index) {
                        var operators = "<span style='color: blue'>";
                        operators += "<a href='javascript:void(0)' onclick=\"messageDelete(\'" + row.id + "\')\">删除</a>";

                        if (row.status === 1) {
                            operators += " | ";
                            operators += "<a href='javascript:void(0)' onclick=\"messageResend(\'" + row.id + "\')\">重发</a>"
                        }
                        operators += "</span>";
                        return operators;
                    }
                }
            ]],
            toolbar: [
                {
                    text: '<span style="color: red"><strong>查看详情（双击）</strong></span>',
                    handler: function () {
                        var checkedRows = $('#message_datagrid').datagrid('getChecked');
                        if (checkedRows.length <= 0) {
                            $.messager.alert('错误提示', '请选择要查看的记录', 'error');
                        } else {
                            message_showDetail(checkedRows[0]);
                        }
                    }
                }, '-']
        });
    });

    /**
     * 删除消息
     */
    function messageDelete(id) {
        $.messager.confirm('确认', '您是否要删除当前的记录？', function (ret) {
            if (ret) {
                parent.$.messager.progress({
                    text: '正在执行，请稍后....',
                    interval: 100
                });
                $.ajax({
                    url: 'message/' + id,
                    data: {
                        _method: "DELETE"
                    },
                    type: "POST",//默认以get提交，使用get提交如果有中文后台会出现乱码
                    dataType: 'json',
                    success: function (rsp) {
                        parent.$.messager.progress('close'); // 关闭进程对话框
                        var dataGrid = $('#message_datagrid');
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
     * 重发消息
     */
    function messageResend(id) {
        $.messager.confirm('确认', '您是否要重发当前消息？', function (ret) {
            if (ret) {
                parent.$.messager.progress({
                    text: '正在执行，请稍后....',
                    interval: 100
                });
                $.ajax({
                    url: 'message/' + id + "/resend",
                    type: "POST",//默认以get提交，使用get提交如果有中文后台会出现乱码
                    dataType: 'json',
                    success: function (rsp) {
                        parent.$.messager.progress('close'); // 关闭进程对话框
                        var dataGrid = $('#message_datagrid');
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
     * 详情页
     */
    function message_showDetail(rowData) {
        var dig = $('<div  />').dialog({
            href: 'page/message/detail',
            width: 850,
            height: 500,
            modal: true,
            title: '详情',
            buttons: [{
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    //关闭窗口
                    dig.dialog('close');
                }
            }],
            onClose: function () {
                //关闭后回收内存
                $(this).dialog('destroy');
            },
            onLoad: function () {
                //必须在窗体打开之前加载数据
                getMessageDetail(rowData.id);
            }
        });
    }

    /**
     * 获取消息详情
     */
    function getMessageDetail(messageId) {
        $.ajax({
            type: "GET",
            cache: false,
            dataType: "json",
            timeout: 15000,
            url: "message/" + messageId,
            success: function (retObj, textStatus, XMLHttpRequest) {
                if (0 === retObj.code) {
                    $('#message_detail_form').form('load', retObj.data);
                } else {
                    $.messager.alert('错误提示', '加载数据异常', 'error');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }

</script>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 130px;">
         <#include "search.ftl"/>
    </div>
    <div data-options="region:'center',border:false">
        <div id="message_datagrid"></div>
    </div>
</div>