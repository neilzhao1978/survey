//service 引用区域 //
var surveyService=SurveyService();

//$(function(){

    Vue.component('demo-grid', {
        template: '#grid-template',
        props: {
            data: Array,
            columns: Array,
            filterKey: String
        }
    })

    // bootstrap the demo
    var demo = new Vue({
        el: '#demo',
        data: {
            searchQuery:'',
            gridColumns: ['name', 'power'],
            gridData: [
                { name: 'Chuck Norris', power: Infinity },
                { name: 'Bruce Lee', power: 9000 },
                { name: 'Jackie Chan', power: 7000 },
                { name: 'Jet Li', power: 8000 }
            ]
        }
    });


//});

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