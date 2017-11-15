
var brandService=BrandService();
var imageService=ImageService();
var surveyService=SurveyService();

var c=0;
var _c=0;

var page_brands={
    pageNumber:1,
    pageSize:9999
};

var page_inspireImg={
    pageNumber:1,
    pageSize:9999
};

var brandIds=[];

var INS_IMG_TYPE = ["INDUSTRY","ANIMAL","BUILDING","ART","OTHERS"];

var localDb={
    brand:[
        //{
        //    brandId:'',
        //    brandIconUrl:'',
        //    brandName:'',
        //    desc:'',
        //    product:[
        //        {
        //            imageId:'',
        //            brandIconUrl:'',
        //            imageName:'',
        //            imageType:'',
        //            imageDesc:''
        //        },
        //        {
        //
        //        }
        //    ]
        //}
    ]
};

var surveyId=common.GetRequest();
var surveyStatus=1;

$(document).ready(function(){

    loadInpireImg_all();
    $("#tab0").addClass("active");
    $("#g_tab0").addClass("active");
    $("#releaseQueryCon").hide();

    loadSurveyInfo();

});

//如果是预览(编辑）某个问卷；则加载问卷信息
function loadSurveyInfo(){
    if(surveyId){
        surveyService.getSurveyDetail(surveyId,function(){},function(data){
            if(data.result){
                console.log("FastJson");
                console.log(FastJson.format(data));

                //返回的数据为$ref:指向行的调用fastJson.format将其转换为正常格式；
                var list=FastJson.format(data).data;

                //
                CreateQuery.qName=list.name;
                CreateQuery.qDesc=list.desc;
                CreateQuery.qTime=common.dateFormatter_hyphen(list.releaseTime);

                //将数据结构转为 此系统定义的数据格式
                for(var i=0;i<list.brands.length;i++){
                    //在list中加入不可或缺的selProduct属性；
                    list.brands[i].selProduct=[];
                    //查看brand-images下；所有的图片，发现与已配置的list.images中重复的则设置为 checked=true;其他设为false
                    for(var j=0;j<list.brands[i].images.length;j++){
                        var temp_brand_img=list.brands[i].images[j];
                        //将选中的产品图片，在品牌-产品对象中表示出来
                        var flag=true;
                        for(var k=0;k<list.images.length;k++){
                            var selImg=list.images[k];
                            if(temp_brand_img.imageId==selImg.imageId){
                                flag=true;
                                //发现某个品牌下选中的图片后，将其保存于selProduct下
                                list.brands[i].selProduct.push(selImg);
                                break
                            }else{
                                flag=false;
                            }
                        }
                        list.brands[i].images[j].checked=flag;
                    }
                    //重新定义个product类型；将image类型替代
                    list.brands[i].product=list.brands[i].images;
                    delete list.brands[i].images
                }
                CreateQuery.brandList=list.brands;
                //根据图片类型将其分类赋值于 CreateQuery.*
                for(i=0;i<list.images.length;i++){
                    //产品整体造型
                    if(list.images[i].imageType=="WHOLE"){
                        CreateQuery.selProList.push(list.images[i]);
                    }
                    //产品细节造型
                    else if(list.images[i].imageType=="DETAIL"){
                        CreateQuery.selProDetailList.push(list.images[i]);
                    }
                    //五种意向图片
                    else{
                        for(j=0;j<INS_IMG_TYPE.length;j++){
                            if(list.images[i].imageType==INS_IMG_TYPE[j]){
                                CreateQuery.inspire_type[j].imgList.push(list.images[i])
                            }
                        }
                    }
                    console.log(CreateQuery.inspire_type);
                }

                loadBrands();
            }
        })
    }else{
        loadBrands();
    }
}



var loadBrands=function(){
    brandService.getAllBrands(page_brands,function(data){
        console.log("加载的品牌信息如下");
        console.log(data.data);
        if(data.result){

            for(var i=0;i<data.data.length;i++){
                data.data[i].checked=false;
            }

            CreateQuery.allBrand=data.data;

            for(i=0;i<CreateQuery.brandList.length;i++){
                var id=CreateQuery.brandList[i].brandId;
                for(var j=0;j<CreateQuery.allBrand.length;j++){
                    if(CreateQuery.allBrand[j].brandId==id){
                        CreateQuery.allBrand[j].checked=true;
                        break
                    }
                }
            }
        }

        else{
            alert(data.description)
        }
    })
};
//


//保存问卷：同返回列表时提示的保存草稿保存草稿
$("#saveQuery").click(function(e){
    surveyStatus=1;
    saveOrNot(1);

});

//发布问卷
$("#releaseQuery").click(function(e){
    surveyStatus=2;
    saveOrNot(1);
});


//根据brandId查找Product信息，并将其保存于CreateQuery.brandList.product中
function loadProductImg(brandId){
    imageService.getProductImagesByBrandId(brandId,function(data){
        if(data.result){
            var list=data.data;

            //将产品信息保存于localDb.brand中
            for(var i=0;i<localDb.brand.length;i++){
                if(localDb.brand[i].brandId==brandId){
                    localDb.brand[i].product=list;
                    break
                }
            }
            //var htmlStr="";
            //for(i=0;i<list.length;i++){
            //    htmlStr+='<div class="col-lg-3"><input type="checkbox" class="brandChoiceChk" /><img class="brandProductImg" id="'+list[i].imageId+'" src="'+list[i].imageUrl+'"/></div>';
            //}
            //$("#_"+brandId).append('<div class="panel-body">'+htmlStr+'</div></div>');
            for(i=0;i<CreateQuery.brandList.length;i++){
                if(CreateQuery.brandList[i].brandId==brandId){
                    CreateQuery.brandList[i].product=list;
                    break
                }
            }
            console.log(CreateQuery.brandList)
        }
        else{

        }
    })
}

function loadProductDetail(productId){
    imageService.getDetailImagesByParentId(productId,function(data){
        if(data.result){
            for(var i=0;i<data.data.length;i++){
                CreateQuery.selProDetailList.push(data.data[i])
            }
            console.log("CreateQuery.selProDetailList");
            console.log(CreateQuery.selProDetailList);
            console.log("CreateQuery.selProList");
            console.log(CreateQuery.selProList);
        }
        else{
            alert(data.description)
        }
    })
}

function saveSurvey(){
    surveyService.updateSurvey()
}
var INSP_TAB_INDEX=0;
var INSP_TAB_INDEX_GAL=0;

$("#upload_inspire_type").val(INS_IMG_TYPE[INSP_TAB_INDEX]);

function loadInpireImg(){
    //for(var i=0;i<INS_IMG_TYPE.length;i++){
        imageService.getAllImages(page_inspireImg,INS_IMG_TYPE[INSP_TAB_INDEX_GAL],function(){},
            function(data){
                if(data.result){
                    if(data.dataCount>0){
                        var index= INS_IMG_TYPE.indexOf(data.data[0].imageType);
                        //CreateQuery.inspire_type[index].imgList=data.data;
                        CreateQuery.gallery[index].imgList=data.data;
                        //激发图片信息如下
                    }else{

                    }

                }
                else{
                    alert(data.description)
                }
            }
        );
    //}

}

function loadInpireImg_all(){
    //for(var i=0;i<INS_IMG_TYPE.length;i++){
    for(var i=0;i<INS_IMG_TYPE.length;i++){
        imageService.getAllImages(page_inspireImg,INS_IMG_TYPE[i],function(){},
            function(data){
                if(data.result){
                    if(data.dataCount>0){
                        var index= INS_IMG_TYPE.indexOf(data.data[0].imageType);
                        //CreateQuery.inspire_type[index].imgList=data.data;
                        CreateQuery.gallery[index].imgList=data.data;
                        console.log("CreateQuery.gallery");
                        console.log(CreateQuery.gallery)
                    }else{

                    }
                }
                else{
                    alert(data.description)
                }
            }
        );
    }
}

function submitPic(formID){
    if(formID=="form1"){

    }
    imageService.uploadImage(formID,function(data){
        if(data.result)
        {
            loadInpireImg();
            alert(data.description);
            //layer.confirm(data.description,{icon:-1,title:"提示",end:function(){}});
        }
        else{
            alert(data.description);
            //layer.confirm(data.description,{icon:-1,title:"提示",end:function(){}});
        }
    });
}

function openImgUpLoadWin(){
    $("#uploadInspireImgWin").modal('show');
}

function openGalleryWin(){
    $("#galleryWin").modal('show');
}

var inspireImgDescArr=new Array(8);
var inspireImgDesc="";
var ImgDescList=[
    {good:'精致',bad:'粗俗'},
    {good:'简洁',bad:'复杂'},
    {good:'圆润',bad:'硬朗'},
    {good:'灵敏',bad:'迟钝'},
    {good:'优雅',bad:'普通'},
    {good:'亲和',bad:'冷漠'},
    {good:'现代',bad:'传统'},
    {good:'安静',bad:'躁动'}
];

function buildInspireDesc(index,which){
    inspireImgDesc="";
    if(which==0){
        inspireImgDescArr[index]=ImgDescList[index].good;
    }else{
        inspireImgDescArr[index]=ImgDescList[index].bad;
    }
    for (i = inspireImgDescArr.length - 1;  i >=0; i--) {
        if (inspireImgDescArr[i] === undefined) {
            inspireImgDescArr.splice(i, 1);
        }
    }
    for(var i=0;i<inspireImgDescArr.length;i++){
        if(i==0){
            inspireImgDesc+=inspireImgDescArr[i];
        }
        else{
            inspireImgDesc+=","+inspireImgDescArr[i];
        }
    }
    console.log(inspireImgDesc);
    $("#inspireImgDesc").val(inspireImgDesc);
    console.log($("#inspireImgDesc").val())
}

//意向图片；点击不同的类，赋值index给INSP_TAB_INDEX
function gettabindex(index){
    INSP_TAB_INDEX=index;
    $("#upload_inspire_type").val(INS_IMG_TYPE[INSP_TAB_INDEX]);
    loadInpireImg();
}

//图库图片；点击不同的类，赋值index给INSP_TAB_INDEX_GAL
function gettabindex_gallery(index){
    INSP_TAB_INDEX_GAL=index;
    loadInpireImg();
}

//问卷配置：主tab切换事件
function tabClick(index){
    if(index==4){
        $("#saveQueryCon").hide();
        $("#releaseQueryCon").show()
    }
    else{
        $("#saveQueryCon").show();
        $("#releaseQueryCon").hide()
    }
}

function returnList(){
    $("#saveAlert").modal("show");
}

function saveOrNot(flag){
    //不保存
    if(flag==0){
        window.location='queryList.html';
    }
    //保存
    else{
        doSaveDraft();
    }
}

//确定保存草稿/发布：surveyStatus:1 保存草稿； surveyStatus：2 发 布
function doSaveDraft(){

    var name=CreateQuery.qName;
    var releaseTime=common.dateFormatter_inverse(new Date());
    //var releaseTime=common.dateFormatter_inverse(CreateQuery.qTime);
    //草稿
    //var status=1;
    var brandArr=[];
    for(var i=0;i<CreateQuery.brandList.length;i++){
        var brandObj={brandId:CreateQuery.brandList[i].brandId};
        brandArr.push(brandObj)
    }
    var imageArr=[];
    for(i=0;i<CreateQuery.selProList.length;i++){
        var Obj={imageId:CreateQuery.selProList[i].imageId};
        imageArr.push(Obj)
    }
    for(i=0;i<CreateQuery.selProDetailList.length;i++){
        Obj={imageId:CreateQuery.selProDetailList[i].imageId};
        imageArr.push(Obj)
    }
    for(i=0;i<CreateQuery.inspire_type.length;i++){
        for(var j=0;j<CreateQuery.inspire_type[i].imgList.length;j++){
            Obj={imageId:CreateQuery.inspire_type[i].imgList[j].imageId};
            imageArr.push(Obj)
        }

    }

    console.log(name);
    console.log(releaseTime);
    console.log(surveyStatus);
    console.log(surveyId);
    console.log(brandArr);
    console.log(imageArr);

    //console.log(CreateQuery.brandList);
    surveyService.updateSurvey(name,releaseTime,surveyStatus,surveyId,brandArr,imageArr,function(){},function(data){
        if(data.result){
            alert(data.description);
            window.location='queryList.html';
        }else{
            alert(data.description)
        }
    })
}


//function gallerySelCancel(){
//    for(var i=0;i<CreateQuery.inspire_type.length;i++){
//        CreateQuery.inspire_type[i].imgList=[];
//    }
//}

function inspireTypeImgCancel(){
    CreateQuery.inspire_type[INSP_TAB_INDEX].imgList=[];
}

//页面解决json中$ref问题
var FastJson = {
    isArray : function(a) {
        return "object" == typeof a
            && "[object array]" == Object.prototype.toString.call(a)
                .toLowerCase();
    },
    isObject : function(a) {
        return "object" == typeof a
            && "[object object]" == Object.prototype.toString.call(a)
                .toLowerCase();
    },
    format : function(a) {
        if (null == a)
            return null;
        "string" == typeof a && (a = eval("(" + a + ")"));
        return this._format(a, a, null, null, null);
    },
    _randomId : function() {
        return "randomId_" + parseInt(1E9 * Math.random());
    },
    _getJsonValue : function(a, c) {
        var d = this._randomId(), b;
        b = "" + ("function " + d + "(root){") + ("return root." + c + ";");
        b += "}";
        b += "";
        var e = document.createElement("script");
        e.id = d;
        e.text = b;
        document.body.appendChild(e);
        d = window[d](a);
        e.parentNode.removeChild(e);
        return d;
    },
    _format : function(a, c, d, b, e) {
        d || (d = "");
        if (this.isObject(c)) {
            if (c.$ref) {
                var g = c.$ref;
                0 == g.indexOf("$.")
                && (b[e] = this._getJsonValue(a, g.substring(2)));
                return
            }
            for ( var f in c)
                b = d, "" != b && (b += "."), g = c[f], b += f, this
                    ._format(a, g, b, c, f);
        } else if (this.isArray(c))
            for (f in c)
                b = d, g = c[f], b = b + "[" + f + "]", this._format(a, g,
                    b, c, f);
        return a;
    }
};