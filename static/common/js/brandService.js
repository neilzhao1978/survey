
/*品牌Service  对象(用于ajax接口调用)*/
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

    /*获取所有图片接口*/
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
