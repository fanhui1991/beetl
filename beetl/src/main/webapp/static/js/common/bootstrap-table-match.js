/**
 * Created by DELL on 2017/7/17.
 */
function queryParams(params){
    return{
        //每页多少条数据
        limit: params.limit,
        //请求第几页
        offset:params.offset,
        sort: params.sort,  //排序列名
        sortOrder: params.order//排位命令（desc，asc）
    }
}

(function () {
    var BSTable1 = function (bstableId, url, columns) {
        this.btInstance = null;					//jquery和BootStrapTable绑定的对象
        this.bstableId = bstableId;
        this.url = Feng.ctxPath + url;
        this.method = "post";
        this.paginationType = "server";			//默认分页方式是服务器分页,可选项"client"
        this.toolbarId = bstableId + "Toolbar";
        this.columns = columns;
        this.height = "";						//默认表格高度665
        this.data = {};
        this.queryParams = {}; // 向后台传递的自定义参数
    };

    BSTable1.prototype = {
        /**
         * 初始化bootstrap table
         */
        init: function () {
            var tableId = this.bstableId;
            this.btInstance =
                $('#' + tableId).bootstrapTable({
                    contentType: "application/x-www-form-urlencoded",
                    url: this.url,				//请求地址
                    method: this.method,		//ajax方式,post还是get
                    sidePagination: "server",
                    detailView: true,//父子表
                    striped: true,     			//是否显示行间隔色
                    cache: false,      			//是否使用缓存,默认为true
                    pagination: true,     		//是否显示分页（*）
                    sortable: true,      		//是否启用排序
                    sortOrder: "desc",     		//排序方式
                    pageNumber: 1,      			//初始化加载第一页，默认第一页
                    pageSize: 14,      			//每页的记录行数（*）
                    pageList: [10, 20, 50, 100, 200],  	//可供选择的每页的行数（*）
                    queryParams: queryParams, // 向后台传递的自定义参数
                    columns: this.columns,		//列数组
                    height: this.height,
                    onExpandRow: function (index, row, $detail) {
                        InitSubTable(index, row, $detail);}
                });//index：父表当前行的行索引。row：父表当前行的Json数据对象。$detail：当前行下面创建的新行里面的td对象。
            return this;
        },

        serve_init : function (searchArgs) {
            var tableId = this.bstableId;
            this.obj = $('#' + tableId);
            //---先销毁表格 ---
            this.obj.bootstrapTable('destroy');
            this.btInstance = this.obj.bootstrapTable({
                    contentType: "application/x-www-form-urlencoded",
                    url:this.url,				//请求地址
                    method: this.method,		//ajax方式,post还是get
                    sidePagination: "server",
                    detailView: true,//父子表
                    striped: true,     			//是否显示行间隔色
                    cache: false,      			//是否使用缓存,默认为true
                    pagination: true,     		//是否显示分页（*）
                    sortable: true,      		//是否启用排序
                    sortOrder: "desc",     		//排序方式
                    pageNumber: 1,      			//初始化加载第一页，默认第一页
                    pageSize: 14,      			//每页的记录行数（*）
                    pageList: [10, 20, 50, 100, 200],  	//可供选择的每页的行数（*）
                    queryParams: function(params){
                        var param = {
                            limit: params.limit,
                            offset:params.offset,
                            sort: params.sort,  //排序列名
                            sortOrder: params.order//排位命令（desc，asc）
                        };
                        for(var key in searchArgs){
                            param[key]=searchArgs[key];
                        }
                        return param;
                    }, // 向后台传递的自定义参数
                    columns: this.columns,		//列数组
                    height: this.height,
                    onExpandRow: function (index, row, $detail) {
                        InitSubTable(index, row, $detail);}
                });//index：父表当前行的行索引。row：父表当前行的Json数据对象。$detail：当前行下面创建的新行里面的td对象。
            return this;
        },

        /**
         * 刷新 bootstrap 表格
         * Refresh the remote server data,
         * you can set {silent: true} to refresh the data silently,
         * and set {url: newUrl} to change the url.
         * To supply query params specific to this request, set {query: {foo: 'bar'}}
         */
        refresh: function (parms) {
            if (typeof parms != "undefined") {
                this.btInstance.bootstrapTable('refresh', parms);
            } else {
                this.btInstance.bootstrapTable('refresh');
            }
        }
    };

    window.BSTable1 = BSTable1;

}());
