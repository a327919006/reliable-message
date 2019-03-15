
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',title:'查询条件',border:true" style="height: 85px;">
        <#include "search.ftl"/>
    </div>

    <div id="role-toolbar" class="datagrid-toolbar">
        <table>
            <tr>
                <td><a onclick="createRole();" class="easyui-linkbutton"
                       data-options="iconCls:'icon-add',plain:true">增加</a></td>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>

                <td><a onclick="deleteRoles();" class="easyui-linkbutton"
                       data-options="iconCls:'icon-remove',plain:true">删除</a></td>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>

                <td><a onclick="updateRole();" class="easyui-linkbutton"
                       data-options="iconCls:'icon-edit',plain:true">修改</a></td>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>

                <td><a onclick="$('#role-datagrid').datagrid('unselectAll');" class="easyui-linkbutton"
                       data-options="iconCls:'icon-redo',plain:true">取消选中</a></td>
                <td>
                    <div class="datagrid-btn-separator"></div>
                </td>
            </tr>
        </table>
    </div>

    <div data-options="region:'center', border:true">
        <table id="role-datagrid" class="easyui-datagrid" title="角色列表"
               data-options="
                   border: false,
                   fit:true,
                   striped: true,
                   url: 'sys_role/search',
                   method: 'post',
                   pagination: true,
                   isFiled: 'roleId',
                   fitColumns: true,
                   singleSelect: true,
                   toolbar:'#role-toolbar',
                   pageSize: 20,
                   pageList: [ 10, 20, 30, 40, 50 ],
                   onCheck: function (rowIndex, rowData) {
                      var zTreeObj = $.fn.zTree.getZTreeObj('role-resourcetree');
                      zTreeObj.destroy();
                      initRoleResourceTree(rowData.roleId);
                   }">
            <thead>
            <tr>
                <th data-options="field: 'roleId',checkbox: true,width: 100">编号</th>
                <th data-options="field: 'roleName',width: 100">名称</th>
                <th data-options="field: 'status',width: 100,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '禁用';
                        } else if (value == 1) {
                            return '启用';
                        } else {
                            return '未知';
                        }
                    }">状态
                </th>
                <th data-options="field: 'createTime',width: 100">创建时间</th>
                <th data-options="field: 'updateTime',width: 100">更新时间</th>
            </tr>
            </thead>
        </table>
    </div>

    <div data-options="region:'east',border:true" style="width: 300px">
        <div class="easyui-panel" data-options="title:'资源分配',border:false,fit:true">
            <#include "setting.ftl"/>
        </div>
    </div>
</div>

<script type="text/javascript">

    /**
     * 新增角色
     */
    function createRole() {
        var dialog = $('<div/>').dialog({
            href: 'sys_role/page?name=create',
            width: 470,
            height: 130,
            modal: true,
            title: '新增角色',
            buttons: [
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        parent.$.messager.progress({ // 打开进程对话框
                            text: '正在执行，请稍后....',
                            interval: 100
                        });
                        if (!$('#role-createform').form('validate')) { // 表单合法性校验
                            parent.$.messager.progress('close');
                        }

                        $('#role-createform').form('submit', {
                            url: 'sys_role/create',
                            success: function (ret) {
                                parent.$.messager.progress('close');
                                var rspJson = $.parseJSON(ret);
                                if (0 == rspJson.code) {
                                    dialog.dialog('close'); // 关闭窗口
                                    $('#role-datagrid').datagrid('load'); // 动态刷新
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
                        dialog.dialog('close'); //关闭窗口
                    }
                }
            ],
            onClose: function () {
                $(this).dialog('destroy'); //关闭后回收内存
            }
        });
    }

    /**
     * 删除角色
     */
    function deleteRoles() {
        var checked = $('#role-datagrid').datagrid('getChecked');
        if (checked.length > 0) {
            $.messager.confirm('确认', '您是否要删除当前选中的记录？', function (ret) {
                if (!ret) {
                    return;
                }

                var roleIds = [];
                for (var i = 0; i < checked.length; i++) {
                    roleIds.push(checked[i].roleId);
                }
                $.ajax({
                    url: 'sys_role/delete',
                    data: {
                        roleIds: roleIds.join(',')
                    },
                    type: 'post',
                    dataType: 'text',
                    success: function (ret) {
                        var rspJson = $.parseJSON(ret);
                        if (0 == rspJson.code) {
                            $('#role-datagrid').datagrid('load');
                            $('#role-datagrid').datagrid('unselectAll');//取消选中
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
     * 修改角色
     */
    function updateRole() {
        var checked = $('#role-datagrid').datagrid('getChecked');
        if (checked.length == 1) {
            var dig = $('<div/>').dialog({
                href: 'sys_role/page?name=update',
                width: 470,
                height: 130,
                modal: true,
                title: '修改角色',
                buttons: [
                    {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: function () {
                            parent.$.messager.progress({ // 打开进程对话框
                                text: '正在执行，请稍后....',
                                interval: 100
                            });

                            if (!$('#role-updateform').form('validate')) {
                                parent.$.messager.progress('close');  // 关闭进程对话框
                            }

                            $('#role-updateform').form('submit', {
                                url: 'sys_role/update',
                                type: 'post',
                                dataType: 'json',
                                success: function (ret) {
                                    parent.$.messager.progress('close');
                                    var rspJson = $.parseJSON(ret);
                                    if (0 == rspJson.code) {
                                        $('#role-datagrid').datagrid('load'); // 动态刷新
                                        dig.dialog('close');
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
                    $('#role-updateform').form('load', checked[0]); // 必须在窗体打开之前加载数据
                }
            });
        } else if (checked.length > 1) {
            $.messager.alert('错误提示', '请勾选一项，请勿多选', 'error');
        } else {
            $.messager.alert('错误提示', '请勾选要修改的记录', 'error');
        }
    }
</script>
