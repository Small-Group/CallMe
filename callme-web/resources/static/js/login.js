$(function () {
    $('#login #password').focus(function () {
        $('#owl-login').addClass('password');
    }).blur(function () {
        $('#owl-login').removeClass('password');
    });
});


function loginSubmit() {
    var user = new Object();
    user.userName = $("#userName").val();
    user.passWord = $("#password").val();
    var dataJson = JSON.stringify(user);
    $.ajax({
        url: "user/login",
        dataType: "json",
        type: "POST",
        data: dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                //隐藏登录失败提示
                $("#loginFailMsg").removeClass("show");
                $("#loginFailMsg").addClass("hidden");
                var clique=JSON.stringify(retData.data.clique);
                var userInfo=retData.data.userInfo;
                var userName=userInfo.userName;
                var nickName=userInfo.nickName;
                window.location.href = "clique.html?"+"userName="+userName+"&nickName="+nickName;
            } else if (retData.code === -1) {
                $("#loginFailMsg").removeClass("hidden");
                $("#loginFailMsg").addClass("show");
            }
        }
    })
}

/*function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}*/
