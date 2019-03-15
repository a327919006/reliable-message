var currRow;
var currParent;

serializeObject = function(form) {
    var o = {};
    $.each(form.serializeArray(), function(index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

function changePassword() {
    var changePasswordDialog = $('<div/>').dialog({
        title: '修改密码',
        width: 330,
        height: 210,
        modal: true,
        href: 'views/changePassword.jsp',
        buttons: [
            {
                text: '修改',
                iconCls: 'icon-ok',
                handler: function () {
                    parent.$.messager.progress({
                        text: '正在执行，请稍后....',
                        interval: 100
                    });
                    if (!$('#changePasswordForm').form('validate')) {
                        parent.$.messager.progress('close');
                    }
                    $('#changePasswordForm').form('submit', {
                        url: 'manager/user/password/change',
                        success: function (ret) {
                            parent.$.messager.progress('close');
                            var httpRsp = $.parseJSON(ret);
                            changePasswordDialog.dialog('close');
                            $.messager.show({
                                title: '成功提示',
                                msg: httpRsp.msg
                            });
                        },
                        error: function (ret) {
                            $.messager.alert('错误提示', httpRsp.msg, 'error');
                        }
                    });
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    changePasswordDialog.dialog('close');
                }
            }
        ],
        onClose: function () {
            $(this).dialog('destroy');
        }
    });
}

/* add by chennan 20160616 */
// 设置img图片路径
function setImgSrc(imgId, imgSrc){
    if(imgSrc != null && imgSrc.length > 0){
        $('#' + imgId).attr("src", imgSrc);
    }else{
        $('#' + imgId).attr("src", "resources/images/none.jpg");
    }
}

// 设置img图片路径
function setNginxImgSrc(imgId, imgSrc){
    if(imgSrc != null && imgSrc.length > 0){
        $('#' + imgId).attr("src", nginxImageUrl + imgSrc);
    }else{
        $('#' + imgId).attr("src", "resources/images/none.jpg");
    }
}

/**
 * 初始化calendar控件为月份选择
 * @param id
 */
function initMonthDateBox(id) {
    $(id).datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $(id).datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function (d) {
            var month = d.getMonth() + 1;
            if (month < 10) {
                return d.getFullYear() + '-0' + (d.getMonth() + 1);
            } else {
                return d.getFullYear() + '-' + (d.getMonth() + 1);
            }
        }
    });
    var p = $(id).datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        span = p.find('span.calendar-text'); //显示月份层的触发控件
}


/**
 * 图片上传
 * @param url 提交到的url
 * @param imgType 照片类型
 * @param imgFileId 用于上传的图片ID (<input id="imgFileId" type="file">)
 * @param previewImgId 用于预览图片的IMG (<img id="previewImgId" src="">)
 * @param previewImgStorePathId 用于保存预览图片URL的组件ID (<input id="previewImgUrlId">) 用servlet实现读取ftp图片信息
 * @param previewImgNameId 用于保存预览图片名字 (<input id="previewImgNameId">)
 * @param hostPath 网站根路径
 */
function uploadImageNew(url, imgType, imgFileId, previewImgId, previewImgStorePathId, previewImgNameId, hostPath) {

    var filemaxsize = 200;//200k

    if($('#' + imgFileId)[0].files[0] == null){
        alert("请先选择文件");
        return false;
    }

    //判断是否为图片
    var fileSuffix = "jpeg,jpg,png,gif";
    var value = $("#" + imgFileId).val();
    var suffix = value.substr(value.lastIndexOf(".") + 1, value.length).toLowerCase();
    if (fileSuffix.indexOf(suffix) < 0) {
        alert("图片格式不正确");
        return false;
    }
    var fsize = $('#' + imgFileId)[0].files[0].size;
    var fsiztValue = fsize / 1024 / 1024;
    if (fsiztValue > 10) {
        alert("上传图片大小不能超过10M");
        fsiztValue = 0;
        return false;
    }


    //AJAX上传图片到服务器临时目录
    $.ajaxFileUpload({
        url: url,
        secureuri: false,
        data: {
            type: imgType
        },
        type: 'post',
        fileElementId: imgFileId,  //文件上传的id属性
        dataType: "json",  //返回值类型为json
        success: function (data, status) {//服务器成功响应处理函数
            if (data.code == 0) {
                $("#" + previewImgId).attr("src", data.data.imageUrl);
                $("#" + previewImgStorePathId).val(data.data.path);
            } else {
                alert(data.msg);
            }
        },
        error: function (data, status, e) { //服务器响应失败处理函数
            alert("服务器繁忙，请稍后再试！");
        }
    });
}

// 获取编辑器中HTML内容
function getEditorHTMLContents(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
    return(oEditor.GetXHTML(true));
}

// 获取编辑器中文字内容
function getEditorTextContent(EditorName) {
    var oEditor = FCKeditorAPI.GetInstance(EditorName);
//        return(oEditor.EditorDocument.body.innerText); //网上都是这样，但是这个属性却取不到值
    return(oEditor.EditorDocument.body.innerHTML);//这样可以获得内容，并且带有样式标签
//        return(oEditor.EditorDocument.body.textContent);//这样可以获得内容，不带样式标签
}

// 获取地区列表
function getAreaInfoList(areaSltId, areaLevel, areaParentId){
    $.ajax({
        url:"area_info/getList",
        type:"post",
        data:{
            //传值，还是JSON数据
            areaLevel : areaLevel,
            areaParentId : areaParentId
        },
        //重要，如果写jsonp会报转换错误，此处不写都可以
        dataType:"json",
        success:function(data){
            $("#" + areaSltId).combobox("loadData", data);
        },
        //异常处理
        error:function(xml, text, msg){
            error.apply(this, arguments);
        }
    });
}