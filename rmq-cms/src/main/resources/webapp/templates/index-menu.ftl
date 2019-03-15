<#--<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="cn.techfish.vanke.api.model.po.SysUser" %>
<%@ page import="cn.techfish.vanke.api.model.Constants" %>

<%
    String userId = "";
    SysUser user = (SysUser) session.getAttribute(Constants.SESSION_USER);
    if (null != user) {
        userId = user.getSysUserId();
    }
%>-->
<#assign basePath=request.contextPath + "/" />

<#if Session["session_user"]?exists>
    <#assign userId = Session["session_user"].sysUserId />
</#if>

<script type="text/javascript">
    var curMenu = null, zTree_Menu = null;
    $(function(){
        initWestMenu();
    })
    var westMenuTreeSetting = {
        view: {
            showLine: true,
            selectedMulti: false,
            dblClickExpand: true
        },
        data: {
            key: {
                url: "menuUrl"
            },
            simpleData: {
                enable: true,
                idKey: "resourceId",
                pIdKey: "parentId",
                rootPId: null
            }
        },
        callback: {
            onClick: addTabsToCenter
        }
    };

    function initWestMenu() {
        $.ajax({
            url: 'sys_user/' + '${userId}' + '/menu',
            type: 'post',
            dataType: "text",
            ContentType: "application/json; charset=UTF-8",
            success: function (json) {
                $.fn.zTree.init($("#west_menu_tree"), westMenuTreeSetting, eval('(' + json + ')'));
            },
            error: function () {
                $.messager.alert('提示', '生成目录树失败', 'info');
            }
        });
    }

    // 向中央布局中添加标签页
    function addTabsToCenter(event, treeId, treeNode, clickFlag) {
        if (treeNode.isParent == true) return;
        var centerTabs = $('#center_tabs_layout');
        if (centerTabs.tabs('exists', treeNode.name)) {
            // 选中已打开的窗体
            centerTabs.tabs('select', treeNode.name);
            // 刷新已经打开的窗体
            var selected = centerTabs.tabs('getSelected');
            selected.panel('refresh', treeNode.url);
        } else {
            // 限制打开6个窗口
            var total = centerTabs.tabs('tabs').length;
            if (total == 6) {
                // 关闭最靠左的标签页
                centerTabs.tabs('close', 1);
            }
            centerTabs.tabs('add', {title: treeNode.name, href: treeNode.url, closable: true});
        }
    }
</script>
<div class="easyui-accordion" data-options="fit:false,border:false">
    <ul id="west_menu_tree" class="ztree"></ul>
</div>