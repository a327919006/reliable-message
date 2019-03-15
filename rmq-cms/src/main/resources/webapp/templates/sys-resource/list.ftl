
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',title:'查询条件',border:true" style="height: 85px;">
        <#include "search.ftl"/>
    </div>

    <div id="resource-toolbar" class="datagrid-toolbar">
        <table>
            <tr>
                <td><a onclick="createResource();" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'">增加</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="updateResource();" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'">修改</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="deleteResource();" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'">删除</a></td>
                <td><div class="datagrid-btn-separator"></div></td>

                <td><a onclick="$('#resource-datagrid').datagrid('unselectAll');" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:'true'">取消选中</a></td>
                <td><div class="datagrid-btn-separator"></div></td>
            </tr>
        </table>
    </div>

    <div data-options="region:'center', border:true">
        <table id="resource-datagrid" class="easyui-datagrid" title="资源列表"
               data-options="
                   border: false,
                   fit:true,
                   striped: true,
                   url: 'sys_resource/search',
                   method: 'post',
                   pagination: true,
                   isFiled: 'resourceId',
                   fitColumns: true,
                   rownumbers: true,
                   singleSelect: true,
                   pageSize: 20,
                   pageList: [ 10, 20, 30, 40, 50 ],
                   toolbar:'#resource-toolbar'">
            <thead>
                <tr>
                    <th data-options="field: 'resourceId',checkbox: true,width: 100">编号</th>
                    <th data-options="field: 'name',width: 100">名称</th>
                    <th data-options="field: 'type',width: 40,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return '菜单';
                            } else if (value == 1) {
                                return '按钮';
                            } else {
                                return '未知';
                            }
                    }">类型</th>
                    <th data-options="field: 'status',width: 40,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '禁用';
                        } else if (value == 1) {
                            return '启用';
                        } else {
                            return '未知';
                        }
                    }">状态</th>
                    <th data-options="field: 'parentName',width: 100">上级资源</th>
                    <th data-options="field: 'permission',width: 100">权限</th>
                    <th data-options="field: 'url',width: 200">URL</th>
                    <th data-options="field: 'icon',width: 150">资源图标</th>
                    <th data-options="field: 'priority',width: 50">排序</th>
                    <th data-options="field: 'createTime',width: 100">创建时间</th>
                    <th data-options="field: 'updateTime',width: 100">更新时间</th>
                </tr>
            </thead>
       </table>
    </div>
</div>

<script type="text/javascript">

    /**
     * 新增资源
     */
    function createResource() {
        var dialog = $('<div/>').dialog({
            href: 'sys_resource/page?name=create',
            width: 500,
            height: 270,
            modal: true,
            title: '新增资源',
            buttons: [
                {
                    text: '保存', iconCls: 'icon-save',
                    handler: function () {
                        parent.$.messager.progress({ // 打开进程对话框
                            text: '正在执行，请稍后....',
                            interval: 100
                        });
                        if (!$('#resource_createform').form('validate')) {
                            parent.$.messager.progress('close');
                        }

                        $('#resource_createform').form('submit', {
                            url: 'sys_resource/create',
                            success: function (ret) {
                                parent.$.messager.progress('close');
                                var rspJson = $.parseJSON(ret);
                                if (0 == rspJson.code) {
                                    dialog.dialog('close'); // 关闭窗口
                                    var rspJson = $.parseJSON(ret);
                                    $('#resource-datagrid').datagrid('insertRow', { // 头部添加一行
                                        index: 0,
                                        row: rspJson.data
                                    });
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
                    text: '关闭', iconCls: 'icon-cancel',
                    handler: function () {
                        dialog.dialog('close'); // 关闭窗口
                    }
                }
            ],
            onClose: function () {
                //关闭后回收内存
                $(this).dialog('destroy');
            }
        });
    }

    /**
     * 删除
     */
    function deleteResource() {
        var checked = $('#resource-datagrid').datagrid('getChecked');
        if (checked.length > 0) {
            $.messager.confirm('确认', '您是否要删除当前选中的记录？', function (ret) {
                if (!ret) {
                    return;
                }

                var resourceIds = [];
                for (var i = 0; i < checked.length; i++) {
                    resourceIds.push(checked[i].resourceId);
                }
                $.ajax({
                    url: 'sys_resource/delete',
                    data: {
                        resourceIds: resourceIds.join(',')
                    },
                    type: 'post',
                    dataType: 'json',
                    success: function (rspJson) {
                        if (0 == rspJson.code) {
                            $('#resource-datagrid').datagrid('load');
                            $('#resource-datagrid').datagrid('unselectAll');//取消选中
                            $.messager.show({
                                title: '成功提示',
                                msg: rspJson.msg
                            });
                        } else {
                            $.messager.alert('错误提示', rspJson.msg, 'error');
                        }
                    },
                    error: function (ret) {
                        $.messager.alert('错误提示', '删除失败', 'error');
                    }
                });
            });
        } else {
            $.messager.alert('错误提示', '请勾选要删除的记录', 'error');
        }
    }

    /**
     * 更新资源
     */
    function updateResource() {
        var checked = $('#resource-datagrid').datagrid('getChecked');
        if (checked.length == 1) {
            var dialog = $('<div/>').dialog({
                href: 'sys_resource/page?name=update',
                width: 500,
                height: 270,
                modal: true,
                title: '修改资源',
                buttons: [
                    {
                        text: '保存', iconCls: 'icon-save',
                        handler: function () {
                            // 判断上一级菜单是否为自己
                            var resourceId = $('#resource-updateform input[name=resourceId]').val();
                            var parentId = $('#resource-updateform input[name=parentId]').val();
                            if (resourceId == parentId) {
                                $.messager.alert('错误提示', '上一级资源不能为自己', 'error');
                                return;
                            }

                            parent.$.messager.progress({ // 打开进程对话框
                                text: '正在执行，请稍后....',
                                interval: 100
                            });
                            if (!$('#resource-updateform').form('validate')) {
                                parent.$.messager.progress('close');  // 关闭进程对话框
                            }
                            $('#resource-updateform').form('submit', {
                                url: 'sys_resource/update',
                                success: function (ret) {
                                    parent.$.messager.progress('close');
                                    var rspJson = $.parseJSON(ret);
                                    if (0 == rspJson.code) {
                                        dialog.dialog('close');
                                        var grid = $('#resource-datagrid');
                                        grid.datagrid('updateRow', {
                                            index: grid.datagrid('getRowIndex', checked[0]),
                                            row: rspJson.data
                                        });
                                        $.messager.show({
                                            title: '成功提示',
                                            msg: rspJson.msg
                                        });
                                        var zTreeObj = $.fn.zTree.getZTreeObj('westMenuTree');
                                        zTreeObj.reAsyncChildNodes(null, 'refresh');
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
                        text: '关闭', iconCls: 'icon-cancel',
                        handler: function () {
                            dialog.dialog('close'); // 关闭窗口
                        }
                    }
                ],
                onClose: function () {
                    $(this).dialog('destroy'); // 关闭后回收内存
                },
                onLoad: function () {
                    $('#resource-updateform').form('load', checked[0]); // 必须在窗体打开之前加载数据
                }
            });
        } else if (checked.length > 1) {
            $.messager.alert('错误提示', '请勾选一项，请勿多选', 'error');
        } else {
            $.messager.alert('错误提示', '请勾选要修改的记录', 'error');
        }
    }

    /**
     * 选择上一级菜单
     *
     * @param menuId 菜单Id
     * @param menuName 菜单名称
     */
    function selectParentMenu(menuId, menuName) {
        var dialog = $('<div/>').dialog({
            title: '选择菜单',
            href: 'sys_resource/page?name=parent-tree',
            width: 300,
            height: 300,
            modal: true,
            buttons: [
                {
                    text: '确定', iconCls: 'icon-ok',
                    handler: function () {
                        debugger;
                        var treeObj = $.fn.zTree.getZTreeObj("resource-parenttree");
                        var checkedNodes = treeObj.getCheckedNodes(true);
                        var checkedArray = new Array();
                        for (var i = 0; i < checkedNodes.length; i++) {
                            if (!checkedNodes[i].getCheckStatus().half) {
                                checkedArray.push(checkedNodes[i]);
                            }
                        }
                        if (checkedArray.length == 0) {
                            $.messager.alert('错误提示', '请选择上一级菜单!', 'error');
                            return false;
                        } else if (checkedArray.length != 1) {
                            $.messager.alert('错误提示', '只能选择一个菜单!', 'error');
                            return false;
                        } else {
                            menuId.val(checkedArray[0].resourceId);
                            menuName.val(checkedArray[0].name);
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
                menuTreeInit(menuId.val());
            }
        });
    }

    /**
     * 清空上一级菜单
     *
     * @param menuId
     * @param menuName
     */
    function clearParentMenu(menuId, menuName) {
        menuId.val("");
        menuName.val("");
    }
</script>
