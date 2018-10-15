/**
 * 图片管理
 */
var Pic = {
    id: "PicTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

var static = {0:"无效",1:"有效"};

/**
 * 初始化表格的列
 */
Pic.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'ID', field: 'ID', visible: false, align: 'center', valign: 'middle',width:'50px'},
        {title: '描述', field: 'DESC', align: 'center', valign: 'middle', sortable: false},
        {title: '图片名字', field: 'PIC_NAME', align: 'center', valign: 'middle', sortable: false},
        {title: "图片", field: 'PIC_NAME', align: 'center', valign: 'middle',formatter:
            function (value,row,index) {
                var ID = row.ID;
                var src =Feng.imgPath + "static/images/" + value;
                if(value == undefined || value == ''){
                    src = "static/images/1314.jpg"
                }
                var img = '<div id="cutPic'+ID+'"><img onclick="Pic.showPic(\''+ID+'\')" src="'+src+'" width="50" height="50"></div>'
                    +'<div id="pic'+ID+'" hidden="hidden"><img onclick="Pic.hidePic(\''+ID+'\')" src="'+src+'"></div>';
                return img;
            }
     }]
    return columns;
};

/**
 *点击缩略图查看大图
 */
Pic.showPic=function (ID) {
    $("#cutPic"+ID).hide();
    $("#pic"+ID).show();
}
/**
 *点击大图隐藏大图
 */
Pic.hidePic=function (ID) {
    $("#cutPic"+ID).show();
    $("#pic"+ID).hide();
}

/**
 * 搜索
 */
Pic.queryPicList = function () {
    var queryData = {};

    queryData['content_id'] = $("#content_id").val();
    queryData['status'] = $("#status").val();
    Pic.table.server_init(queryData);
}

/**
 * 搜索
 */
Pic.queryAddPic = function () {
    var index = layer.open({
        type: 2,
        title: '上传图片',
        area: ['400px', '660px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/pic/addPic'
    });
    this.layerIndex = index;
}

$(function () {
    var defaultColunms = Pic.initColumn();
    var table = new BSTable(Pic.id, "/pic/queryPicList", defaultColunms);
    var queryData = {};
    queryData['pic_name'] = $("#pic_name").val();
    queryData['status'] = $("#status").val();
    Pic.table = table.server_init(queryData);
});

/**
 * 关闭此对话框
 */
Pic.close = function () {
    parent.layer.close(window.parent.Pic.layerIndex);
};


/**
 * 上传提交
 */
Pic.addSubmit = function ajaxFileUpload() {
    var formData = new FormData($("#Pic")[0]);
    $.ajax({
        url : Feng.ctxPath + "/pic/appendPic",
        type: 'POST',
        data: formData,
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.success(data["msg"]);
            window.parent.Pic.table.refresh();
            Pic.close();
        },
        error: function () {
            Feng.alert("服务器连接失败");
        }
    });
}
