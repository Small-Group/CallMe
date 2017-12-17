$().ready(function () {
    $("#header").load("header.html");
    //checkLogin();
    getCliqueList();
})


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

/*获取url后的请求参数*/
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r !== null) return decodeURI(r[2]);
    return null; //返回参数值
}

/*从cookie中获取圈子列表*/
function getCliqueList() {
    var cliqueList=JSON.parse(getCookie('clique'));
    for (var i=0;i<cliqueList.length;i++){
        var clique=cliqueList[i];
        var cliqueName=clique.name;
        var cliqueSerialNum=clique.serialNum;
        $("#menu-toogle").append('<div class="single-menu">'+
            '<h2><a title="" href="javascript:void(0);" onclick="loadCliqueUsersPage('+cliqueSerialNum+')"><i class="fa fa-heart-o"></i><span>'+cliqueName+'</span></a></h2>\n' +
            '</div>')
    }
}

/*加载右侧页面*/
function loadCliqueUsersPage(serialNum) {
    $.ajax({
        url: "user/findUserInfoList/"+serialNum,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.code === 0) {
               var userInfo=retData.data;
                $("#cliqueContent").empty();
               for(var i=0;i<userInfo.length;i++){
                   $("#cliqueContent").append('<div class="col-md-2">\n' +
                       '                <div class="widget-area no-padding">\n' +
                       '                    <div class="my-profile-widget">\n' +
                       '                        <div class="profile-widget-head">\n' +
                       '                            <h3>'+userInfo[i].nickName+'</h3>\n' +
                       '                            <span><img alt="" src="images/resource/me.jpg"></span>\n' +
                       '                        </div>\n' +
                       '                        <span class="blue"><i class="fa fa-map-marker"></i>San Francisco</span>\n' +
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
    var createClique='<input type="text" id="cliqueName"><button type="button" class="green" onclick="createClique()"><i class="fa fa-plus"></i>创建圈子</button>';
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
                console.log('xx==='+retData);
            }
        }
    })
}

function searchClique() {
    var cliqueName=$("#cliqueNameSearch").val();
    $.ajax({
        url: "user/search/"+cliqueName,
        dataType: "json",
        type: "GET",
        contentType: "application/json; charset=utf-8",
        success: function (retData) {
            if (retData.data.length>0) {
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
                        '                                            </tr>\n' +
                        '                                        </thead>\n' +
                        '                                        <tbody id="cliqueList">\n' +
                        '                                        </tbody>\n' +
                        '                                    </table>\n' +
                        '                                </div>\n' +
                        '                            </div>')
                }
                var cliqueList=retData.data;
                $("#cliqueList").empty();
                for(var i=0;i<cliqueList.length;i++){
                     $("#cliqueList").append('<tr>\n' +
                         '                                                <td>'+(i+1)+'</td>\n' +
                         '                                                <td>'+cliqueList[i].name+'</td>\n' +
                         '                                                <td>'+cliqueList[i].creator+'</td>\n' +
                         '                                                <td>'+cliqueList[i].updateTime+'</td>\n' +
                         '                                            </tr>');
                }
                }

        }
    })
}

function getCookie(c_name)
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
}



