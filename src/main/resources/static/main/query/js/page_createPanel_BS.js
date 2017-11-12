
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
    loadInpireImg();
});


var loadBrands=function(){
    brandService.getAllBrands(page_brands,function(data){
        console.log("加载的品牌信息如下");
        console.log(data.data);
        if(data.result){
            loadBrandInPanel(data.data);
            localDb.brand=data.data;
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

function loadInpireImg(){
    for(var i=0;i<INS_IMG_TYPE.length;i++){
        imageService.getAllImages(page_inspireImg,INS_IMG_TYPE[i],function(){},
            function(data){
                if(data.result){
                    console.log("获取的激发图片如下：")
                    console.log(data.data)
                }
                else{
                    alert(data.description)
                }
            }
        )
    }

}