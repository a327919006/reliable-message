/* 获取cookie中的用户名 */
var cookieUser = $.cookie('cookie_user');
var cookiePwd = $.cookie('cookie_pwd');

$(document).ready(function () {

    /* 检测按键按下事件 */
    document.onkeydown = function (e) {
        var ev = document.all ? window.event : e;
        if (ev.keyCode == 13) {
            $("#login-btn").click();
        }
    };

    if (!isUndefined(cookieUser)) {
        $("#username").val(cookieUser);
        $("#password").val(cookiePwd);
        $("input[name='rememberMe']").attr("checked", true);
    }

    $("#yzm-img").click(function () {
        $("#yzm-img").attr("src", "login/captcha?" + new Date().getTime());
    });


    /* 为登录按键绑定事件 */
    $("#login-btn").click(function () {
        var username = $("#username").val();
        var password = $("#password").val();
        var yzm = $("#yzm").val();

        if (isUndefined(username)) {
            alert("请输入用户名");
            return;
        }
        if (isUndefined(password)) {
            alert("请输入密码");
            return;
        }
        if (isUndefined(yzm)) {
            alert("请输入验证码");
            return;
        }

        $.ajax({
            url: 'login/submit',
            data: {
                'username': username,
                'password': password,
                'captcha': yzm
            },
            type: 'post',
            dataType: 'json',
            success: function (jsondata) {
                if(jsondata == null || jsondata.code == 0){
                    var rememberMe = $("input[name='rememberMe']:checked").val();
                    if (!isUndefined(rememberMe)) {
                        $.cookie('cookie_user', username, {expires: 7});
                        $.cookie('cookie_pwd', password, {expires: 7});
                    }
                    window.location.href = "index";
                }else{
                    alert(jsondata.msg);
                    $("#yzm-img").attr("src", "login/captcha?" + new Date().getTime());
                }
            },
            error: function (ret) {
                var rspJson = $.parseJSON(ret.responseText);
                alert(rspJson.msg);
                $("#yzm-img").attr("src", "login/captcha?" + new Date().getTime());
            }
        });
    });
});