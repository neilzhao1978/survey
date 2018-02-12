
/*t图片Service 对象(用于ajax接口调用)*/
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
    service.getAllImages=function(page,type,beforeSend,onSuccess){
        var temp=this.url+"/getAllImages";
        var pageStr=JSON.stringify(page);
        $.ajax({

            url:temp,
            data:{
                page:pageStr,
                type:type
            },
            cache:false,
            type:"get",
            beforeSend:beforeSend,
            success:onSuccess,
            error:this.onError
        })
    };
    /*上传image*/
    service.uploadImage=function(formID,onSuccess){
        var temp=this.url+"/uploadImage";
        var formIdStr="#"+formID;
        var formdata=new FormData($(formIdStr)[0]);
        $.ajax({
            url: temp,
            type: 'post',
            data: formdata,
            async:false,
            cache:false,
            contentType: false,
            processData: false,
            success: onSuccess,
            error:common.onError
        });
    };

  /*  删除images*/
  service.deleteImages=function(images,onSuccess){
    var temp=this.url+"/deleteImages";
    var objImages=JSON.stringify(images);
    $.ajax({
        headers:{
            'Accept':"*/*",
            'Content-Type':"application/json"
        },
        url:temp,
        data:objImages,
        ache:false,
        type:"post",
        success:onSuccess,
        error:this.onError
    })
  };
 
    return service;
}
