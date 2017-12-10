$().ready(function () {
   checkCookie();
})


function checkCookie() {
    circleToken = getCookie('circle_token');
    console.debug(circleToken);
    if (circleToken != null && circleToken != "") {
        $.ajax({
            url: "user/checkToken",
            dataType: "json",
            type: "GET",
            contentType: "application/json; charset=utf-8",
            success: function (retData) {
                console.debug(retData);
                if (retData.code === 0) {
                   $("#unLogin").css("display","none");
                   console.debug(retData);
                   //$("#unLogin").after("<li>"+retData.data.nickName+"<li>")
                }
            }
        })

    }
    else {
        username = prompt('Please enter your name:', "")
        if (username != null && username != "") {
            setCookie('username', username, 365)
        }
    }
}

function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return ""
}

