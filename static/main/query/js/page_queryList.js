//service 引用区域 //
var surveyService=SurveyService();
var clipboard = new Clipboard("#copyUrl");
$(function(){

    $("#searchBtn").click(function(e){
        searchSurvey();
    });

    $("#build").click(function(e){
        window.location='createPanel.html';
    });

    clipboard.on('success', function(e) {
        console.log(e);
    });

    clipboard.on('error', function(e) {
        console.log(e);
    });

    ////复制链接按钮事件绑定
    //$("#copyUrl").click(function(e){
    //    var url=$("#answerAddress")[0].href;
    //    window.copy(url)
    //});

    $('#tbContent').bootstrapTable({
        striped: true,
        pagination: true,
        paginationLoop: true,
        smartDisplay: true,
        singleSelect: false,
        pageSize: 5,
        pageList: [5],
        search: false,
        sidePagination: 'client',
        //showColumns:true,
        searchOnEnterKey: true,
        showFooter: false,
        //showToggle:true,
        showHeader: true,
        columns:[[
            {field:'name',align:'center',width:100,title:'问卷名称',sortable:true},
            {field:'releaseTime',align:'center',width:100,title:'发布时间',formatter:common.dateFormatter,sortable:true},
            {field:'status',align:'center',width:80,title:'状态',formatter:surveyStatus,sortable:true},
            {field:'num',align:'center',width:50,title:'有效问卷数量',formatter:validQueryNum,sortable:true},
            {field:'result',align:'center',width:50,title:'看板结果',formatter:panelResult,sortable:true},
            {field:'operate',align:'center',width:120,title:'操作',formatter:operatorFormatter},
            {field:'share',align:'center',width:50,title:'',formatter:shareOperate}
        ]],
        data:[

        ],
        onSelect:function(value,row,index){
            console.log(row)
        }
    });


    loadSurveyList();

});

function surveyStatus(value,row,index){
    if(value==1){
        return '草稿'
    }
    else if(value==2){
        return '开放中<button class="btn btn-default" onclick="closeOpenSurvey('+'\''+row.surveyId+'\''+','+3+')">关闭</button>'
    }
    else if(value==3){
        return '已关闭<button class="btn btn-default" onclick="closeOpenSurvey('+'\''+row.surveyId+'\''+','+2+')">开启</button>'
    }
    else{
        return '未知'
    }

}

function validQueryNum(value,row,index){
    if(value){
        return value
    }else{
        return "/"
    }
}
function panelResult(value,row,index){
    if(value){
        return value
    }else{
        return "/"
    }
}
function operatorFormatter(value,row,index){
    var allBtnList=[
        '<button class="btn btn-default" onclick="_edit('+'\''+row.surveyId+'\''+')">编辑</button>',
        '<button class="btn btn-default" onclick="_preview('+'\''+row.surveyId+'\''+')">预览</button>',
        '<button class="btn btn-default" onclick="_copy('+'\''+row.surveyId+'\''+')">复制</button>',
        '<button class="btn btn-default" onclick="_remove('+'\''+row.surveyId+'\''+')">删除</button>'
    ];
    var btnArr=[];
    if(row.status==1){//草稿
        btnArr.push(allBtnList[0]);
    }
    else if(row.status==2){//发布
        btnArr.push(allBtnList[1]);
    }
    else if(row.status==3){//关闭
        btnArr.push(allBtnList[1]);
    }else{

    }
    btnArr.push(allBtnList[2]);
    btnArr.push(allBtnList[3]);

    return btnArr.join("");
}

//关闭、开放suvery
function closeOpenSurvey(surveyId,status){
    surveyService.closeOpenSurvey(surveyId,status,function(){},function(data){
        alert(data.description);
        if(data.result){
            loadSurveyList();
        }else{

        }
    })
}

function shareOperate(value,row,index){
    var str="";
    if(row.status==1){
        str=""
    }
    else{
        str='<a class="easyui-linkbutton" onclick="shareQuery('+'\''+row.surveyId+'\''+')">分享</a>'
    }
    return str
}

//跳转到预览界面
function _preview(surveyId){
    window.location='answer.html'+"?"+surveyId
}

//跳转到编辑界面
function _edit(surveyId){
    window.location='createPanel.html'+"?"+surveyId
}

//复制一份survey
function _copy(surveyId){
    surveyService.copySurvey(surveyId,function(){},function(data){
        alert(data.description);
        if(data.result){
            loadSurveyList();
        }else{

        }
    })
}

//删除suvery
function _remove(surveyId){
    var row=findRowById(surveyId);
    surveyService.deleteSurvey(row,function(){},function(data){
        alert(data.description);
        if(data.result){
            loadSurveyList();
        }else{

        }
    })
}

//打开分享问卷窗口
function shareQuery(surveyId){
    $("#shareWin").modal("show");
    var location=window.location.href;
    var rev_locate=location.split("").reverse().join("");
    var index=rev_locate.indexOf('/');
    console.log("indexed");
    console.log(index);
    var newLocation=location.substring(0,location.length-index)+"answer.html"+"?"+surveyId;
    $("#answerAddress").html(newLocation);
    $("#answerAddress").attr("href",newLocation);
    $("#share_unicode").attr("src",surveyService.getQRcode(newLocation));


}

//根据id查找到行信息并返回行对象
function findRowById(id){
    var rows=$("#tbContent").bootstrapTable("getData",true);
    for(var i=0;i<rows.length;i++){
        if(rows[i].surveyId==id){
            console.log("rows[i]");
            console.log(rows[i]);

            return rows[i];
        }
    }
}

//分页获取问卷的分页参数（bootstrapTable由于加载效率高，直接获取全部）
var page={
    pageNumber:1,//页数
    pageSize:9999,//每页获取的记录数
    orderByFieldName:"releaseTime",//排序字段名
    isDesc:true //true:递减，false:递增
};

//用户存放问卷列表的公共变量
var tableDatas=[];
//获取并加载survey
function loadSurveyList(){
    surveyService.getAllSurveys(page,function(data){
        if(data.result){
            tableDatas=data.data;
            $("#tbContent").bootstrapTable('load',data.data);
            //common.loadTableData(data,"tbContent")
        }
        else{
            alert(data.description)
        }
    })
}

//根据名称查询suvery
function searchSurvey(){
    var surveyName=$("#name").val();
    var theRow=[];
    if(surveyName!=""){
        var list=fuzzySearch(surveyName,"objArr",tableDatas,"name");
        for(var i=0;i<list.length;i++){
            theRow.push(list[i]);
        }
    }else{
        theRow=tableDatas;
    }

    $("#tbContent").bootstrapTable('load',theRow);
}
//模糊查询
function fuzzySearch(keyWord,type,list,attrName){
    var arr=[];
    var reg=new RegExp(keyWord);
    if(type=="pureArr"){
        for(var i=0;i<list.length;i++){
            if(list[i].match(reg)){
                arr.push(list[i]);
            }
        }
    }
    else if(type=="objArr"){
        for(i=0;i<list.length;i++){
            if(list[i][attrName].match(reg)){
                arr.push(list[i]);
            }
        }
    }

    return arr;

}

