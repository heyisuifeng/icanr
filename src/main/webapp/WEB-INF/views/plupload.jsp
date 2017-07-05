<%--
  Created by IntelliJ IDEA.
  User: yecanyi
  Date: 2017/7/5
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>plupload插件</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/js/plupload/jquery.plupload.queue/css/jquery.plupload.queue.css"/>

    <link rel="stylesheet" type="text/css" href="${ctx}/assets/js/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/js/plupload/jquery.ui.plupload/css/jquery-ui.min.css"/>
</head>
<body>
    <div>
        <div id="uploader"></div>
    </div>
</body>

<script src="${ctx}/assets/js/jquery-1.10.2.min.js" type="text/javascript" charset="utf-8"></script>
<%--第一种方式需要引入的js--%>
<%--<script src="${ctx}/assets/js/plupload/plupload.full.min.js"></script>
<script src="${ctx}/assets/js/plupload/jquery.plupload.queue/jquery.plupload.queue.js"></script>--%>

<%--第二种方式需要引入的js--%>
<script src="${ctx}/assets/js/plupload/jquery-ui.min.js"></script>
<script src="${ctx}/assets/js/plupload/plupload.full.min.js"></script>
<script src="${ctx}/assets/js/plupload/jquery.ui.plupload/jquery.ui.plupload.js"></script>

<script src="${ctx}/assets/js/plupload/i18n/zh_CN.js"></script>
<script>
    $(function() {
        //第一种实现方式，用pluploadQueue初始化
        /*var uploader = $("#uploader").pluploadQueue({
            // General settings
            runtimes: 'html5,flash,silverlight,html4',

            //这里可以配置上传路径，但是这里没有办法传文件大小等其他参数，
            // 所以我们可以在文件添加完之后，在BeforeUpload方法里重写路径，将参数加在路径后面
            url : "/plupload/resource",

            // Maximum file size
            max_file_size: '500mb',

            chunk_size: '2mb',

            // Resize images on clientside if we can
            resize: {
                quality: 90,
                crop: false // crop to exact dimensions
            },

            // Specify what files to browse for
            filters: {
                /!*mime_types:[
                 {title: "Image files", extensions: "jpg,jpeg,gif,png"},
                 {title: "Music files", extensions: "mp3,wav"},
                 {title: "Vedio files", extensions: "mp4,mkv,avi"},
                 {title: "Zip files", extensions: "zip"}
                 ],*!/

                prevent_duplicates : true, //不允许选取重复文件
            },

            // Rename files by clicking on their titles
            //rename: true,

            // Sort files
            sortable: true,

            // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
            dragdrop: true,

            // Views to activate
            views: {
                list: true,
                thumbs: true, // Show thumbs
                active: 'thumbs'
            },

            // Flash settings
            flash_swf_url: 'js/Moxie.swf',

            // Silverlight settings
            silverlight_xap_url: 'js/Moxie.xap',

            init : {
                Browse : function () {
                    //开始文件选择窗口前可以先做些判断
                    /!*var type = $("#sel-resource-type").val();
                    var month = $("#sel-month").val();
                    if ("" == type || "" == month){
                        dsDialogBox("请选择文件类型和月份！","info.png");
                        return false;
                    }*!/
                },

                FilesAdded : function (up,files) {
                    //添加文件完文件后可以做些前期的逻辑处理，比如文件的类型什么的
                    /!*var type = $("#sel-resource-type").val();
                    if (type == 1){
                        //判断是否为音频格式，不是移出队列
                        var needRemove = [];
                        for(var i=0; i<up.files.length; i++){
                            if ((up.files[i].type.indexOf("audio") == -1)){
                                //alert("文件格式错误,将自动为您移除");
                                dsDialogBox("文件格式错误,将自动为您移除！","info.png");
                                //将不符合的类型放入数组
                                needRemove.push(up.files[i]);
                            }
                        }
                        //判断是否有需要移除的类型文件
                        if (needRemove.length>0){
                            for (var j=0;j<needRemove.length;j++){
                                up.removeFile(needRemove[j]);
                            }
                        }
                    }else if (type == 2){
                        //判断是否为视频格式
                        var needRemove = [];
                        for(var i=0; i<up.files.length; i++){
                            if ((up.files[i].type.indexOf("video") == -1)){
                                //alert("文件格式错误,将自动为您移除");
                                dsDialogBox("文件格式错误,将自动为您移除！","info.png");
                                //将不符合的类型放入数组
                                needRemove.push(up.files[i]);
                            }
                        }
                        //判断是否有需要移除的类型文件
                        if (needRemove.length>0){
                            for (var j=0;j<needRemove.length;j++){
                                up.removeFile(needRemove[j]);
                            }
                        }
                    }else if (type == 3 || type == 4){
                        //判断是否为图片格式
                        var needRemove = [];
                        for(var i=0; i<up.files.length; i++){
                            if ((up.files[i].type.indexOf("image") == -1)){
                                //alert("文件格式错误,将自动为您移除");
                                dsDialogBox("文件格式错误,将自动为您移除！","info.png");
                                //将不符合的类型放入数组
                                needRemove.push(up.files[i]);
                            }
                        }
                        //判断是否有需要移除的类型文件
                        if (needRemove.length>0){
                            for (var j=0;j<needRemove.length;j++){
                                up.removeFile(needRemove[j]);
                            }
                        }
                    }*!/
                },

                BeforeUpload : function (up,file) {
                    //点击开始上传的时候，可以重写路径等其他操作，比如上传的路径修改自己想要的
                    /!*if (up.files.length>0){
                        var rename_url = "/plupload/resource?childId=" + $(".courseName").attr("id") + "&type=" + $("#sel-resource-type").val() + "&month=" + $("#sel-month").val()+"&fileSize="+file.size;
                        up.settings.url = rename_url;
                    }*!/
                    //up.settings.url = "/plupload/resource"
                },

                UploadComplete : function () {
                    //上传成功后的逻辑
                    /!*dsDialogBox("上传成功！","success.png");
                    setTimeout(function () {
                        window.location.href = basePath + "/index/upload/resource/" + $(".courseName").attr("id");
                    },2000);*!/
                },
            }
        });*/

        //第二种实现方式，可以预览文件
        $("#uploader").plupload({
            // General settings
            runtimes : 'html5,flash,silverlight,html4',
            url : "/plupload/resource",

            // Maximum file size
            max_file_size : '2mb',

            chunk_size: '1mb',

            // Resize images on clientside if we can
            resize : {
                width : 200,
                height : 200,
                quality : 90,
                crop: true // crop to exact dimensions
            },

            // Specify what files to browse for
            filters : [
                {title : "Image files", extensions : "jpg,gif,png"},
                {title : "Zip files", extensions : "zip,avi"}
            ],

            // Rename files by clicking on their titles
            rename: true,

            // Sort files
            sortable: true,

            // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
            dragdrop: true,

            // Views to activate
            views: {
                list: true,
                thumbs: true, // Show thumbs
                active: 'thumbs'
            },

            // Flash settings
            flash_swf_url : '/plupload/js/Moxie.swf',

            // Silverlight settings
            silverlight_xap_url : '/plupload/js/Moxie.xap'
        });
    });
</script>
</html>
