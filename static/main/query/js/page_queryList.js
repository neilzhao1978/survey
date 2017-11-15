//service 引用区域 //
var surveyService=SurveyService();

$(function(){

    //$("#query").textbox({
    //    buttonText:"",
    //    iconCls:'icon-search',
    //    iconAlign:'left',
    //    height:30,
    //    onClick:function(){
    //        alert("search complete!")
    //    }
    //});

    $("#searchBtn").click(function(e){
        searchSurvey();
    });

    $("#build").click(function(e){
        window.location='createPanel.html';
    });

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
            {field:'name',align:'center',width:100,title:'问卷名称'},
            {field:'releaseTime',align:'center',width:100,title:'发布时间',formatter:common.dateFormatter},
            {field:'status',align:'center',width:80,title:'状态',formatter:common.surveyStatus},
            {field:'num',align:'center',width:50,title:'有效问卷数量',formatter:validQueryNum},
            {field:'result',align:'center',width:50,title:'看板结果',formatter:panelResult},
            {field:'operate',align:'center',width:120,title:'操作',formatter:operatorFormatter},
            {field:'share',align:'center',width:50,title:'',formatter:shareOperate}
        ]],
        data:[

        ],
        onSelect:function(value,row,index){
            console.log(row)
        }
    });
    //$('#tbContent').bootstrapTable({
    //    singleSelect:true,
    //    striped:true,
    //    rownumbers:true,
    //    checkbox:true,
    //    fit:true,
    //    toolbar:"#toolbar",
    //    pagination:true,
    //    fitColumns:true,
    //    columns:[[
    //        {field:'name',align:'center',width:50,title:'问卷名称'},
    //        {field:'releaseTime',align:'center',width:50,title:'发布时间',formatter:common.dateFormatter},
    //        {field:'status',align:'center',width:50,title:'状态',formatter:common.surveyStatus},
    //        {field:'num',align:'center',width:50,title:'有效问卷数量',formatter:validQueryNum},
    //        {field:'result',align:'center',width:50,title:'看板结果',formatter:panelResult},
    //        {field:'operate',align:'center',width:50,title:'操作',formatter:operatorFormatter},
    //        {field:'share',align:'center',width:50,title:'',formatter:shareOperate}
    //    ]],
    //    data:[
    //
    //    ],
    //    onSelect:function(value,row,index){
    //        console.log(row)
    //    }
    //});

    loadSurveyList();

});

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
    return '<button class="btn btn-default" onclick="preview('+'\''+row.surveyId+'\''+')">预览</button>' +
           '<button class="btn btn-default" onclick="copy('+'\''+row.surveyId+'\''+')">复制</button>' +
           '<button class="btn btn-default" onclick="remove('+'\''+row.surveyId+'\''+')">删除</button>'
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

function preview(surveyId){
    window.location='createPanel.html'+"?"+surveyId
}
function copy(){
    window.location='createPanel.html'
}

//删除suvery
function remove(surveyId){
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

var page={
    pageNumber:1,
    pageSize:999
};

var tableDatas=[];
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

function searchSurvey(){
    var surveyName=$("#name").val();
    var theRow=[];

    if(surveyName!=""){
        for(var i=0;i<tableDatas.length;i++){
            if(tableDatas[i].name==surveyName){
                console.log("tableDatas[i]");
                console.log(tableDatas[i]);
                theRow.push(tableDatas[i]);
            }else{

            }
        }
    }else{
        theRow=tableDatas;
    }

    $("#tbContent").bootstrapTable('load',theRow);
}
