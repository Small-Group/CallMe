$().ready(function () {
    //$("#header").load("header.html");
    //checkLogin();
    userName=getUrlParam("userName");
    nickName = getUrlParam("nickName");
    setProfile();
    getCliqueList();
})

var userName;
var nickName;
/*登录校验*/
//TODO
/*失效，暂时不考虑校验*/
/*function checkLogin() {
    var userName = getUrlParam("userName");
    var nickName = getUrlParam("nickName");
    if (token !== null && token !== "" && userName !== null && userName !== "") {
        $.ajax({
            url: "user/findUserInfo/" + userName,
            dataType: "json",
            type: "GET",
            headers: {
                token: token
            },
            contentType: "application/json; charset=utf-8",
            success: function (retData) {
                if (retData.code === 0) {
                    $("#unLogin").addClass("hidden");
                    $("#register").addClass("hidden");
                    console.debug(retData);
                    $("#userDiv").append("<li><a href='user.html'>" + retData.data.nickName+'，欢迎您！'+ "</a><li>");
                    $("#userDiv").append("<li><a href='index.html'>登出</a>")
                }
            }
        })

    }
}*/

/*设置登录信息*/
function setProfile() {
    if(nickName!==null&&nickName!==''){
        $("#userName").append(nickName+',欢迎您');
    }
}

/*获取url后的请求参数*/
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r !== null) return decodeURI(r[2]);
    return null; //返回参数值
}


function getCliqueList() {
    $.ajax({
        url: "user/findCliqueList/"+userName,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                var cliqueList=retData.data.cliqueList;
                for (var i=0;i<cliqueList.length;i++){
                    var clique=cliqueList[i];
                    var cliqueName=clique.name;
                    var cliqueSerialNum=clique.serialNum;
                    $("#createClique_btn").append('<div class="single-menu">'+
                        '<h2><a title="" href="javascript:void(0);" onclick="loadCliqueUsersPage(\''+cliqueSerialNum+'\',this)"><i class="fa fa-heart-o"></i><span>'+cliqueName+'</span></a></h2>\n' +
                        '</div>')
                    $("#createNum").text(retData.data.countJoin);
                    $("#joinNum").text(retData.data.countCreate);

                }
            }
        }
    })

}

/*加载圈子页面*/
function loadCliqueUsersPage(serialNum,ele) {
    $("div.single-menu h2").css("background","none repeat scroll 0 0 #383c42");
    $(ele).parent().css("background","#6a94ff");
    $.ajax({
        url: "user/findUserInfoList/"+serialNum,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
               var userInfo=retData.data;
                $("#cliqueContent").empty();
                $("#cliqueContent").append('<div class="row">\n' +
                    '    <button style="margin-left: 3%" onclick="quitClique(\''+serialNum+'\')" class="btn btn-warning">退出圈子</button>\n' +
                    '</div>');
               for(var i=0;i<userInfo.length;i++){
                   $("#cliqueContent").append('<div class="col-md-2">\n' +
                       '                <div class="widget-area no-padding">\n' +
                       '                    <div class="my-profile-widget">\n' +
                       '                        <div class="profile-widget-head">\n' +
                       '                            <h3>'+userInfo[i].nickName+'</h3>\n' +
                       '                            <span><a href="javascript:void(0)" onclick="loadUserInfo()"><img style="width: 70px;" alt="" src="images/resource/me.jpg"></a></span>\n' +
                       '                        </div>\n' +
                       '                        <span class="blue"><i class="fa fa-phone"></i>'+userInfo[i].phone+'</span>\n' +
                       '                    </div>\n' +
                       '                    <!-- My Profile Widget -->\n' +
                       '                </div>\n' +
                       '            </div>');
               }

            }
        }
    })
}
/*打开创建圈子输入框*/
function openCreateCliqueCont() {
    $("#cliqueContent").empty();
    var input='<form  class="form-horizontal">'+
        '<div class="form-group">'+
        '<label for="cliqueName" class="col-sm-offset-3 col-sm-2 control-label">圈子名称</label>'+
        '<div class="col-sm-3">'+
        '<input type="text" class="form-control" id="cliqueName"'+ 'name="cliqueName">'+
        '</div>'+
        '</div>'+
        '</form>';
    var createClique='<div class="container" style="margin-top: 20%;text-align: center">' +input+
        '<button type="button" class="btn btn-primary" style="margin-top: 40px;margin-left: 8%" onclick="createClique()">创建圈子</button></div>';
    $("#cliqueContent").append(createClique);
}
/*创建圈子提交*/
function createClique() {
    var cliqueName=$("#cliqueName").val();
    var dataJson=new Object();
    dataJson.userName=userName;
    dataJson.cliqueName=cliqueName;
    var data=JSON.stringify(dataJson);
    $.ajax({
        url: "user/create",
        dataType: "json",
        type: "POST",
        data:data,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                window.location.reload();
            }
        }
    })
}

function searchClique() {
    var cliqueName=$("#cliqueNameSearch").val();
    //userName=getUrlParam("userName");
    $.ajax({
        url: "user/search/"+userName+"/"+cliqueName,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            $("#cliqueContent").empty();
                if($("#tableHead").length==0){
                    $("#cliqueContent").append('<div id="tableHead" class="col-md-12">\n' +
                        '                                <div class="widget-area">\n' +
                        '                                    <h2 class="widget-title"><strong>圈子</strong> 搜索列表</h2>\n' +
                        '                                    <table class="table table-striped">\n' +
                        '                                        <thead>\n' +
                        '                                            <tr>\n' +
                        '                                                <th>#</th>\n' +
                        '                                                <th>圈子名称</th>\n' +
                        '                                                <th>创建者</th>\n' +
                        '                                                <th>创建时间</th>\n' +
                        '                                                <th style="text-align: center">操作</th>\n' +
                        '                                            </tr>\n' +
                        '                                        </thead>\n' +
                        '                                        <tbody id="cliqueList">\n' +
                        '                                        </tbody>\n' +
                        '                                    </table>\n' +
                        '                                </div>\n' +
                        '                            </div>')
                }
            if (retData.data.length>0){
                var cliqueList=retData.data;
                $("#cliqueList").empty();
                //var name=''+userName;
                for(var i=0;i<cliqueList.length;i++){
                    //var serialNum=''+cliqueList[i].serialNum;
                    var tableContent='<tr>\n' +
                        '<td>'+(i+1)+'</td>\n' +
                        '<td>'+cliqueList[i].name+'</td>\n' +
                        '<td>'+cliqueList[i].creator+'</td>\n' +
                        '<td>'+cliqueList[i].updateTime+'</td>\n';
                    if(cliqueList[i].joined==1){
                        tableContent+='<td style="text-align: center"><button class="btn btn-default" disabled="disabled">已加入</button></td></tr>'
                    }else {
                        tableContent+='<td style="text-align: center"><button class="btn btn-success" onclick="joinClique('+'\''+cliqueList[i].serialNum+'\',\''+userName+'\''+ ')">加入圈子</button></td></tr>'
                    }
                    $("#cliqueList").append(tableContent);
                }
            }else {
                $("#cliqueList").append("无匹配圈子");
            }



        }
    })
}

/*加入圈子*/
function joinClique(serialNum,userName) {
    var data=JSON.stringify({'serialNum':serialNum,'userName':userName});
    $.ajax({
        url: "user/join",
        dataType: "json",
        type: "POST",
        data:data,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                window.location.reload();
            }
        }
    })
}
/*退出圈子*/
function quitClique(serialNum) {
    $.ajax({
        url: "user/quit/"+userName+"/"+serialNum,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                window.location.reload();
            }
        }
    })
}
/*加载修改个人信息页面*/
function loadUpdateUserInfoPage() {
    $("#cliqueContent").empty();
    $("#cliqueContent").load("update-user-info.html");
}
/*加载修改密码页面*/
function loadUpdatePasswordPage() {
    $("#cliqueContent").empty();
    var input='<form  class="form-horizontal">'+
        '<div class="form-group">'+
        '<label for="password" class="col-sm-offset-3 col-sm-2 control-label">新密码</label>'+
        '<div class="col-sm-3">'+
        '<input type="text" class="form-control" id="password"'+ 'name="password">'+
        '</div>'+
        '</div>'+
        '</form>';
    $("#cliqueContent").append('<div class="container" style="margin-top: 20%;text-align: center">'+input+'<button type="button" style="margin-top: 40px;margin-left: 8%;" class="btn btn-primary" onclick="updatePassword()">确认修改</button></div>');
}
/*修改密码*/
function updatePassword() {
    var password=$("#password").val();
    var dataJson=JSON.stringify({'userName':userName,'passWord':password})
    $.ajax({
        url: "user/updatePassWord",
        dataType: "json",
        type: "POST",
        data:dataJson,
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
                   $("#cliqueContent").empty();
            }
        }
    })
}
function loadUserInfo() {
    $("#cliqueContent").load("user-info.html");
}

/*function getCookie(c_name)
{
    if (document.cookie.length>0)
    {
        c_start=document.cookie.indexOf(c_name + "=")
        if (c_start!=-1)
        {
            c_start=c_start + c_name.length+1
            c_end=document.cookie.indexOf(";",c_start)
            if (c_end==-1) c_end=document.cookie.length
            return unescape(document.cookie.substring(c_start,c_end))
        }
    }
    return ""
}*/
/*function setCookie(c_name,value,expiredays)
{
    var exdate=new Date()
    exdate.setDate(exdate.getDate()+expiredays)
    document.cookie=c_name+ "=" +escape(value)+
        ((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}*/



