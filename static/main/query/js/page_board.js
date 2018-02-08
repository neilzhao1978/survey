//service 引用区域 //
var answerService=AnswerService();

//从url中获取surveyId
var surveyId=common.GetRequest();

$(function(){

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
            {field:'replayerName',align:'center',width:100,title:'姓名',sortable:true},
            {field:'replayerPosition',align:'center',width:100,title:'职业类别',sortable:true},
            {field:'operate',align:'center',width:100,title:'操作',formatter:operatorFormatter}
        ]],
        data:[

        ],
        onSelect:function(value,row,index){
            console.log(row)
        },
        onClickCell:function(field, value, row, $element){
            if(field=="answerCount"){
                //alert('find you!')
                window.location='createPanel.html'+"?"+surveyId
            }
        }
    });


    loadAnswerList();

});


function operatorFormatter(value,row,index){
    var allBtnList=[
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

    return '<button class="btn btn-default" onclick="viewResult('+'\''+row.answerId+'\''+')">查看结果</button>';
}

function viewResult(answerId){
    window.location='answer.html?'+answerId;
}

function returnToList(){
    window.location='queryList.html';
}

//分页获取问卷的分页参数（bootstrapTable由于加载效率高，直接获取全部）
var page={
    pageNumber:1,//页数
    pageSize:9999,//每页获取的记录数
    orderByFieldName:"releaseTime",//排序字段名
    isDesc:true //true:递减，false:递增
};


//获取并加载survey
function loadAnswerList(){
    answerService.getAllAnswerList(page,surveyId,function(data){
        if(data.result){
            var surveyTitle = data.data[0].survey.name
            $("#survey-title").text(surveyTitle)
            $("#tbContent").bootstrapTable('load',data.data);
        }
        else{
            alert(data.description)
        }
    })
}

