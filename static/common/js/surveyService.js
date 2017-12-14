
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
    service.updateSurvey=function(name,releaseTime,status,surveyId,brands,images,limits,beforeSend,onSuccess){
        var temp=this.url+"/updateSurvey";
        //var creatorStr=JSON.stringify({"email":"niel@123.com"});
        //var brandsStr=JSON.stringify(brands);
        //var imagesStr=JSON.stringify(images);
        var allObj={
            creator:{"email":"niel@123.com"},
            name:name,
            releaseTime:releaseTime,
            status:status,
            surveyId:surveyId,
            brands:brands,
            images:images,
            maxUserBrandCount:limits.maxUserBrandCount,
            maxUserIndustryImageCount:limits.maxUserIndustryImageCount,
            maxUserAnimalImageCount:limits.maxUserAnimalImageCount,
            maxUserBuildingImageCount:limits.maxUserBuildingImageCount,
            maxUserArtImageCount:limits.maxUserArtImageCount,
            maxUserOthersImageCount:limits.maxUserOthersImageCount

        };
        var allObjStr=JSON.stringify(allObj);
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

    /* 打开关闭survey 修改时填已有的surveyId.status: 1:草稿，2：发布，3：关闭  9： 删除*/
    service.closeOpenSurvey=function(surveyId,status,beforeSend,onSuccess){
        var temp=this.url+"/closeOpenSurvey";
        //var creatorStr=JSON.stringify({"email":"niel@123.com"});
        //var brandsStr=JSON.stringify(brands);
        //var imagesStr=JSON.stringify(images);
        var allObj={
            creator:{"email":"niel@123.com"},
            status:status,
            surveyId:surveyId,
        };
        var allObjStr=JSON.stringify(allObj);
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

    /*  删除问卷*/
    service.deleteSurvey=function(rowInfo,beforeSend,onSuccess){
        var temp=this.url+"/deleteSurvey";
        var allObj={
            creator:{"email":"niel@123.com"},
            name:rowInfo.name,
            releaseTime:rowInfo.releaseTime,
            status:rowInfo.status,
            surveyId:rowInfo.surveyId
        };
        var allObjStr=JSON.stringify(allObj);

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

    /*复制survey*/
    service.copySurvey=function(surveyId,beforeSend,onSuccess){
        var temp=this.url+"/copySurvey";
        $.ajax({
            url:temp,
            data:{
                surveyId:surveyId
            },
            cache:false,
            type:"get",
            beforeSend:beforeSend,
            success:onSuccess,
            error:this.onError
        })
    };

    service.getQRcode=function(path){
        var temp=this.url+"/getQRcode"+"?pathStringCode="+path;
        return temp

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
    service.getSurveyDetail=function(surveyId,beforeSend,onSuccess){
        var temp=this.url+"/getSurveyDetail";
        $.ajax({

            url:temp,
            data:{
                surveyId:surveyId
            },
            cache:false,
            type:"get",
            beforeSend:beforeSend,
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
