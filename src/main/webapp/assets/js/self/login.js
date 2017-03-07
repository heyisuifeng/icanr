$(document).ready(function () {
    login.bindEvent();
    login.init();
});

var login = {
    request: {
        "userName": "",
        "password": ""
    },

    init: function () {
    },

    bindEvent: function () {
        this.event.registerBind();
    },

    event: {
        registerBind: function () {
            $("#signup-box").on("click", "#btn-register", login.register);
        }
    },

    register: function () {
        login.request.userName = $.trim($("#inp-user-name").val());
        login.request.password = $.trim($("#inp-password").val());
        $.ajax({
            url: "/register",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(login.request),
            success: function (result) {
                try {

                } catch (e) {
                    console.error(e);
                } finally {
                    console.log(XMLHttpRequest.responseText);
                    $.loading.close();
                }
            },
            error: function () {
            }
        });
    }
}