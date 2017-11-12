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
            {field:'num',align:'center',width:50,title:'有效问卷数量'},
            {field:'result',align:'center',width:50,title:'看板结果'},
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

function operatorFormatter(value,row,index){
    return "<button onclick='edit()'>编辑</button><button onclick='copy()'>复制</button><button  onclick='remove()'>删除</button>"
}

function shareOperate(value,row,index){
    return "<a class='easyui-linkbutton' onclick='shareQuery()'>分享</a>"
}

function edit(){
    window.location='createPanel.html'
}
function copy(){
    window.location='createPanel.html'
}
function remove(){
    window.location='createPanel.html'
}

function shareQuery(){
    $("#shareWin").window("open");
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