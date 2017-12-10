function loginSubmit() {
    var user = new Object();
    user.userName = $("#userName").val();
    user.passWord = $("#password").val();
    dataJson = JSON.stringify(user);
    $.ajax({
        url: "user/login",
        dataType: "json",
        type: "POST",
        data: dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                //隐藏登录失败提示
                $("#login_fail").css("display","none");
                //设置cookie
                var token=retData.data.token;
                setCookie("circle_token",token,30);
                window.location.href = "index.html";
            } else if (retData.code === -1) {
                $("#login_fail").css("display","block");
            }
        }

    })
}

//设置cookie及过期时间
function setCookie(c_name, value, expiredays) {
    var exdate = new Date()
    exdate.setDate(exdate.getDate() + expiredays)
    document.cookie = c_name + "=" + escape(value) +
        ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString())
}
