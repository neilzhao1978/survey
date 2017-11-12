//service 引用区域 //
var surveyService=SurveyService();

Vue.component('demo-grid',{
    template:"#grid-template",
    props: {
        data: Array,
        columns: Array
    }
});

var col_fields=['name','releaseTime','status','num','result','operate','share'];
var columns=[
    {field:'name',align:'center',width:50,title:'问卷名称'},
    {field:'releaseTime',align:'center',width:50,title:'发布时间',formatter:common.dateFormatter},
    {field:'status',align:'center',width:50,title:'状态',formatter:common.surveyStatus},
    {field:'num',align:'center',width:50,title:'有效问卷数量'},
    {field:'result',align:'center',width:50,title:'看板结果'},
    {field:'operate',align:'center',width:50,title:'操作',formatter:operatorFormatter},
    {field:'share',align:'center',width:50,title:'',formatter:shareOperate}
];

// bootstrap the demo
var demo = new Vue({
    el: '#demo',
    data: {
        gridColumns: col_fields,
        gridData: [

        ]
    }
});

$(function(){

});

function operatorFormatter(value,row,index){
    return "<span onclick='edit()'>编辑/</span><span onclick='copy()'>复制/</span><span  onclick='remove()'>删除</span>"
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

$(function(){
    loadSurveyList();
});

function loadSurveyList(){
    demo.getData=[{ name: 'Chuck Norris', power: Infinity ,age:'2'},
        { name: 'Bruce Lee', power: 9000 ,age:'2'},
        { name: 'Jackie Chan', power: 7000 ,age:'2'},
        { name: 'Jet Li', power: 8000 ,age:'2',operate:'1'}];
    //surveyService.getAllSurveys(page,function(data){
    //    if(data.result){
    //        demo.getData=data.data
    //    }
    //    else{
    //        alert(data.description)
    //    }
    //})
}