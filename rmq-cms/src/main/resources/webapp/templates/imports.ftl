<#assign basePath=request.contextPath + "/" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<!-- EasyUI依赖 -->
<link rel="stylesheet" type="text/css" href="${basePath}external/easyui-1.4.5/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}external/easyui-1.4.5/themes/default/layout.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}external/easyui-1.4.5/themes/icon.css"/>
<script type="text/javascript" src="${basePath}external/easyui-1.4.5/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}external/easyui-1.4.5/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${basePath}external/easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>

<!-- zTree依赖 -->
<link rel="stylesheet" type="text/css" href="${basePath}external/zTree_v3/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" src="${basePath}external/zTree_v3/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${basePath}external/zTree_v3/js/jquery.ztree.excheck-3.5.js"></script>

<!-- 引入WebUploader依赖 -->
<link rel="stylesheet" type="text/css"
      href="${basePath}external/webuploader-0.1.5/webuploader.css">
<script type="text/javascript"
        src="${basePath}external/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="${basePath}js/tyb.webuploader.util.js"></script>

<!-- 引入FancyBox依赖 -->
<link rel="stylesheet" href="${basePath}external/fancyBox-v2.1.5/source/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen"/>
<script type="text/javascript" src="${basePath}external/fancyBox-v2.1.5/source/jquery.fancybox.pack.js?v=2.1.5"></script>

<!-- 自定义样式 -->
<link rel="stylesheet" type="text/css" href="${basePath}css/main.css"/>

<!-- 自定义Js -->
<script type="text/javascript" src="${basePath}js/util.js"></script>
<script type="text/javascript" src="${basePath}js/easyui.validator.js"></script>
<script type="text/javascript" src="${basePath}external/ajaxfileupload.js"></script>

<script type="text/javascript">
    $.ajaxSetup({
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        complete:function(XMLHttpRequest,textStatus){
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
            if(sessionstatus == "timeout"){
                //如果超时就处理 ，指定要跳转的页面
                var currUrl = location.href;
                var loginUrl = currUrl.substring(0, currUrl.lastIndexOf('/')) + "/login";
                location.href = loginUrl;
            }
            if("parsererror" == textStatus){
                var currUrl = location.href;
                var loginUrl = currUrl.substring(0, currUrl.lastIndexOf('/')) + "/login";
                location.href = loginUrl;
            }
        }
    });
</script>

<script>
    /*
     * fancybox 初始化
     *
     */
    $(function () {
        $(".fancybox").fancybox({
            'autoHeight': true,
            'autoWidth': true,
            beforeLoad: function () {
                var src = this.element.find('img').attr('src');
                this.href = src;
            },
            beforeShow: function () {
                var alt = this.element.find('img').attr('alt');
                this.inner.find('img').attr('alt', alt);
                this.title = alt;
            }
        });
    });
</script>

