
var host="http://192.168.0.247:8000/api";

//上次弹窗内容
var lastAlertContent="";
//同样内容弹窗最少间隔
var MIN_ALERT_TIME_INTERVAL=3000;

//Array函数原型加入indexOf方法：根据数据元素的值获取其所在的位置index     PS：Global.js中也有
Array.prototype.indexOf=function(val){
    for(var i=0;i<this.length;i++){
        if(this[i]==val)return i;
    }
    return -1;
};

//Array函数原型加入remove方法：从数组中删除指定的元素     PS：Global.js中也有
Array.prototype.remove=function(val){
    var index=this.indexOf(val);
    if(index==-1){

    }else{
        this.splice(index,1)

    }
};

Array.prototype.del=function(n) {
//n表示第几项，从0开始算起。
//prototype为对象原型，注意这里为对象增加自定义方法的方法。
    if(n<0) {//如果n<0，则不进行任何操作。
        return this;
    }
    else{
        return this.slice(0,n).concat(this.slice(n+1,this.length));
    }
    /*
     　concat方法：返回一个新数组，这个新数组是由两个或更多数组组合而成的。
     　这里就是返回this.slice(0,n)/this.slice(n+1,this.length)
     组成的新数组，这中间，刚好少了第n项。
     　slice方法： 返回一个数组的一段，两个参数，分别指定开始和结束的位置。
     */
};

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
    surveyStatus:function(value,row,index){
        if(value==1){
            return '草稿'
        }
        else if(value==2){
            return '开放中'
        }
        else if(value==3){
            return '已关闭'
        }
        else{
            return '未知'
        }

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
    //毫秒转换成年月日 时分秒  以连词符-衔接
    dateFormatter_hyphen:function(value,rec,index){

        if(value!=undefined){

            var value1=Number(value);
            var d=new Date(value1);
            var year= d.getFullYear();
            var month= d.getMonth()+1;
            var day= d.getDate();
            var hour= d.getHours();
            var minute= d.getMinutes();
            var second= d.getSeconds();
            var timeStr=year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
            return  timeStr;

            // return unixTimestamp.toLocaleString();
        }
        return value;
    },
    //年月日时分秒转换成 毫秒
    dateFormatter_inverse:function(value,rec,index){
        if(value!=undefined){
            var dateTimeMilliSecond=Date.parse(value);
            if(isNaN(dateTimeMilliSecond)){
                var new_dateTimeMilliSecond=value.replace('-','/');
                new_dateTimeMilliSecond=new_dateTimeMilliSecond.replace('年','/');
                new_dateTimeMilliSecond=new_dateTimeMilliSecond.replace('月','/');
                new_dateTimeMilliSecond=new_dateTimeMilliSecond.replace('日','/');
                return (new Date(new_dateTimeMilliSecond)).getTime();
            }else{
                return dateTimeMilliSecond
            }
        }
        return value;
    },
    getCurrentTime:function(){

    var obj=new Date();

    return obj.getTime();

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
        alert(msg)
    },
    GetRequest:function(){
        var url = location.search;
        var index=url.indexOf("?");

        if (index != -1) {
            var str =url.substr(index+1);
        }else{
            str =""
        }
        return str
    }

};

$.ajaxSetup({
    //headers: {'Cookie' : document.cookie },
    crossDomain:true,
    xhrFields:{withCredentials:true},
    error:common.onError
});


