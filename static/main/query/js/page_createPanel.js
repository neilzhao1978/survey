window.onload = function () {
    new uploadPreview({UpBtn: "up_img", DivShow: "imgdiv", ImgShow: "imgShow"});
}
//引用所需ajax 对象
var brandService=BrandService();
var imageService=ImageService();
var surveyService=SurveyService();

var c=0;
var _c_inspireImg=[0,0,0,0,0];

var page_brands={
    pageNumber:1,
    pageSize:9999
};

var page_inspireImg={
    pageNumber:1,
    pageSize:9999
};

var brandIds=[];

//定义意向图片-图库的类型数组
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

//从url路径中提取suryveyId
var surveyId=common.GetRequest();
var surveyStatus=1;

$(document).ready(function(){

    //页面元素初始化加载
    $("#tab0").addClass("active");
    $("#g_tab0").addClass("active");
    $("#releaseQueryCon").hide();

    //加载survey信息
    loadSurveyInfo();

    //基本设置中的：时间选择器插件初始化
    var timepickerObj=new _timepicker("year","month","day");
    timepickerObj.init();

    //$('#defaultForm').bootstrapValidator();

    //加载图库中的所有图片
    loadInpireImg_all();
});

//如果是预览(编辑）某个问卷；则加载问卷信息
function loadSurveyInfo(){
    //如果survyId存在（即编辑survey）则加载survey的详情
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
                //CreateQuery.qTime=common.dateFormatter_hyphen(list.releaseTime);

                //发布日期：年月日，赋值
                var time=new Date(list.releaseTime);
                CreateQuery.qYear=time.getFullYear();
                CreateQuery.qMonth=time.getMonth()+1;
                CreateQuery.qDay=time.getDate();

                //获取并赋值 品牌选择上限
                if(list.maxUserBrandCount){
                    CreateQuery.brandLimit=list.maxUserBrandCount;
                }

                //意向图片选择上限赋值
                if(list.maxUserIndustryImageCount){
                    CreateQuery.inspire_type[0].limit=list.maxUserIndustryImageCount;
                }
                if(list.maxUserAnimalImageCount){
                    CreateQuery.inspire_type[1].limit=list.maxUserAnimalImageCount;
                }
                if(list.maxUserBuildingImageCount){
                    CreateQuery.inspire_type[2].limit=list.maxUserBuildingImageCount;
                }
                if(list.maxUserArtImageCount){
                    CreateQuery.inspire_type[3].limit=list.maxUserArtImageCount;
                }
                if(list.maxUserOthersImageCount){
                    CreateQuery.inspire_type[4].limit=list.maxUserOthersImageCount;
                }

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
                    delete list.brands[i].images;
                    CreateQuery.brandList.push(list.brands[i])
                }


                //根据图片类型将其分类赋值于 CreateQuery.*
                for(i=0;i<list.images.length;i++){
                    //产品整体造型
                    if(list.images[i].imageType=="WHOLE"){
                        CreateQuery.selProList.push(list.images[i]);
                    }
                    //产品细节造型
                    else if(list.images[i].imageType=="PART"){
                        CreateQuery.selProDetailList.push(list.images[i]);
                    }
                    //五种意向图片
                    else{
                        for(j=0;j<INS_IMG_TYPE.length;j++){
                            if(list.images[i].imageType==INS_IMG_TYPE[j]){
                                CreateQuery.inspire_type[j].imgList.push(list.images[i]);
                                CreateQuery.inspire_type[j].isSelected=true;
                            }
                        }
                    }
                    //console.log(CreateQuery.inspire_type);
                }

                loadBrands();
            }
        })
    }
    //如果surveyId不存在（即新建一个工单）,直接获取品牌信息
    else{
        loadBrands();
    }
}


//加载品牌信息，并将其赋值于vue实例
var loadBrands=function(){
    brandService.getAllBrands(page_brands,function(data){
        //console.log("加载的品牌信息如下");
        //console.log(data.data);
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
            //将产品信息根据品牌ID 保存于vue实例对应的brandList中
            for(i=0;i<CreateQuery.brandList.length;i++){
                if(CreateQuery.brandList[i].brandId==brandId){
                    CreateQuery.brandList[i].product=list;
                    break
                }
            }
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
        }
        else{
            alert(data.description)
        }
    })
}

function saveSurvey(){
    surveyService.updateSurvey()
}

//主页面中：意向图片选项卡中的，五种类型意向图片 当前选中的tab index
var INSP_TAB_INDEX=0;
//图库窗口：图片选项卡中的，五种类型图片的tab index
var INSP_TAB_INDEX_GAL=0;

//加载意向图片
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

//加载图库中的所有类型的图片
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
                        for(var i=0;i<CreateQuery.gallery[index].imgList.length;i++){
                            CreateQuery.gallery[index].imgList[i].isSelected=false
                        }
                        //console.log("CreateQuery.gallery");
                        //console.log(CreateQuery.gallery)
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

/**  上传图片到图库 ***/
//打开图片上传窗口
function openImgUpLoadWin(){
    $("#uploadInspireImgWin").modal('show');
}
//上传时，隐藏的字段：用于指定当前选中的图片的类型
$("#upload_inspire_type").val(INS_IMG_TYPE[INSP_TAB_INDEX]);

//上传图片到图库
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

//打开图库窗口
function openGalleryWin(){
    $("#galleryWin").modal('show');
    //打开图库后，根据意向图片的当前的类型tab，指定图库中的对应类型tab
    $("#galleryTab li").removeClass("active");
    $("#galleryTab li:eq("+INSP_TAB_INDEX+")").addClass("active");
    $("#galleryTabContent .tab-pane").removeClass("active");
    $("#galleryTabContent .tab-pane:eq("+INSP_TAB_INDEX+")").addClass("active");
    INSP_TAB_INDEX_GAL=INSP_TAB_INDEX;

}

var inspireImgDescArr=new Array(3);
var inspireImgDesc="";
var ImgDescList=[
    //{good:'精致',bad:'粗俗'},
    {good:'简洁',bad:'复杂'},
    {good:'圆润',bad:'硬朗'},
    //{good:'灵敏',bad:'迟钝'},
    //{good:'优雅',bad:'普通'},
    //{good:'亲和',bad:'冷漠'},
    {good:'现代',bad:'传统'}
    //{good:'安静',bad:'躁动'}
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

//返回survey列表页面的提示信息
function returnList(){
    $("#saveAlert").modal("show");
}

//保存或不保存
function saveOrNot(flag){
    //不保存
    if(flag==0){
        //不执行任何保存操作，直接将页面跳转到surveyList页面
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
    var desc=CreateQuery.qDesc;
    if(name==""){
        alert("问卷名称未填写，请录入后再保存！");
        return
    }
    //强制转换brandLimit类型
    CreateQuery.brandLimit = Number(CreateQuery.brandLimit);
    if(typeof CreateQuery.brandLimit =="number"){
        if(CreateQuery.brandLimit>0){

        }else{
            alert("用户选择上限必须大于0！");
            return
        }
    }
    else{
        alert("用户选择上限必须是正整数！");
        return
    }
    var releaseTime=common.dateFormatter_inverse(CreateQuery.qReleaseTime);
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

    //console.log(name);
    //console.log(releaseTime);
    //console.log(surveyStatus);
    //console.log(surveyId);
    //console.log(brandArr);
    //console.log(imageArr);

    //console.log(CreateQuery.brandList);
    var limits={
        maxUserBrandCount:          CreateQuery.brandLimit,
        maxUserIndustryImageCount:  CreateQuery.inspire_type[0].limit,
        maxUserAnimalImageCount:    CreateQuery.inspire_type[1].limit,
        maxUserBuildingImageCount:  CreateQuery.inspire_type[2].limit,
        maxUserArtImageCount:       CreateQuery.inspire_type[3].limit,
        maxUserOthersImageCount:    CreateQuery.inspire_type[4].limit
    };

    surveyService.updateSurvey(name,desc,releaseTime,surveyStatus,surveyId,brandArr,imageArr,limits,function(){},function(data){
        if(data.result){
            alert("问卷保存成功!");
            window.location='queryList.html';
        }else{
            alert("问卷保存失败！");
        }
    })
}

//清空意向图片，提示窗口弹出
function emptyInspireAlert(){
    $("#emptyAlert").modal('show')
}

//确定清空当前选中类型的意向图片
function inspireTypeImgCancel(){
    CreateQuery.inspire_type[INSP_TAB_INDEX].imgList=[];
    //由于没有图片，所以设置isSelected为false,用于预览中的隐藏
    CreateQuery.inspire_type[INSP_TAB_INDEX].isSelected=false;

    //清空图库中的选中状态;
    var len=CreateQuery.gallery[INSP_TAB_INDEX].imgList.length;
    for(var i=0;i<len;i++){
        CreateQuery.gallery[INSP_TAB_INDEX].imgList[i].isSelected=false
    }

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

//基本设置中的：时间选择器插件
var _timepicker=function(yearId,monthId,dayId){

    function computeMonthDays(year,month){

        if([1,3,5,7,8,10,12].indexOf(month)!=-1){
            return 31
        }
        else if([4,6,9,11].indexOf(month)!=-1){
            return 30
        }
        else if(month==2){
            if(year%4!=0){
                return 29
            }
            else{
                return 28
            }
        }
        else{
            return false
        }

    }

    var obj={
        yearInit:function(){
            $("#"+yearId).change(function(){
                if($("#"+monthId).val()==""){
                    $("#"+dayId).html("")
                }
                else{
                    var year_=parseInt($("#"+yearId).val());
                    var month_=parseInt($("#"+monthId).val());
                    var days=computeMonthDays(year_,month_);
                    var htmlStr="";
                    for(var i=1;i<=days;i++){
                        htmlStr+='<option>'+i+'</option>'
                    }


                    $("#"+dayId).html(htmlStr)
                }
            });
        },
        monthInit:function(){
            $("#"+monthId).change(function(){
                if($("#"+yearId).val()==""){
                    $("#"+dayId).html("")
                }
                else{
                    var year_=parseInt($("#"+yearId).val());
                    var month_=parseInt($("#"+monthId).val());
                    var days=computeMonthDays(year_,month_);
                    var htmlStr="";
                    for(var i=1;i<=days;i++){
                        htmlStr+='<option>'+i+'</option>'
                    }

                    $("#"+dayId).html(htmlStr)
                }
            });
        },
        dayInit:function(){

        },
        init:function(){
            obj.yearInit();
            obj.monthInit();
        }
    };
    return obj
};

