$().ready(function() {
// 在键盘按下并释放及提交后验证提交表单

    $("#registerForm").validate({
        submitHandler:function(form){
            registerSubmit();
        },
        rules: {
            userName: {
                required:true,
                //isUserNameExist:true
                remote: {
                    url: "user/check/",     //后台处理程序
                    type: "get",               //数据发送方式
                    dataType: "json",           //接受数据格式
                    data: {                     //要传递的数据
                        userName: function() {
                            return $("#userName").val();
                        }
                    },
                    dataFilter: function(data, type) {
                        var retData=JSON.parse(data);
                        if (retData.code == 0) {
                            return true;
                        } else if (retData.code == -1) {
                            return false;
                        }

                    }
                }
            },
            nickName:"required",
            passWord: {
                required: true,
                minlength: 5
            },
            confirmPassWord: {
                required: true,
                minlength: 5,
                equalTo: "#passWord"
            },
        },
        messages: {
            userName: {
                required: "请输入用户名",
                remote:"用户名已存在",
                minlength: "用户名必需由两个字母组成"
            },
            nickName: {
                required: "请输入用户名",
                minlength: "用户名必需由两个字母组成"
            },
            passWord: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母"
            },
            confirmPassWord: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字母",
                equalTo: "两次密码输入不一致"
            }
        },
        errorPlacement: function(error, element) {
            console.debug(error);
            console.debug(element);
            error.appendTo(element.parent());
        }
    })
});

/**
jQuery.validator.addMethod("isUserNameExist", function(value, element) {
    console.debug("username:: "+value);
    var userNameExist;
    $.ajax({
        url: "user/check/"+value,
        dataType: "json",
        // async:false,
        type:"GET",
        success: function (retData) {
            if (retData.code == 0) {
                // userNameExist=false;
                return true;
            } else if (retData.code == -1) {
                //userNameExist=true;
                return false;
            }
            console.debug(userNameExist);
        }
    });
    if(userNameExist){
        console.debug("存在")
        return false;
    }else {
        console.debug("不存在")
        return true;
    }
}, "用户名已存在");
 **/

function registerSubmit() {
    var user = new Object();
    user.userName = $("#userName").val();
    user.passWord = $("#passWord").val();
    dataJson = JSON.stringify(user);
    $.ajax({
        url: "user/register",
        dataType: "json",
        type:"POST",
        data: dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                window.location.href = "login.html";
            } else {
                $("#login_btn").after("注册失败，请稍后重试");
            }
        }
    })
}
function loginSubmit() {
    var user = new Object();
    user.userName = $("#userName").val();
    user.passWord = $("#passWord").val();
    user.nickName = $("#nickName").val();
    user.school = $("#school").val();
    user.sex = $("#sex").val();
    user.phone = $("#phone").val();
    user.landLine = $("#landLine").val();
    user.qq = $("#qq").val();
    user.wechat = $("#wechat").val();
    user.company = $("#company").val();
    user.email = $("#email").val();
    dataJson = JSON.stringify(user);
    $.ajax({
        url: "user/register",
        dataType: "json",
        type:"POST",
        data: dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                window.location.href = "login.html";
            } else if (retData.code === -1) {
                console.debug("ret: "+retData.code);
                $("#login_btn").after("注册失败，请稍后重试");
            }
        }


    })

}