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
function ImageService(){
    var service=new Object();
    /*设置全局统一错误处理*/
    service.onError=common.onError;
    service.url=host+"/imageService";

  /*  按品牌列出其下所有的产品整体images*/
    service.getProductImagesByBrandId=function(brandId,onSuccess){
        var temp=this.url+"/getProductImagesByBrandId";
        $.ajax({
            url:temp,
            data:{
                brandId:brandId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

    /*  按产品列出与其相关的细节images。 parentId是上面获得的整体ID*/
    service.getDetailImagesByParentId=function(parentId,onSuccess){
        var temp=this.url+"/getDetailImagesByParentId";
        $.ajax({
            url:temp,
            data:{
                parentId:parentId
            },
            cache:false,
            type:"get",
            success:onSuccess,
            error:this.onError
        })
    };

    /*#按类型列出所有激发想象图像images  type = INDUSTRY,ANIMAL,BUILDING,ART,OTHERS*/
    service.getAllImages=function(page,type,onSuccess){
        var temp=this.url+"/getAllImages";
        var pageStr=JSON.stringify(page);
        $.ajax({

            url:temp,
            data:{
                page:pageStr,
                type:type
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };
    /*分页多条件排序查询摄像机信息*/
    service.uploadImage=function(type,file,onSuccess){
        var temp=this.url+"/getCameraInfoList";
        $.ajax({
            url:temp,
            data:{
                type:type,
                file:file
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
