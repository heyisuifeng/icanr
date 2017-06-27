<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2017/6/26
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <title>百度ueditor试用</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script>
        var UEDITOR_HOME_URL = "/assets/js/ueditor/";　　//从项目的根目录开始
    </script>
    <script type="text/javascript" charset="utf-8" src="/assets/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="/assets/js/ueditor/ueditor.all.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="/assets/js/ueditor/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" type="text/css" href="/assets/js/ueditor/themes/default/css/ueditor.css"/>

</head>
<body>
    <h1>demo</h1>
    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
    <script type="text/javascript">

        //实例化编辑器
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var ue = UE.getEditor('editor');

        UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
        UE.Editor.prototype.getActionUrl = function(action){
            if(action == '/upload/image'){
                return basePath+'/upload/image';
            }else{
                return this._bkGetActionUrl.call(this, action);
            }
        }
    </script>
</body>
</html>
