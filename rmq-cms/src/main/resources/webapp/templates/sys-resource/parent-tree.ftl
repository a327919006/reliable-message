
<script>
    var menuTreeSetting = {
        check : {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        data : {
            key: {
                url:"menuUrl"
            },
            simpleData : {
                enable : true,
                idKey : "resourceId",
                pIdKey : "parentId",
                rootPId : null
            }
        }
    };

    function menuTreeInit(oldMenuId) {
        $.ajax({
            url:'sys_resource/menu',
            type:'post',
            dataType:"text",
            ContentType : "application/json; charset=UTF-8",
            success : function(json) {
                debugger;
                $.fn.zTree.init($("#resource-parenttree"), menuTreeSetting, eval('(' + json + ')'));
                var zTreeObj = $.fn.zTree.getZTreeObj("resource-parenttree");
                if(null == oldMenuId) {
                    return;
                }
                var node = zTreeObj.getNodeByParam("resourceId", oldMenuId, null);
                zTreeObj.checkNode(node, true, true);
                var pNode = node.getParentNode();
                if (null != pNode) {
                    zTreeObj.expandNode(pNode,true,false,true);
                }
                if (node.isParent == true) {
                    zTreeObj.expandNode(node,true,false,true);
                }
            },
            error : function() {
                $.messager.alert('提示', '生成目录树失败', 'info');
            }
        });
    }
</script>

<div align="left">
	<div style="width: auto;overflow: auto;height: auto; border: 1px; float: left;" align="left">
		<ul id="resource-parenttree" class="ztree"></ul>
	</div>
</div>

