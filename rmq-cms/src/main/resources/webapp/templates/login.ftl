<#assign basePath=request.contextPath + "/" />

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type"  />
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" charset="utf-8">
    <title>CMS</title>
    <link rel="stylesheet" type="text/css" href="${basePath}css/base.css">
    <link rel="stylesheet" type="text/css" href="${basePath}css/login.css">
    <script type="text/javascript" src="${basePath}js/jquery-1.8.0.min.js"></script>
    <script src="${basePath}external/jquery.cookie.js"></script>
    <script src="${basePath}external/jquery.base64.js"></script>
    <script src="${basePath}js/utils.js"></script>
    <script src="${basePath}js/login.js"></script>
</head>

<body class="login-bg">
<script language="JavaScript">
    if ($("#index_center").attr("class") != null) {
        var currUrl = location.href;
        var loginUrl = currUrl.substring(0, currUrl.lastIndexOf('/')) + "/login";
        location.href = loginUrl;
    }
</script>
<div class="header">
    <#--<p class="page-logo"></p>-->
</div>

<div class="login-block fn-clear">
    <div class="login-box fn-right">
        <p class="logo">CMS</p>
        <div class="login-form">
            <div class="gray-bar fn-clear">
                <label >账户</label>
                <input class="username" type="text" id="username" placeholder="请输入你的账户">
            </div>
            <div class="gray-bar fn-clear">
                <label >密码</label>
                <input class="password" type="password" id="password" placeholder="请输入你的密码">
            </div>
            <div class="gray-bar fn-clear">
                <label >验证码</label>
                <input class="code" type="text" id="yzm" placeholder="请输入你的验证码">
                <img class="yzm-img" src="login/captcha" id="yzm-img">
            </div>
            <button class="common-button" id="login-btn">登录</button>
        </div>
    </div>
</div>

<div class="bottom-text">
    xxxx　版权所有
</div>
</body>
</html>