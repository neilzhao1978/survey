/**
 * Created by virgil on 15-9-29.
 */
/*var host="http://192.168.0.156:8080/web";

//var host="/web/api";

var wsHost='https:'==document.location.protocol?"wss://"+document.location.host+"/web":"ws://"+document.location.host+"/web";
/*var docUrl=host+"/doc/index.html";*/
var host="http://localhost:8000/api";
var m={
    "tCollectTime":"T_COLLECT_TIME",
    "cCreateTime":"C_CREATE_TIME",
    "cUpdateTime":"C_UPDATE_TIME"
};
//上次弹窗发生时间
var lastAlertTime=0;
//上次弹窗内容
var lastAlertContent="";
//同样内容弹窗最少间隔
var MIN_ALERT_TIME_INTERVAL=3000;
var imageHost="http://192.168.0.123:8080/Img";



function closeWindow(window){
    window.window("close");
}
function openWindow(window){
    window.window("open");
}

var common={
     pageParameter:{

        pageSize: 5,//每页显示的记录条数，默认为5
        pageList: [5, 10, 13, 25],//可以设置每页记录条数的列表
        beforePageText: '第',//页数文本框前显示的汉字
        afterPageText: '页    共 {pages} 页',
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
    },
    createImageUrl:function(cameraId,collectTime){

        var d=new Date(collectTime);

        //20_2016-01-06_17-02-59.jpg
        var url=imageHost+"/"+cameraId+"_";
        var month= d.getMonth()+1;
        var day= d.getDate();
        var hour= d.getHours();
        var minute= d.getMinutes();
        var second= d.getSeconds();
        var timeStr= d.getFullYear()+"-"
            + (month<10?"0"+month:month)+"-"
            + (day<10?"0"+day:day)+"_"
            + (hour<10?"0"+hour:hour)+"-"
            + (minute<10?"0"+minute:minute)+"-"
            + (second<10?"0"+second:second)+".jpg";
        return url+timeStr;
    },
    loadTableData:function(data,tableId){

        var tableIdStr="#"+tableId;
        if(data.result){

            var wellFormData={

                total:data.extra,
                rows:data.data

            }
            $(tableIdStr).datagrid("loadData",wellFormData);

        }else{

            alert("加载数据失败")
        }


    },
    dateFormatter:function(value,rec,index){

        if(value!=undefined){

            var value1=Number(value);
            var d=new Date(value1);

            var month= d.getMonth()+1;
            var day= d.getDate();
            var hour= d.getHours();
            var minute= d.getMinutes();
            var second= d.getSeconds();
            var timeStr=(month<10?"0"+month:month)+"月"+(day<10?"0"+day:day)+"日 "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
            return  timeStr;


           // return unixTimestamp.toLocaleString();
        }
        return value;
    },
    getCurrentTime:function(){

    var obj=new Date();

    return obj.getTime();

    },
    vqdResultFormatter:function(value,rec,index){

        switch (value) {
            case 0: return "成功";
            case 1: return "创建线程失败";
            case 2: return "设备SDK初始化异常";
            case 3: return "登录设备失败";
            case 4: return "预览设备视频失败";
            case 5: return "回调视频流失败";
            case 6: return "取流超时";
            default: return undefined;
        }


    },


    //异常原因转义函数
    alarmCause:function(value,row,index){
        cause=row.cAlarmCause;
        var str="设备不在线";
        if(cause=="0"){
            return str;
        }
    },

    //异常来源钻转义函数
    alarmSrcFormatter:function(value,rec,index){
        switch(value){
            case 1: return "视频质量诊断";
            case 2: return "录像质量诊断";
            case 3: return "设备在线";
            case 4: return "服务在线";
            default: return undefined;

        }
    },

    //工单状态转义函数
    orderStatusFormatter:function(value,rec,index){

        switch(value){
            case 0: return "已上报";
            case 1: return "已确认";
            case 2: return "已挂起";
            case 3: return "已申请延期";
            case 4: return "待反馈";
            case 5: return "已结束";
            case 6: return "已驳回";
            default: return undefined;

        }
    },

    /*返回一个UserInfo对象*/
    getUserInfo:function(){

        var userInfoStr=this.getCookieByKey("userInfo");

        try{

            var userInfo=JSON.parse(userInfoStr);
            return userInfo;
        }catch (e){

            return null;
        }

        return null;


    },
    getCookieByKey:function(key){

        var strCookie=document.cookie;

       var reg=new RegExp("(^|)"+key+"=([^;]*)(;|$)");
        var value;
        if(value=document.cookie.match(reg)){

            return value[2];
        }else{

            return null;
        }

    },
    deleteCookieByKey:function(key){

        var date=new Date();
        date.setTime(date.getTime()-1000);
        document.cookie=key+"=a;expires="+date.toGMTString();
    },

    orderStrToBool:function(order){

    if(order=="asc"){

       return false;
    }else{

      return true;
    }
},
    serverStatusFormatter:function(value,rec,index){

        switch (value){
            case 0:
                return "正常";



            default :
                return "异常";
        }
    },
    serverTypeFormatter:function(value,rec,index){

        switch (value){
            case 0:
                return "网页服务器";

            case 1:
                return "管理中心服务器";

            case 2:
                return "视频诊断服务器";

            default :
                return "";
        }

    },
    createMenuItem:function(id,text,iconCls,state,loadChild,action){

        var menuItem=
            {

                "id":id,
                "text":text,
                "iconCls":iconCls,
                "state":state,
                "attributes":{
                    loadChild:loadChild,
                    action:action
                }
            }

        return menuItem;

    },
    toLoginPage:function(msg){

        $.messager.confirm({
            title:'提示',
            msg:msg,
            fn:function(isConfirmed){

                window.parent.location.href="/web/login.html";
            }

        })

    },
    onError:function(data){

    try{

        var resp=data.responseText;

        var msg=JSON.parse(resp);
        var httpStatus=data.status;
        if(httpStatus==403){


            common.toLoginPage("你还没有登录,请重新登录");

        }else if(httpStatus==404){

            common.alert("网络异常，请稍后再试");
          //  window.parent.location.href="/web/login.html";
        }
    }catch(e){

        common.alert("网络异常，请稍后再试");
        //alert("网络异常，请稍后再试");
     //   window.parent.location.href="/web/login.html";
            console.log(data.responseText);
    }finally{

    }




},
    alert:function(msg,title){

        var currentTime=this.getCurrentTime();
        if(lastAlertContent==msg){

            if((currentTime-lastAlertContent)<MIN_ALERT_TIME_INTERVAL){

                return ;
            }

        }
        lastAlertContent=currentTime;
        lastAlertContent=msg;
        $.messager.alert({

            title:title==undefined?"提示":title,
            msg:msg
        })

},

loadPage:function(url,onSuccess){

    $.ajax({
        url:url,
        type:"get",
        cache:false,
        success:onSuccess,
        error:this.OnError
    })

}

}
