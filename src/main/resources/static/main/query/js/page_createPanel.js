
var brandService=BrandService();
var imageService=ImageService();
var c=0,limit=9999;
var page_brands={
    pageNumber:1,
    pageSize:9999
};
var brandIds=[];

$(document).ready(function(){
    loadBrands();
    $("#brandChoiceLimit").numberspinner({
        onChange:function(newValue,oldValue){
            limit=newValue
        }
    });

    $("#tabs").tabs({
        onSelect:function(title,index){
            //如果选择了“选择产品图片”选项卡则进行下列的操作
            if(index==2){

            }
        }
    })

});


var loadBrands=function(){
    brandService.getAllBrands(page_brands,function(data){
        console.log("加载的品牌信息如下");
        console.log(data.data);
        if(data.result){
            loadBrandInPanel(data.data);
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
            htmlStr+='<td><input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox"/><img class="brandImg" src="'+item.brandIconUrl+'"/></td></tr>'

        }else if((i+1)%3==1){
            htmlStr+='<tr><td><input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox"/><img class="brandImg" src="'+item.brandIconUrl+'"/></td>';

        }else{
            htmlStr+='<td><input onClick="doCheck(this)" class="brandChoiceChk" id="'+item.brandId+'" type="checkbox"/><img class="brandImg" src="'+item.brandIconUrl+'"/></td>'
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

function doCheck(obj) {
    obj.checked?c++:c--;
    if(c>limit){
        obj.checked=false;
        alert("已达到选择上限！");
        c--;
    }
    var brandInfoStr=obj.nextElementSibling.parentElement.innerHTML;
    var index=brandInfoStr.indexOf("<img");
    brandInfoStr=brandInfoStr.substr(index);
    $("#brand_product").append('<div id="_'+obj.id+'">'+brandInfoStr+'<button>全选</button><button>取消选择</button><hr/><br/></div>');
    loadProductImg(obj.id)
}


function loadProductImg(brandId){
    imageService.getProductImagesByBrandId(brandId,function(data){
        if(data.result){
            var list=data.data;
            console.log('相应品牌的产品如下！');
            console.log(data.data);
            var htmlStr="";
            for(var i=0;i<list.length;i++){
                htmlStr+='<div><input type="checkbox" class="brandChoiceChk" /><img class="brandProductImg" id="'+list[i].imageId+'" src="'+list[i].imageUrl+'"/>';
            }
            $("#_"+brandId).append(htmlStr)

        }
        else{

        }
    })

}

function saveSurvey(){
    surveyService.updateSurvey()
}