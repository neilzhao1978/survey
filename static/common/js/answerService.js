/*! @file 格式注释
 =============================================
 * Copyright (c) 2015,湖南华南光电科技股份有限公司
 * 【文件名】    common.css
 * 【文件实现功能】 运维监控视频诊断运维公共样式
 * 【相关文件】  无
 * 【功能模块和目的】通用
 * 【开发者及日期】 技术中心  2015.12.07
 * 【版本信息】   0.1
 * 【备注】 <其它说明>
 ==============================================
 * 【更改记录】
 * 日 期 版本 修改人 修改内容
 * YYYY/MM/DD X.Y <作者或修改者名> <修改内容>
 ============================================ * /
 /**
 * Created by virgil on 15-9-28.
 */
/*创建摄像机管理对象*/
function AnswerService(){
    var service=new Object();
    /*设置全局统一错误处理*/
    service.onError=common.onError;
    service.url=host+"/answerService";

  /*  添加摄像机*/
    service.getAllAnswerList=function(page,onSuccess){
        var temp=this.url+"/getAllAnswerList";
        var pageStr=JSON.stringify(page);
        $.ajax({
            url:temp,
            data:{
                page:pageStr
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
    /*分页多条件排序查询摄像机信息*/
    service.getCameraInfoList=function(page,onSuccess){
        var temp=this.url+"/getCameraInfoList";
        var pageStr=JSON.stringify(page);
        $.ajax({
            url:temp,
            data:{
                page:pageStr
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };


    /*根据组织id查询摄像机列表*/
    service.getCameraInfoListByOrgId=function(orgId,onSuccess){
        var temp=this.url+"/getCameraListByOrgId";
        var pageStr=JSON.stringify(page);
        $.ajax({
            url:temp,
            data:{
                orgId:orgId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };
    /*根据组织id查询摄像机列表*/
    service.getCameraInfoListByPlanIdAndOrgId=function(planId,orgId,onSuccess){
        var temp=this.url+"/getCameraInfoListByPlanIdAndOrgId";

        $.ajax({
            url:temp,
            data:{
                planId:planId,
                orgId:orgId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };
    service.applyVQDAlarmRuleTemplate=function(cameraIds,vQDAlarmRuleTemplateId,onSuccess){
        var temp=this.url+"/applyVQDAlarmRuleTemplate";
        var cameraIdsStr=JSON.stringify(cameraIds);//cameraId  -> string
        var vQDAlarmRuleTemplateIdLong=vQDAlarmRuleTemplateId;//vQDId  ->  long
        $.ajax({
            url:temp,
            data:{
                cameraIds:cameraIdsStr,
                vQDAlarmRuleTemplateId:vQDAlarmRuleTemplateIdLong
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };


    service.getDeviceInfoByCameraId=function(cameraId,onSuccess){


        var temp=this.url+"/getDeviceInfoByCameraId";

        $.ajax({
            url:temp,
            data:{
                cameraId:cameraId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };
    service.getExcelFieldList=function(onSuccess){

        var temp=this.url+"/getExcelFieldList";

        $.ajax({
            url:temp,
            data:{

            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };
    service.downloadExcelTemplate=function(ids){

        var temp=this.url+"/downloadExcelTemplate";

        var idsStr=JSON.stringify(ids);
        window.location.href=temp+"?ids="+idsStr;
    }
    /*分页多条件排序导出摄像机信息*/
    service.exportCameraInfoList=function(page){
        var temp=this.url+"/exportCameraInfoList";
        var pageStr=JSON.stringify(page);
        window.location.href=temp+"?page="+pageStr;

    };
    return service;
}
