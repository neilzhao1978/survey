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
function SurveyService(){
    var service=new Object();
    /*设置全局统一错误处理*/
    service.onError=common.onError;
    service.url=host+"/surveyService";
  /*  获取所有问卷列表*/
    service.getAllSurveys=function(page,onSuccess){
        var temp=this.url+"/getAllSurveys";
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

    /*  增加、更新问卷*/
    service.updateSurvey=function(name,releaseTime,status,surveyId,brands,images,onSuccess){
        var temp=this.url+"/updateSurvey";
        var creatorStr=JSON.stringify({"email":"niel@123.com"});
        var brandsStr=JSON.stringify(brands);
        var imagesStr=JSON.stringify(images);
        $.ajax({
            url:temp,
            data:{
                creator:creatorStr,
                name:name,
                releaseTime:releaseTime,
                status:status,
                surveyId:surveyId,
                brands:brandsStr,
                images:imagesStr

            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };

    /*  删除问卷*/
    service.deleteSurvey=function(name,releaseTime,status,surveyId,onSuccess){
        var temp=this.url+"/deleteSurvey";
        var creatorStr=JSON.stringify({"email":"niel@123.com"});
        $.ajax({
            url:temp,
            data:{
                creator:creatorStr,
                name:name,
                releaseTime:releaseTime,
                status:status,
                surveyId:surveyId
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };


  /*获取结果*/
    service.getSurveyResult=function(ids,onSuccess){
        var temp=this.url+"/getSurveyResult";
        var idsStr=JSON.stringify(ids);
        $.ajax({

            url:temp,
            data:{
                ids:idsStr
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };

  /*获取明细*/
    service.getSurveyDetail=function(surveyId,onSuccess){
        var temp=this.url+"/getSurveyDetail";
        $.ajax({

            url:temp,
            data:{
                surveyId:surveyId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

  /*同步品牌*/
    service.sychDb=function(surveyId,onSuccess){
        var temp=this.url+"/sychDb";
        $.ajax({

            url:temp,
            data:{
                surveyId:surveyId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

  /*获取结果imageType=WHOLE,DETAIL,INSPIRE*/
    service.getSurveyResult=function(surveyId,imageType,onSuccess){
        var temp=this.url+"/getSurveyResult";
        $.ajax({

            url:temp,
            data:{
                surveyId:surveyId,
                imageType:imageType
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

    return service;
}
