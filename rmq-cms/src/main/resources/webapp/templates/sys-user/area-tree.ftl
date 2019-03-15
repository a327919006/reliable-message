
<script>
    var areaTreeSetting = {
        check : {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        data : {
            simpleData : {
                enable : true,
                idKey : "areaId",
                pIdKey : "areaParentId",
                rootPId : null
            },
            key : {
                name: "areaName"
            }
        }
    };

    function areaTreeInit(oldAreaId) {
        $.ajax({
            url:'area_info/ztree',
            type:'post',
            dataType:"text",
            ContentType : "application/json; charset=UTF-8",
            success : function(json) {
                $.fn.zTree.init($("#user-areatree"), areaTreeSetting, eval('(' + json + ')'));
                var zTreeObj = $.fn.zTree.getZTreeObj("user-areatree");
                if(null == oldAreaId) {
                    return;
                }
                var node = zTreeObj.getNodeByParam("areaId", oldAreaId, null);
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
                $.messager.alert('提示', '生出地区树失败', 'info');
            }
        });
    }
</script>

<div align="left">
	<div style="width: auto;overflow: auto;height: auto; border: 1px; float: left;" align="left">
		<ul id="user-areatree" class="ztree"></ul>
	</div>
</div>

