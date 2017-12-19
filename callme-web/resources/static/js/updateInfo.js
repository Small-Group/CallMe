/*用户信息赋值*/
function userInfoAssign(userInfo) {
    console.log(userInfo);
    $("#name").val(userInfo.name);
    $("#remark").val(userInfo.remark);
    if(userInfo.sex=='male'){
        $("#sexRadio1").attr('checked', 'true');
    }
    if(userInfo.sex=='female'){
        $("#sexRadio2").attr('checked', 'true');
    }
    $("#school").val(userInfo.school);
    $("#phone").val(userInfo.phone);
    $("#landLine").val(userInfo.landLine);
    $("#qq").val(userInfo.qq);
    $("#wechat").val(userInfo.weChat);
    $("#company").val(userInfo.company);
    $("#email").val(userInfo.email);
}


/*用户信息修改提交*/
function updateInfoSubmit() {
    var userName=getUrlParam("userName");
    var nickName=getUrlParam("nickName");
    var userInfo = {};
    userInfo.userName=userName;
    userInfo.nickName=nickName;
    userInfo.name=$("#name").val();
    userInfo.remark=$("#remark").val();
    var sex=$("input[name='sex']:checked").val();
    if(typeof(sex)=="undefined"){
        sex='';
    }
    userInfo.sex=sex;
    userInfo.school=$("#school").val();
    userInfo.phone=$("#phone").val();
    userInfo.landLine=$("#landLine").val();
    userInfo.qq=$("#qq").val();
    userInfo.weChat=$("#wechat").val();
    userInfo.company=$("#company").val();
    userInfo.email=$("#email").val();
    var dataJson=JSON.stringify(userInfo);
    $.ajax({
        url: "user/updateUserInfo",
        dataType: "json",
        type: "POST",
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
