
var brandService=BrandService();
var imageService=ImageService();
var surveyService=SurveyService();
var answerService=AnswerService();
var c=0;
var _c_inspireImg=[0,0,0,0,0];

var INS_IMG_TYPE = ["INDUSTRY","ANIMAL","BUILDING","ART","OTHERS"];

var surveyId=common.GetRequest();

$(document).ready(function(){

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
                    delete list.brands[i].images
                }
                CreateQuery.brandList=list.brands;
                //根据图片类型将其分类赋值于 CreateQuery.*
                for(i=0;i<list.images.length;i++){
                    if(list.images[i].imageType=="WHOLE"){
                        CreateQuery.selProList.push(list.images[i]);
                    }
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
                }
            }
        })
    }else{

    }
}


//提交answer
function submitAnswer(){
    var replyerName=CreateQuery.answer_replyerName;
    var replyerPosition=CreateQuery.answer_replyerPosition;
    var replyTime=common.dateFormatter_inverse(new Date());
    var brands=CreateQuery.answer_brand;
    var images=CreateQuery.answer_image;
    console.log(replyTime);
    console.log(replyerName);
    console.log(replyerPosition);
    console.log(surveyId);
    console.log(brands);
    console.log(images);

    if(replyerName==""){
        alert("您还未填写您的姓名！");
        return
    }
    if(replyerPosition==""){
        alert("您还未填写您的您的职位！");
        return
    }
    if(brands.length==0){
        alert("您还未选择品牌！");
        return
    }
    if(images.length==0){
        alert("您还未选择 整体造型、局部图片、意向图片！");
        return
    }

    answerService.addAnswer(brands,images,replyTime,replyerName,replyerPosition,surveyId,function(){},function(data){
        if(data.result){
            alert(data.description);
            window.location.assign("thankyou.html");
        }else{
            alert(data.description);
        }
    })

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