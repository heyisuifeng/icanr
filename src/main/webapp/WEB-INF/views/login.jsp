<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 2017/3/1
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
login
<input type="button" id="xml-button">
</body>
<script src="/assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    $("#xml-button").click(function () {
        var xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><book><id>1</id><name>疯狂Java讲义</name><author>李刚</author></book>";
        $.ajax("/sendxml",// 发送请求的URL字符串。
            {
                type : "POST", //  请求方式 POST或GET
                contentType:"application/xml;charset=utf-8", //  发送信息至服务器时的内容编码类型
                // 发送到服务器的数据。
                data: xmlData,
                async:  true , // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            });
    });
</script>
</html>
