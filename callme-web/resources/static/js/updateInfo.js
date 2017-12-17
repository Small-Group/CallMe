$.ready(function () {
    var username=window.opener.username;
    var token=window.opener.token;
    $.ajax({
        url: "user/findUserInfo/"+username,
        dataType: "json",
        type: "GET",
        data: token,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {;
            if (retData.code === 0) {
                 userInfoAssign(retData)
            }else {
                 /*TODO*/
            }
        }
    })
})

/*用户信息赋值*/
function userInfoAssign(retData) {
    var userInfo = retData.data;
    $("#name").val(userInfo.name);
    $("#remark").val(userInfo.remark);
    //userInfo.sex=$("input[name='sex']:checked").val();
    $("#school").val(userInfo.school);
    $("#phone").val(userInfo.phone);
    $("#landLine").val(userInfo.landLine);
    $("#qq").val(userInfo.qq);
    $("#wechat").val(userInfo.wechat);
    $("#company").val(userInfo.company);
    $("#email").val(userInfo.email);
}


/*用户信息修改提交*/
function updateInfoSubmit() {
    var userInfo = new Object();
    userInfo.name=$("#name").val();
    userInfo.remark=$("#remark").val();
    userInfo.sex=$("input[name='sex']:checked").val();
    userInfo.school=$("#school").val();
    userInfo.phone=$("#phone").val();
    userInfo.landLine=$("#landLine").val();
    userInfo.qq=$("#qq").val();
    userInfo.wechat=$("#wechat").val();
    userInfo.company=$("#company").val();
    userInfo.email=$("#email").val();
    var dataJson=JSON.stringify(userInfo);
    $.ajax({
        url: "user/updateUserInfo",
        dataType: "json",
        type: "GET",
        data: dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {;
            if (retData.code === 0) {
                $("#updateSuccessMsg").removeClass("hidden");
                $("#updateSuccessMsg").addClass("show");
            }else {
                $("#updateFailMsg").removeClass("hidden");
                $("#updateFailMsg").addClass("show");
            }
        }
    })

}