
<SCRIPT type="text/javascript">

    var rspJson=null;
    initRoleResourceTree(); // 初始化树

    var roleResourceTreeSetting = {
        check: {
            enable: true
        },
        data: {
            key: {
                url: "resourceUrl"
            },
            simpleData: {
                enable: true,
                idKey: "resourceId",
                pIdKey: "parentId",
                rootPId: null
            }
        }
    };

    /**
     * 初始树
     *
     * @param roleId 角色唯一标识
     */
    function initRoleResourceTree(roleId) {
        parent.$.messager.progress({
            text: '正在执行，请稍后....',
            interval: 100
        });

        $.ajax({
            url: "sys_resource/ztree",
            type: 'post',
            dataType: "text",
            ContentType: "application/json; charset=UTF-8",
            success: function (ret) {
                parent.$.messager.progress('close');
                $.fn.zTree.init($("#role-resourcetree"), roleResourceTreeSetting, eval('(' + ret + ')'));
                if (undefined != roleId && roleId != '') {
                    checkRoleResourceTree(roleId);
                }
            },
            error: function () {
                parent.$.messager.progress('close');
                $.messager.alert('提示', '生出目录树失败', 'error');
            }
        });
    }

    // 在初始化后的树中勾选对已角色拥有的资源
    function checkRoleResourceTree(roleId) {
        var treeObj = $.fn.zTree.getZTreeObj("role-resourcetree");
        $.ajax({
            url: 'sys_role/' + roleId + '/resources',
            type: 'post',
            dataType: "text",
            ContentType: "application/json; charset=UTF-8",
            success: function (ret) {
                rspJson = $.parseJSON(ret);
                for (var i = 0; i < rspJson.length; i++) {
                    var node = treeObj.getNodeByParam("resourceId", rspJson[i].resourceId, null);
                    if (node.isParent == false) {
                        treeObj.checkNode(node, true, true);
                    } else {
                        treeObj.expandNode(node,true,false,true);
                    }
                }
            },
            error: function () {
                $.messager.alert('提示', '生成资源树失败', 'error');
            }
        });
    }

    // 授权功能
    function saveRoleResourceAllot() {
        var selected = $('#role-datagrid').datagrid('getSelected');
        if (!selected) {
            $.messager.alert('错误提示', '请先选择左侧的角色信息', 'error');
            return;
        }
        parent.$.messager.progress({
            text: '正在执行，请稍后....',
            interval: 100
        });

        var treeObj = parent.$.fn.zTree.getZTreeObj("role-resourcetree");
        var nodes = treeObj.getCheckedNodes(true);
        var array = new Array();
        for (var i = 0; i < nodes.length; i++) {
            array.push(nodes[i].resourceId);
        }
        var checked = array.join(",");

        $.ajax({
            url: 'sys_role/' + selected.roleId + '/resources/allot',
            data: {
                resourceIds: checked
            },
            type: 'post',
            dataType: "json",
            success: function (rspJson) {
                parent.$.messager.progress('close');
                if (0 == rspJson.code) {
                    // 重新加载
                    var zTreeObj = $.fn.zTree.getZTreeObj("role-resourcetree");
                    zTreeObj.destroy();
                    // 初始化树
                    initRoleResourceTree(selected.roleId);
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
                $.messager.alert('错误提示', '分配失败', 'error');
            }
        });
    }

    // 重做角色的资源分配
    function redoRoleResourceAllot() {
        var selected = $('#role-datagrid').datagrid('getSelected');
        if (!selected) {
            $.messager.alert('错误提示', '请先选择左边的角色信息', 'error');
            return;
        }
//        // 重新加载
        var zTreeObj = $.fn.zTree.getZTreeObj("role-resourcetree");
//        zTreeObj.destroy();
//        // 初始化树
//        initRoleResourceTree(selected.roleId);
        for (var i = 0; i < rspJson.length; i++) {
            var node = zTreeObj.getNodeByParam("resourceId", rspJson[i].resourceId, null);
                zTreeObj.checkNode(node, false, true);
        }
    }
</SCRIPT>
<div class="easyui-layout" data-options="fit:true,border:true" style="width: 300px">
    <div data-options="region:'north',border:true" style="height: 44px;">
        <div style="text-align:center;margin-top: 7px;margin-bottom: 5px">
            <a onclick="saveRoleResourceAllot();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
            <a onclick="redoRoleResourceAllot();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="margin-left: 10px">重置</a>
        </div>
    </div>
    <div data-options="region:'center',border:true">
        <ul id="role-resourcetree" class="ztree"></ul>
    </div>
</div>
</div>




