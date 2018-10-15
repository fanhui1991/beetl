var Feng = {

    imgPath:"http://112.74.47.13:9110/fh/",
    ctxPath: "",
    addCtx: function (ctx) {
        if (this.ctxPath == "") {
            this.ctxPath = ctx;
        }
    },
    setMatchconfirm: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            title: "info",
            btn: ['确定','取消']
        }, function (index) {
            parent.layer.close(index);
            ensure();
        }, function (index) {
            parent.layer.close(index);
        });
    },
    confirm: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            title: "info",
            btn: ['确定','取消']
        }, function (index) {
            ensure();
            parent.layer.close(index);
        }, function (index) {
            parent.layer.close(index);
        });
    },
    layerOpenTip :function(title,html,fun1) {
        //弹出窗口 支持html 和执行方法的，Feng.layerOpenTip("请确认数据源对应的本地队伍：",html,function(){})
        var index=layer.open({
            title: title
            ,content: html,
            btn:['确定','取消'],
            btn1:function(index,layero){
                fun1()
            },btn2:function (index,layero) {
                parent.layer.close()
            }
        });
        this.layerIndex = index;
    },
    layerOpen : function (title,width,height,url) {
        //跳转到弹出层页面 调用方法 Feng.layerOpen("新建联赛名称","50%","50%","/localLeagueManage/toAddTournamet")
        var index = layer.open({
            type: 2,
            title: title,
            area: [width, height], //宽高
            fix: false, //不固定
            maxmin: false,
            content: Feng.ctxPath + url
        })
        this.layerIndex = index;
    },
    confirmOnlyTure: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            btn: ['确定']
        })
    },
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex) {
        parent.layer.msg(info, {
            icon: iconIndex
        });
    },
    info: function (info) {
        Feng.alert(info, 0);
    },
    success: function (info) {
        Feng.alert(info, 1);
    },
    error: function (info) {
        Feng.alert(info, 2);
    },
    en_error: function (data) {
        //后台传回来的消息  resMap.put("msg","该联赛本地不存在,请去本地联赛管理新增···"); resMap.put("en_msg","not exist")
        var Language = getCookie("userLanguage");
        if(Language =="en"){
            Feng.alert($.i18n.prop("operationfailure")+" "+data["en_msg"], 2);
        }else {
            Feng.alert($.i18n.prop("operationfailure")+" "+data["msg"], 2);
        }
    },
    infoDetail: function (title, info) {
        var display = "";
        if (typeof info == "string") {
            display = info;
        } else {
            if (info instanceof Array) {
                for (var x in info) {
                    display = display + info[x] + "<br/>";
                }
            } else {
                display = info;
            }
        }
        parent.layer.open({
            title: title,
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['950px', '600px'], //宽高
            content: '<div style="padding: 20px;">' + display + '</div>'
        });
    },
    writeObj: function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += i + " = " + property + ",";
        }
        layer.alert(description, {
            skin: 'layui-layer-molv',
            closeBtn: 0
        });
    },
    showInputTree: function (inputId, inputTreeContentId, leftOffset, rightOffset) {
        var onBodyDown = function (event) {
            if (!(event.target.id == "menuBtn" || event.target.id == inputTreeContentId || $(event.target).parents("#" + inputTreeContentId).length > 0)) {
                $("#" + inputTreeContentId).fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
            }
        };

        if(leftOffset == undefined && rightOffset == undefined){
            var inputDiv = $("#" + inputId);
            var inputDivOffset = $("#" + inputId).offset();
            $("#" + inputTreeContentId).css({
                left: inputDivOffset.left + "px",
                top: inputDivOffset.top + inputDiv.outerHeight() + "px"
            }).slideDown("fast");
        }else{
            $("#" + inputTreeContentId).css({
                left: leftOffset + "px",
                top: rightOffset + "px"
            }).slideDown("fast");
        }

        $("body").bind("mousedown", onBodyDown);
    },
    baseAjax: function (url, tip) {
        var ajax = new $ax(Feng.ctxPath + url, function (data) {
            Feng.success(tip + "成功!");
        }, function (data) {
            Feng.error(tip + "失败!" + data.responseJSON.message + "!");
        });
        return ajax;
    },
    changeAjax: function (url) {
        return Feng.baseAjax(url, "修改");
    },
    zTreeCheckedNodes: function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        var nodes = zTree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            ids += "," + nodes[i].id;
        }
        return ids.substring(1);
    },
    eventParseObject: function (event) {//获取点击事件的源对象
        event = event ? event : window.event;
        var obj = event.srcElement ? event.srcElement : event.target;
        return $(obj);
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    //如果超时就处理 ，指定要跳转的页面
                    window.location = Feng.ctxPath + "/global/sessionError";
                }
            }
        });
    },
    initValidator: function(formId,fields){
        $('#' + formId).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields,
            live: 'enabled',
            message: '该字段不能为空'
        });
    },
    isEmpty:function(obj){
        if(Object.prototype.toString.call(obj) == "[object Object]"){
            var i;
            for(i in obj){
                return false
            }
            return true;
        }
        if(obj == null || typeof (obj) == "undefined" || obj == undefined || obj == "undefined" || (""+obj).toUpperCase() == "NULL" || ""+obj == ""){ // 0 == "" ->true
            return true ;
        }

        return false ;
    }
};
