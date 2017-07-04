<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<body>
<h2>Hello World!</h2>
<div>
    <form action="/login" method="post">
        <input name="name" type="text"/>
        <input name="password" type="password"/>
        <input type="submit" value="login">
    </form>
</div>
<div>
    <form id="fm-add-user">
        <input type="text" maxlength="20" id="inp-username"/>
        <input type="password" maxlength="16" id="inp-password"/>
        <input type="password" maxlength="16" id="inp-confirm-password"/>
        <input type="button" value="add" id="btn-add-user"/>
    </form>
</div>
</body>
<script src="${ctx}/assets/js/jquery-1.10.2.min.js"></script>
<script src="${ctx}/assets/js/self/user.js"></script>
</html>
