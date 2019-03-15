/** create by ChenNan 20160627
 * 用于把easyui的databox转换成只能选择月份的选择框
 **/
function toDateSelector(id){
    $('#' + id).datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                        , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#' + id).datebox('hidePanel')//隐藏日期对象
                        .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0);
            yearIpt.unbind();//解绑年份输入框中任何事件
        },
        formatter: sltDateFormatter,
        parser: sltDateParser
    });
    var p = $('#' + id).datebox('panel'), //日期选择对象
        tds = false, //日期选择对象中月份
        yearIpt = p.find('input.calendar-menu-year'),//年份输入框
        span = p.find('span.calendar-text'); //显示月份层的触发控件
}

function sltDateFormatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    if(m < 10){
        m = '0' + m;
    }
    return y+'-'+m;
}

function sltDateParser(s){
    var t = Date.parse(s);
    if (!isNaN(t)){
        return new Date(t);
    } else {
        return new Date();
    }
}