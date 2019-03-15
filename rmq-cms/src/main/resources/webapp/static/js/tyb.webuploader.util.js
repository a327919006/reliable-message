/**
 * 物流管理平台WebUploader工具
 *
 * @author xuerongXue
 * @date 2015-08-12
 * @requires jQuery,WebUploader
 */

// 优化retina, 在retina下这个值是2
var ratio = window.devicePixelRatio || 1,

//缩略图大小
thumbnailWidth = 110 * ratio,
thumbnailHeight = 110 * ratio,

// 允许的图片文件扩展名
acceptImgExtensions = [".png", ".jpg", ".jpeg", ".gif", ".bmp"].join('').replace(/\./g, ',').replace(/^[,]/, '');

/**
 * 获取浏览器的flash版本号
 *
 * @author xuerongXue
 * @return 浏览器的flash版本
 */
function getFlashVersion() {
    var version;
    try {
        version = navigator.plugins['Shockwave Flash'];
        version = version.description;
    } catch (ex1) {
        try {
            version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash').GetVariable('$version');
        } catch (ex2) {
            version = '0.0';
        }
    }
    version = version.match(/\d+/g);
    return parseFloat(version[0]+ '.' + version[1], 10);
}

/**
 * 判断浏览器是否支持图片的base64
 *
 * @author xuerongXue
 * @return 如果浏览器支持图片的base64则返回true，否则返回false
 */
function isSupportBase64() {
    var imgData = new Image();
    var support = true;
    imgData.onload = imgData.onerror = function() {
        if(this.width != 1 || this.height != 1) {
            support = false;
        }
    };
    return support;
}

function isSupportTransition() {
	var style = document.createElement('p').style;
	var ret = 'transition' in style || 'WebkitTransition' in style ||
    	'MozTransition' in style || 'msTransition' in style || 'OTransition' in style;
	return ret;
}


function uploadSingleFile(fileVal, imgFile, fileContainer, filePicker, fileInfo, uploadBtn, url) {
	var uploader = WebUploader.create({		
		pick: { // 指定选择文件的按钮容器
            id: filePicker,
            innerHTML : '点击选择图片',
            multiple: false // 不允许同时选择多个文件
        },
        accept: {
            title: 'Images',
            extensions: acceptImgExtensions,
            mimeTypes: 'image/*'
        }, 
        compress : {        	
        	compressSize: 400, // 图片文件大于400k的才进行压缩
        	noCompressIfLarger: false // 如果发现压缩后文件大小比原来还大，则使用原来图片
        },
        server: url,
        swf: '/js/external/webuploader-0.1.5/Uploader.swf',
	});
	
	uploader.options.fileVal=fileVal; // 上传时对应的文件名
	
	uploader.on('uploadSuccess', function (file, ret) {
		debugger;
		if(ret.code == 0) {
			$(imgFile).val(ret.data);
			$.messager.show({title : "成功提示", msg : "上传成功！"});
		} else {
			$.messager.alert('错误提示', "上传失败！", 'error');
		}		
	});
	
	uploader.on('fileQueued', function(file) {
		debugger;	        	
    	// 创建缩略图
    	// 如果为非图片文件，可以不用调用此方法。
    	// thumbnailWidth x thumbnailHeight 为 100 x 100
    	uploader.makeThumb(file, function(error, src) {	    		
    		if (error) {
    			$img.replaceWith('<span>不能预览</span>');
    			return;
    		}
    		if(isSupportBase64) {                    
                $(fileContainer).attr("src", src);
            } else {
            	$(fileContainer).attr( 'src', src );
            }	    		
    	}, thumbnailWidth, thumbnailHeight);
    	
    	$(fileInfo).html('<p>文件大小：' +  WebUploader.formatSize(file.size) + '</p>');
	});
	
	$(uploadBtn).click(function() {		
		uploader.upload(uploader.getFiles());
    });
}

