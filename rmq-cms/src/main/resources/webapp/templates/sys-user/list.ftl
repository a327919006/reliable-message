
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',title:'查询条件',border:true" style="height: 140px;">
        <#include "search.ftl"/>
    </div>

    <div id="user-toolbar" class="datagrid-toolbar">
        <table>
            <tr>
                <td><a onclick="createUser();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">增加</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="deleteUsers();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="updateUser();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="$('#user-datagrid').datagrid('unselectAll');" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true">取消选中</a></td>
                <td><div class="datagrid-btn-separator"></div></td>
            </tr>
        </table>
    </div>

    <div data-options="region:'center',border:true">
        <table id="user-datagrid" class="easyui-datagrid" title="管理员列表"
            data-options="
                border: false,
                fit: true,
                striped: true,
                url: 'sys_user/search',
                method: 'post',
                pagination: true,
                isFiled: 'sysUserId',
                fitColumns: true,
                rownumbers: true,
                singleSelect: true,
                toolbar:'#user-toolbar',
                pageSize: 20,
                pageList: [ 10, 20, 30, 40, 50 ],
                onSelect: function (rowIndex, rowData) {
                    var zTreeObj = $.fn.zTree.getZTreeObj('user-roletree'); // 重新加载
                    if(null != zTreeObj) {
                        zTreeObj.destroy();
                    }
                    if(null != rowData) {
                        initUserRoleTree(rowData.sysUserId);
                    }
                }">
            <thead>
                <tr>
                    <th data-options="field: 'sysUserId',checkbox: true,width: 100">编号</th>
                    <th data-options="field: 'userName',width: 100">用户名</th>
                    <th data-options="field: 'userStatus',width: 70,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return '禁用';
                            } else if (value == 1) {
                                return '启用';
                            } else {
                                return '未知';
                            }
                        }">状态</th>
                    <th data-options="field: 'createTime',width: 100">创建时间</th>
                    <th data-options="field: 'updateTime',width: 100">更新时间</th>
                </tr>
            </thead>
        </table>
    </div>
    <div data-options="region:'east',border:true, collapsible:false" title="角色分配" style="width: 250px">
        <#include "setting.ftl"/>
    </div>
</div>

<script type="text/javascript">

    /**
     * 新增管理员
     */
    function createUser() {
        var dig = $('<div/>').dialog({
            href: 'sys_user/page?name=create',
            width: 490,
            height: 210,
            modal: true,
            title: '新增管理员',
            buttons: [
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        parent.$.messager.progress({ // 打开进程对话框
                            text: '正在执行，请稍后....',
                            interval: 100
                        });
                        if (!$('#user-createform').form('validate')) {
                            parent.$.messager.progress('close');
                        }

                        $('#user-createform').form('submit', {
                            url: 'sys_user/create',
                            dataType: 'json',
                            success: function (ret) {
                                parent.$.messager.progress('close');
                                var rspJson = $.parseJSON(ret);
                                if (0 == rspJson.code) {
                                    dig.dialog('close'); // 关闭窗口
                                    $('#user-datagrid').datagrid('load');
                                    $.messager.show({
                                        title: '成功提示',
                                        msg: rspJson.msg
                                    });
                                } else {
                                    $.messager.alert('错误提示', rspJson.msg, 'error');
                                }
                            },
                            error: function () {
                                parent.$.messager.progress('close');
                                $.messager.alert('错误提示', '新增失败', 'error');
                            }
                        });
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        dig.dialog('close'); // 关闭窗口
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy'); // 关闭后回收内存
            }
        });
    }

    /**
     * 删除管理员
     */
    function deleteUsers() {
        var selected = $('#user-datagrid').datagrid('getSelections');
        if (selected.length > 0) {
            $.messager.confirm('确认', '您是否要删除当前选中的记录？', function (ret) {
                if (!ret) { // 点击取消按钮
                    return;
                }

                var userIds = [];
                for (var i = 0; i < selected.length; i++) {
                    userIds.push(selected[i].sysUserId);
                }
                $.ajax({
                    url: 'sys_user/delete',
                    data: {
                        userIds: userIds.join(',')
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (rspJson) {
                        if (0 == rspJson.code) {
                            $('#user-datagrid').datagrid('load');
                            $('#user-datagrid').datagrid('unselectAll');//取消选中
                            $.messager.show({
                                title: '成功提示',
                                msg: rspJson.msg
                            });
                        } else {
                            $.messager.alert('错误提示', rspJson.msg, 'error');
                        }
                    },
                    error: function () {
                        $.messager.alert('错误提示', '删除失败', 'error');
                    }
                });
            });
        } else {
            $.messager.alert('错误提示', '请勾选要删除的记录', 'error');
        }
    }

    /**
     * 修改管理员
     */
    function updateUser() {
        var selected = $('#user-datagrid').datagrid('getSelections');
        if (selected.length == 1) {
            var dig = $('<div/>').dialog({
                href: 'sys_user/page?name=update',
                width: 500,
                height: 210,
                modal: true,
                title: '修改管理员',
                buttons: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            parent.$.messager.progress({ // 打开进程对话框
                                text: '正在执行，请稍后....',
                                interval: 100
                            });

                            if (!$('#user-updateform').form('validate')) {
                                parent.$.messager.progress('close');  // 关闭进程对话框
                            }

                            $('#user-updateform').form('submit', {
                                url: 'sys_user/update',
                                type: 'post',
                                dataType: 'json',
                                success: function (ret) {
                                    parent.$.messager.progress('close');
                                    var rspJson = $.parseJSON(ret);
                                    if (0 == rspJson.code) {
                                        dig.dialog('close'); // 关闭窗口
                                        $('#user-datagrid').datagrid('load');
                                        $.messager.show({
                                            title: '成功提示',
                                            msg: rspJson.msg
                                        });
                                    } else {
                                        $.messager.alert('错误提示', rspJson.msg, 'error');
                                    }
                                },
                                error: function () {
                                    parent.$.messager.progress('close');
                                    $.messager.alert('错误提示', '修改失败', 'error');
                                }
                            });
                        }
                    },
                    {
                        text: '关闭',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            dig.dialog('close'); // 关闭窗口
                        }
                    }
                ],
                onClose: function () {
                    $(this).dialog('destroy'); // 关闭后回收内存
                },
                onLoad: function () {
                    $('#user-updateform').form('load', selected[0]); // 必须在窗体打开之前加载数据
                }
            });
        } else if (selected.length > 1) {
            $.messager.alert('错误提示', '请勾选一项，请勿多选', 'error');
        } else {
            $.messager.alert('错误提示', '请勾选要修改的记录', 'error');
        }
    }

    /**
     * 设置地区信息
     */
    function setAreaInfo(areaId, areaName) {
        var dialog = $('<div/>').dialog({
            title: '选择菜单',
            href: 'sys_user/page?name=area-tree',
            width: 300,
            height: 300,
            modal: true,
            buttons: [
                {
                    text: '确定', iconCls: 'icon-ok',
                    handler: function () {
                        debugger;
                        var treeObj = $.fn.zTree.getZTreeObj("user-areatree");
                        var checkedNodes = treeObj.getCheckedNodes(true);
                        var checkedArray = new Array();
                        for (var i = 0; i < checkedNodes.length; i++) {
                            if (!checkedNodes[i].getCheckStatus().half) {
                                checkedArray.push(checkedNodes[i]);
                            }
                        }
                        if (checkedArray.length == 0) {
                            $.messager.alert('错误提示', '请选择上一级地区!', 'error');
                            return false;
                        } else if (checkedArray.length != 1) {
                            $.messager.alert('错误提示', '只能选择一个地区!', 'error');
                            return false;
                        } else {
                            areaId.val(checkedArray[0].areaId);
                            areaName.val(checkedArray[0].areaName);
                            dialog.dialog('close');
                        }
                    }
                }, {
                    text: '关闭', iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog('close');
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy'); // 关闭后回收内存
            },
            onLoad: function () {
                debugger;
                areaTreeInit(areaId.val());
            }
        });
    }

    /**
     * 清空地区信息
     */
    function clearAreaInfo(areaId, areaName) {
        areaId.val("");
        areaName.val("");
    }
</script>
