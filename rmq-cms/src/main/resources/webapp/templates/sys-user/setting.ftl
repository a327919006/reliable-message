
<script type="text/javascript">

    var rsp=null;
    /**
     * 初始化用户角色树
     * @param userId 用户唯一标识
     */
    function initUserRoleTree(userId) {
        parent.$.messager.progress({
            text: '正在执行，请稍后....',
            interval: 100
        });

        var userRoleTreeSetting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "roleId",
                    rootPId: null
                },
                key : {
                    name: "roleName"
                }
            }
        };

        $.ajax({
            url: "sys_role/ztree",
            type: "post",
            dataType: "text",
            ContentType: "application/json; charset=UTF-8",
            success: function (data) {
                parent.$.messager.progress('close');
                $.fn.zTree.init($("#user-roletree"), userRoleTreeSetting, eval('(' + data + ')'));
                checkUserRoleTree(userId);
            },
            error: function () {
                parent.$.messager.progress('close');
                $.messager.alert('提示', '生成用户角色树失败', 'error');
            }
        });
    }

    /**
     * 选中树
     */
    function checkUserRoleTree(userId) {
        if (null == userId || '' == userId) {
            return;
        }
        parent.$.messager.progress({
            text: '正在执行，请稍后....',
            interval: 100
        });
        var zTreeObj = $.fn.zTree.getZTreeObj("user-roletree");
        $.ajax({
            url: 'sys_user/' + userId + '/roles',
            type: 'post',
            dataType: "text",
            ContentType: "application/json; charset=UTF-8",
            success: function (ret) {
                parent.$.messager.progress('close');
                 rsp = $.parseJSON(ret);
                for (var i = 0; i < rsp.length; i++) {
                    var node = zTreeObj.getNodeByParam("roleId", rsp[i].roleId, null);
                    if (node.isParent == false) {
                        zTreeObj.checkNode(node, true, true);
                    } else {
                        zTreeObj.expandNode(node,true,false,true);
                    }
                }
            },
            error: function () {
                parent.$.messager.progress('close');
                $.messager.alert('提示', '获取用户角色失败', 'error');
            }
        });
    }

    /**
     * 保存用户角色分配
     */
    function saveUserRoleAllot() {
        debugger;
        var selectedRow = $('#user-datagrid').datagrid('getSelected');
        if (!selectedRow) {
            $.messager.alert('错误提示', '请先选择左侧的角色信息', 'error');
            return;
        }
        var zTreeObj = parent.$.fn.zTree.getZTreeObj("user-roletree");
        var checkedNodes = zTreeObj.getCheckedNodes(true);
        var checkArray = new Array();

        parent.$.messager.progress({
            text: '正在执行，请稍后....',
            interval: 100
        });

        for (var i = 0; i < checkedNodes.length; i++) {
            checkArray.push(checkedNodes[i].roleId);
        }
        var checkedList = checkArray.join(",");

        $.ajax({
            url: 'sys_user/' + selectedRow.sysUserId + '/role/allot',
            data: {
                roleIds: checkedList
            },
            type: 'post',
            dataType: 'json',
            success: function (rspJson) {
                parent.$.messager.progress('close');
                if (0 == rspJson.code) {
                    zTreeObj.destroy();
                    initUserRoleTree(selectedRow.sysUserId);
                    $.messager.show({
                        title: '成功提示',
                        msg: rspJson.msg
                    });
                } else {
                    $.messager.alert('错误提示', rspJson.msg, 'error');
                }
            },
            error: function() {
                parent.$.messager.progress('close');
                $.messager.alert('错误提示', '分配失败', 'error');
            }
        });
    }

    /**
     * 重置用户角色分配
     */
    function redoUserRoleAllot() {
        // 获取选中的行
        var selectedRow = $('#user-datagrid').datagrid('getSelected');
        if (!selectedRow) {
            $.messager.alert('错误提示', '请先选择左侧的用户信息', 'error');
            return;
        }
        // 重新加载
        var zTreeObj = $.fn.zTree.getZTreeObj("user-roletree");

        for (var i = 0; i < rsp.length; i++) {
            var node = zTreeObj.getNodeByParam("roleId", rsp[i].roleId, null);
                zTreeObj.checkNode(node, false, true);
        }
    }

    $(document).ready(function(){
        // 初始化用户角色树
        initUserRoleTree();
    });
</script>


<div class="easyui-layout" data-options="fit:true, border:true" style="width: 300px">
    <div data-options="region:'north', border:true" style="height: 44px;">
        <div style="text-align:center;margin-top: 7px;margin-bottom: 5px">
            <a onclick="saveUserRoleAllot();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
            <a onclick="redoUserRoleAllot();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="margin-left: 10px">重置</a>
        </div>
    </div>
    <div data-options="region:'center',border:true">
        <ul id="user-roletree" class="ztree"></ul>
    </div>
</div>




