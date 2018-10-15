/**
 * 管理
 */
var Content = {
    id: "ContentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

var static = {0:"无效",1:"有效"};

/**
 * 初始化表格的列
 */
Content.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'ID', field: 'ID', visible: true, align: 'center', valign: 'middle',width:'50px'},
        {title: '标题', field: 'TITLE', align: 'center', valign: 'middle', sortable: false},
        {title: '内容', field: 'CONTENT', align: 'center', valign: 'middle', sortable: false,
            formatter:function (value,row) {
                var CONTENT = row.CONTENT;
                var txt = CONTENT.replace(/<[^>]+>/g,"");
                if(txt.length>50){
                    txt=txt.substring(0,50)+'…';
                }
                return txt;
            }
        },
        {title: '图片', field: 'PIC', align: 'center', valign: 'middle', sortable: false},
        {title: "操作", field: '', align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                var ID = row.ID;
                var html = '<a onclick="Content.contentPostInfo(\''+ID+'\')">编辑</a>'
                return html;
            }
        }
    ]
    return columns;
};

/**
 * 搜索
 */
Content.queryContentList = function () {
    var queryData = {};
    queryData['content_id'] = $("#content_id").val();
    queryData['status'] = $("#status").val();
    Content.table.server_init(queryData);
}

/**
 *编辑
 */
Content.contentPostInfo= function (ID) {
    var index = layer.open({
        type: 2,
        title: '编辑 ：'+ID,
        area: ['100%', '100%'], //宽高
        fix: true, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/demo/contentPostInfo/'+ID
    });
    this.layerIndex = index;
}

/**
 * 点击添加
 */
Content.queryAddContent = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/demo/addContent'
    });
    this.layerIndex = index;
};


$(function () {
    var defaultColunms = Content.initColumn();
    var AUTO_ID = $("#AUTO_ID").val();
    var table = new BSTable(Content.id, "/demo/queryContentList", defaultColunms);
    var queryData = {};
    queryData['content_id'] = $("#content_id").val();
    queryData['status'] = $("#status").val();
    Content.table = table.server_init(queryData);
    Content.Info(AUTO_ID)
});

Content.Info = function (AUTO_ID) {
    if(AUTO_ID=="1"){
        KindEditor.ready(function(K) {
            window.editor = K.create('#content',{
                uploadJson : Feng.ctxPath + '/demo/addPic',
                themeType : 'simple',
                newlineTag:'br'
            });
        });
    }
}

/**
 * 关闭此对话框
 */
Content.close = function () {
    parent.layer.close(window.parent.Content.layerIndex);
};

Content.addSubmit = function () {
    var title = $("#title").val();
    var content = editor.fullHtml()
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/demo/contentInsert", function (data) {
        if(data.code ==1){
            Feng.success("添加成功！");
            window.parent.Content.table.refresh();
            Content.close();
        }else {
            Feng.error("添加失败!");
            Content.close();
        }
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("title",title);
    ajax.set("content",content);
    ajax.start();
}

Content.exitSubmit = function () {
    var id = $("#id").val();
    var title = $("#title").val();
    var content = editor.fullHtml()
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/demo/contentUpdate", function (data) {
        if(data.code ==1){
            Feng.success("编辑成功！");
            window.parent.Content.table.refresh();
            Content.close();
        }else {
            Feng.error("编辑失败！");
            Content.close();
        }
    }, function (data) {
        Feng.error("编辑失败!" + data.responseJSON.message + "!");
    });
    ajax.set("title",title);
    ajax.set("content",content);
    ajax.set("id",id);
    ajax.start();
}