/**
 * @功能描述：easyui-validatebox自定义验证函数集合
 * @作者：薛学榕
 * @创建日期：2015-07-17
 */
$.extend($.fn.validatebox.defaults.rules, {
    mobileTel : { // 验证手机号码
        validator : function(value) {
            var reg = /^1\d{10}$/;
            return reg.test(value);
        },
        message : '手机号码格式不正确'
    },
    datetime : { // 验证 YYYY-MM-DD hh:mm:ss
        validator : function(value) {
            if(value.length!=0){
                var reg = /^(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})$/;
                var r = value.match(reg);
                if(r == null){
                    return false;
                }
                return true;
            }
        },
        message : '时间格式不正确'
    },
    IP : { // 验证IP地址
        validator : function(value) {
            var reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
            return reg.test(value);
        },
        message : 'IP地址格式不正确'
    },idcard : {// 验证身份证
        validator : function(value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message : '身份证号码格式不正确'
    }
});
