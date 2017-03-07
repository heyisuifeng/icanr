/**
 * Created by 1 on 2017/3/7.
 */
$(document).ready(function () {
    userOperator.bindEvent();
    userOperator.init();
})

var userOperator = {
    addUserRequest : {
        "username":"",
        "password":"",
        "confirmPassword":""
    },
    init : function () {
        userOperator.bindEvent();
    },

    bindEvent:function () {
        $("#fm-add-user").on("click", "#btn-add-user", userOperator.addUserBind);
    },

    addUserBind:function () {
        userOperator.buildAddUserRequest();
        $.alert(userOperator.addUserRequest.username);
    },

    buildAddUserRequest:function () {
        userOperator.addUserRequest.username=$("#inp-username").val();
        userOperator.addUserRequest.password=$("#inp-password").val();
        userOperator.addUserRequest.confirmPassword=$("#inp-confirm-password").val();
    }
}
