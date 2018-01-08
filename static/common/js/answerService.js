
/*创建问Answer Service 对象(用于ajax接口调用)*/
function AnswerService(){
    var service=new Object();
    /*设置全局统一错误处理*/
    service.onError=common.onError;
    service.url=host+"/answerService";

  /*  获取所有的问卷回复列表*/
    service.getAllAnswerList=function(page,surveyId,onSuccess){
        var temp=this.url+"/getAllAnswerList";
        var pageStr=JSON.stringify(page);
        $.ajax({
            url:temp,
            data:{
                page:pageStr,
                surveyId:surveyId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

    /*  增加answer*/
    service.addAnswer=function(brands,images,replyTime,replyerName,replyerPosition,surveyId,beforeSend,onSuccess){
        var temp=this.url+"/addAnswer";
        var allObj={
            brands:brands,
            images:images,
            replyTime:replyTime,
            replyerName:replyerName,
            replyerPosition:replyerPosition,
            survey:{
                surveyId:surveyId
            }
        };
        var allObjStr=JSON.stringify(allObj);
        console.log(allObjStr);
        $.ajax({
            headers:{
                'Accept':"*/*",
                'Content-Type':"application/json"
            },
            url:temp,
            data:allObjStr,
            cache:false,
            type:"post",
            beforeSend:beforeSend,
            success:onSuccess,
            error:this.onError
        })
    };

    /*更新answer*/
    service.updateAnswer=function(answerId,brands,images,replayerName,replayerPosition,replyTime,replyerName,replyerPosition,survey,onSuccess){
        var temp=this.url+"/updateAnswer";
        var idsStr=JSON.stringify(ids);
        $.ajax({
            url:temp,
            data:{
                answerId:answerId,
                brands:brands,
                images:images,
                replayerName:replayerName,
                replayerPosition:replayerPosition,
                replyTime:replyTime,
                replyerName:replyerName,
                replyerPosition:replyerPosition,
                survey:survey
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };

    return service;
}
