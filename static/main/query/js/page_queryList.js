//service 引用区域 //
var surveyService=SurveyService();

$(function(){

    $("#query").textbox({
        buttonText:"",
        iconCls:'icon-search',
        iconAlign:'left',
        height:30,
        onClick:function(){
            alert("search complete!")
        }
    });

    $('#tbContent').datagrid({
        singleSelect:true,
        striped:true,
        rownumbers:true,
        checkbox:true,
        fit:true,
        toolbar:"#toolbar",
        pagination:true,
        fitColumns:true,
        columns:[[
            {field:'name',align:'center',width:50,title:'问卷名称'},
            {field:'releaseTime',align:'center',width:50,title:'发布时间',formatter:common.dateFormatter},
            {field:'status',align:'center',width:50,title:'状态',formatter:common.surveyStatus},
            {field:'num',align:'center',width:50,title:'有效问卷数量',formatter:validQueryNum},
            {field:'result',align:'center',width:50,title:'看板结果',formatter:panelResult},
            {field:'operate',align:'center',width:50,title:'操作',formatter:operatorFormatter},
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
    return '<button onclick="preview('+'\''+row.surveyId+'\''+')">预览</button>' +
           '<button onclick="copy('+'\''+row.surveyId+'\''+')">复制</button>' +
           '<button  onclick="remove('+'\''+row.surveyId+'\''+')">删除</button>'
}

function shareOperate(value,row,index){
    return '<a class="easyui-linkbutton" onclick="shareQuery('+'\''+row.surveyId+'\''+')">分享</a>'
}

function preview(surveyId){
    window.location='createPanel.html'+"?"+surveyId
}
function copy(){
    window.location='createPanel.html'
}
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

function shareQuery(surveyId){
    $("#shareWin").window("open");
    $("#share_unicode").attr("src",surveyService.getQRcode());
    var location=window.location.href;
    var rev_locate=location.split("").reverse().join("");
    var index=rev_locate.indexOf('/');
    console.log("indexed");
    console.log(index);
    var newLocation=location.substring(0,location.length-index)+"answer.html"+"?"+surveyId;
    $("#answerAddress").html(newLocation);
    $("#answerAddress").attr("href",newLocation)

}

function findRowById(id){
    var rows=$("#tbContent").datagrid("getRows");
    for(var i=0;i<rows.length;i++){
        if(rows[i].surveyId==id){
            return rows[i];
        }
    }
}

var page={
    pageNumber:1,
    pageSize:20
};


function loadSurveyList(){
    surveyService.getAllSurveys(page,function(data){
        if(data.result){
            common.loadTableData(data,"tbContent")
        }
        else{
            alert(data.description)
        }
    })
}