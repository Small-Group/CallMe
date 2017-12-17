$("#myfile").fileinput({
        //上传的地址
        uploadUrl:"<%=basePath%>food/uploadFile",
        uploadAsync : true, //默认异步上传
        showUpload : true, //是否显示上传按钮,跟随文本框的那个
        showRemove : true, //显示移除按钮,跟随文本框的那个
        showCaption : true,//是否显示标题,就是那个文本框
        showPreview : true, //是否显示预览,不写默认为true
        dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount : 1, //表示允许同时上传的最大文件个数
        enctype : 'multipart/form-data',
        validateInitialCount : true,
        previewFileIcon : "<i class='glyphicon glyphicon-king'></i>",
        msgFilesTooMany : "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
        allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
        allowedPreviewTypes : [ 'image' ],//配置所有的被预览文件类型
        allowedPreviewMimeTypes : [ 'jpg', 'png', 'gif' ],//控制被预览的所有mime类型
        language : 'zh'
    })
//异步上传返回结果处理
$('.myfile').on('fileerror', function(event, data, msg) {
    console.log("fileerror");
    console.log(data);
});
//异步上传返回结果处理
$(".myfile").on("fileuploaded", function(event, data, previewId, index) {
    console.log("fileuploaded");
    var ref = $(this).attr("data-ref");
    $("input[name='" + ref + "']").val(data.response.url);

});

//同步上传错误处理
$('.myfile').on('filebatchuploaderror', function(event, data, msg) {
    console.log("filebatchuploaderror");
    console.log(data);
});

//同步上传返回结果处理
$(".myfile").on("filebatchuploadsuccess",
    function(event, data, previewId, index) {
        console.log("filebatchuploadsuccess");
        console.log(data);
    });

//上传前
$('.myfile').on('filepreupload', function(event, data, previewId, index) {
    console.log("filepreupload");
});
