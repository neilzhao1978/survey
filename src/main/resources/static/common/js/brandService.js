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
function BrandService(){
    var service=new Object();
    /*设置全局统一错误处理*/
    service.onError=common.onError;
    service.url=host+"/brandService";

  /*  列出所有brands */
    service.getAllBrands=function(page,onSuccess){
        var temp=this.url+"/getAllBrands";
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

    /*  获取品牌详情图片*/
    service.getDetailImagesByParentId=function(cameraInfo,onSuccess){
        var temp=this.url+"/getDetailImagesByParentId";
        var cameraInfoStr=JSON.stringify(cameraInfo);
        $.ajax({
            url:temp,
            data:{
                cameraInfo:cameraInfoStr
            },
            cache:false,
            type:"post",
            success:onSuccess,
            error:this.onError
        })
    };

    /*根据探针id删除摄像机*/
    service.getAllImages=function(ids,onSuccess){
        var temp=this.url+"/getAllImages";
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
    /*分页多条件排序查询摄像机信息*/
    service.uploadImage=function(page,onSuccess){
        var temp=this.url+"/uploadImage";
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
    service.deleteImage=function(orgId,onSuccess){
        var temp=this.url+"/deleteImage";
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

    return service;
}
