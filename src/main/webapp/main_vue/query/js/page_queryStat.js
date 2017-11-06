
//功能 用户管理页面的 功能 ==========================================
//工程师   B
//email:     phone number:
//===================================================================
/*小工具 将字符串前面加上一个“#”，方便Jquery选择器使用*/
function hashfy(str){
    str+="#"+str;
    return str;
}

function orderQuery(queryObj){
    var hashKey=GLOBAL_lht.hashKey;
    var query=new Object();
    query.startTime=$(hashKey+queryObj.startTime).datetimebox('getValue');
    query.endTime=$(hashKey+queryObj.endTime).datetimebox('getValue');
    query.abnormalDevices=$(hashKey+queryObj.abnormalDevices).combobox('getValue');
    query.idInputQueryContain=$(hashKey+queryObj.idInputQueryContain).combobox('getValue');
    query.abnormalOrigin=$(hashKey+queryObj.abnormalOrigin).combobox('getValue');
    //工单确认页面中的工单查询
    //orderConfirmService.searchOrder(query,function(data){
    //    if(data.result){
    //
    //    }
    //})
}

function orderUpwardReport(orderObj){
    var hashKey=GLOBAL_lht.hashKey;

    function process(){
        openWindow($('#orderConfirm'));
        getLog()
    }

    function getLog(){

    }


    function orderUpwardReportConfirm(){
        //var tableId=orderObj.tableId;
        var selectedRow=$(hashKey+tableId).datagrid('getSelected');
        var index = $(hashKey+tableId).datagrid("getRowIndex", selectedRow);

        var order=new Object();
        var orderInputs=new Array();
        orderInputs[0]=order.cWdId=$(hashKey+orderObj.cWdId).textbox('getValue');
        orderInputs[1]=order.tAlarmOccurTime=$(hashKey+orderObj.tAlarmOccurTime).textbox('getValue');
        orderInputs[2]=order.cAlarmSrc=$(hashKey+orderObj.cAlarmSrc).textbox('getValue');
        orderInputs[3]=order.cAlarmCause=$(hashKey+orderObj.cAlarmCause).textbox('getValue');
        orderInputs[4]=order.cAlarmDeviceUnit=$(hashKey+orderObj.cAlarmDeviceUnit).textbox('getValue');
        orderInputs[5]=order.cAlarmDevice=$(hashKey+orderObj.cAlarmDevice).textbox('getValue');
        orderInputs[6]=order.cDutyUser=$(hashKey+orderObj.cDutyUser).textbox('getValue');
        orderInputs[7]=order.cAlarmDesc=$("#"+orderObj.cAlarmDesc).textbox('getValue');
        //下面两个是属于工单状态
        orderInputs[8]=order.rejectYN=$(hashKey+orderObj.rejectYN).textbox('getValue');
        orderInputs[9]=order.suspendYN=$(hashKey+orderObj.suspendYN).textbox('getValue');
        //上面两个应该归为一个属性iState
        orderInputs[10]=order.cAlarmConfirmDesc=$(hashKey+orderObj.cAlarmConfirmDesc).textbox('getValue');
        orderInputs[11]=order.cReceiveUnit=$(hashKey+orderObj.cReceiveUnit).textbox('getValue');
        orderInputs[12]=order.tDownTime=$(hashKey+orderObj.tDownTime).textbox('getValue');
        orderInputs[13]=order.tRequiredSolvedTime=$(hashKey+orderObj.tRequiredSolvedTime).textbox('getValue');

        var flag=true;
        for(var i=0;i<orderInputs.length;i++){
            if(orderInputs[i]==""){
                flag=false;
            }
        }
        if(flag==true){
            //工单表格输入内容传入后台的接口 -------将此需求及时告知谭奇栋
            //orderConfirmService.addOrder(order,function(data){
            //    if(data.result){
            //        $(hashKey+orderObj.tableId).datagrid('deleteRow',index)
            //
            //    }
            //})
        }else{
            alert("录入信息不完整！");
        }
        $(hashKey+orderObj.tableId).datagrid('deleteRow',index)

    }
    $(hashKey+orderObj.orderConfirmButton).click(function (event) {
        orderUpwardReportConfirm()
    });
    $(hashKey+orderObj.orderConfirmCancelButton).click(function (event) {
        closeWindow($(hashKey+orderObj.windowId));
    });


}






//功能 用户管理页面 组织树操作和用户操作 ===========================================
//工程师 芦海涛
//email: luhaitao0626@163.com     phone number: 15575913089
//==================================================================================



/*-----在document.ready时初始化加载JS--------------------------------------------*/
$(document).ready(function(){
    /*------------page层的公共变量设定----------------------------*/
    var GLOBAL_lht_orderConfirm=new Object();
    //工单表格对象
    GLOBAL_lht_orderConfirm.tb="tbContent";
    GLOBAL_lht_orderConfirm.toolbarId="toolbar";
    //搜素栏对象
    GLOBAL_lht_orderConfirm.search={
        startTime:"start_date",
        endTime:"end_time",
        abnormalDevice:"abnormal_devices",
        orgName:"id_input_queryContain",
        abnormalOrigin:"abnormal_origin",
        searchButton:"search",
        verifyInBatch:"verifyInBatch",
        leadout:"leadout"
    };

    //工单查询对象
    GLOBAL_lht_orderConfirm.queryObj={
        startTime:"start_time",
        endTime:"end_time",
        abnormalDevices:"abnormal_devices",
        idInputQueryContain:"id_input_queryContain",
        abnormalOrigin:"abnormal_origin"
    };

    //工单确认窗体内的组件对象
    GLOBAL_lht_orderConfirm.orderObj={
        tableId:GLOBAL_lht_orderConfirm.tb,
        windowId:"orderConfirm",
        cWdId:"abnormality_serial_number",
        tAlarmOccurTime:"abnormality_occurrence_time",
        cAlarmSrc:"abnormality_origin",
        cAlarmCause:"abnormality_reason",
        cAlarmDeviceUnit:"cOrgName",
        cAlarmDevice:"abnormality_device",
        cDutyUser:"submit_person",
        cAlarmDesc:"abnormality_description",
        iState:"reject_Y_N",
        suspendYN:"suspend_Y_N",
        cAlarmConfirmDesc:"abnormality_confirm_remark",
        cReceiveUnit:"receiving_unit",
        tDownTime:"order_issued_time",
        tRequiredSolvedTime:"required_completion_time",
        operationLog:"operation_log",
        orderConfirmButton:"order_confirm_button",
        orderConfirmCancelButton:"order_confirm_cancel_button"
    };

    /*------------page层的公共变量设定完毕------------------------*/
    /*------------变量引用和对象属性设置：------------------------*/

    /*------------变量引用和对象属性设置完毕：--------------------*/
    /*------------初始化函数引用----------------------------------*/
        var orderObj=GLOBAL_lht_orderConfirm.orderObj;
        var queryObj=GLOBAL_lht_orderConfirm.queryObj;
        //工单查询操作初始化
        orderQuery(queryObj);
        //工单确认操作初始化
        orderUpwardReport(orderObj);

    /*------------初始化函数引用完毕-----------------------------*/
});
/*----------初始化加载JS结束---------------------------------------------------*/