
var brandService=BrandService();
var imageService=ImageService();

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



$(document).ready(function(){
    loadBrands();
    loadProductImg();
    loadInpireImg_all();
    $("#tab0").addClass("active");
});


var loadBrands=function(){
    brandService.getAllBrands(page_brands,function(data){
        console.log("加载的品牌信息如下");
        console.log(data.data);
        if(data.result){
            loadBrandInPanel(data.data);
            //localDb.brand=data.data;
            CreateQuery.allBrand=data.data;
            console.log('localDb.brand');
            console.log(localDb.brand)
        }
        else{
            alert(data.description)
        }
    })
};

var loadBrandInPanel=function(list){
    var item;
    var htmlStr="";
    for(var i=0;i<list.length;i++){
        item=list[i];
        if((i+1)%3==0){
            htmlStr+='<td>' +
            '<input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox" alt="'+item.brandIconUrl+'"/>' +
            '<img class="brandImg" src="'+item.brandIconUrl+'"/></td></tr>'

        }else if((i+1)%3==1){
            htmlStr+='<tr><td>' +
            '<input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox" alt="'+item.brandIconUrl+'"/>' +
            '<img class="brandImg" src="'+item.brandIconUrl+'"/></td>';

        }else{
            htmlStr+='<td>' +
            '<input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox" alt="'+item.brandIconUrl+'"/>' +
            '<img class="brandImg" src="'+item.brandIconUrl+'"/>' +
            '</td>'
        }
        if(i==list.length-1){
            if(htmlStr.indexOf('tr')==0){
                htmlStr+='</tr>'
            }
        }
    }
    $("#brandContainer").html(htmlStr);
};


$("#saveQuery").click(function(e){
    saveBrandIds();

});

function saveBrandIds(){
    var selectedBrand=$("#brandContainer input[type='checkbox']:checked");
    for(var i=0;i<selectedBrand.length;i++){
        brandIds.push(selectedBrand[i].id)
    }
    console.log(brandIds);
}

function doCheck(obj){
    obj.checked?c++:c--;
    if(c>CreateQuery.brandLimit){
        obj.checked=false;
        alert("已达到选择上限！");
        c--;
    }
    //选中的品牌信息保存
    CreateQuery.brandList.push(
        {
            //品牌id
            brandId:obj.id,
            //品牌icon的url
            brandIconUrl:obj.nextSibling.src,
            //此属性下的productList保存的是该品牌下所有的产品信息
            product:[

            ],
            //此属性下的productList保存的是该品牌下所有被选中的产品信息
            selProduct:[

            ]
        }
    );
    console.log("信息如下：");
    console.log(CreateQuery.brandList);
    loadProductImg(obj.id)
}

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

function saveSurvey(){
    surveyService.updateSurvey()
}
var INSP_TAB_INDEX=0;
$("#upload_inspire_type").val(INS_IMG_TYPE[INSP_TAB_INDEX]);
function loadInpireImg(){
    //for(var i=0;i<INS_IMG_TYPE.length;i++){
        imageService.getAllImages(page_inspireImg,INS_IMG_TYPE[INSP_TAB_INDEX],function(){},
            function(data){
                if(data.result){
                    if(data.dataCount>0){
                        var index= INS_IMG_TYPE.indexOf(data.data[0].imageType);
                        //console.log("获取的激发图片如下：");
                        //console.log(data.data);
                        CreateQuery.inspire_type[index].imgList=data.data;
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
                        CreateQuery.inspire_type[index].imgList=data.data;
                    }else{

                    }
                }
                else{
                    alert(data.description)
                }
            }
        );
    }

    //}

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