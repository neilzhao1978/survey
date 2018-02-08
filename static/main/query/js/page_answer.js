
var brandService=BrandService();
var imageService=ImageService();
var surveyService=SurveyService();
var answerService=AnswerService();
var c=0;
var _c_inspireImg=[0,0,0,0,0];

var page_inspireImg={
    pageNumber:1,
    pageSize:9999
};

var brandIds=[];

var INS_IMG_TYPE = ["INDUSTRY","ANIMAL","BUILDING","ART","OTHERS"];

//var localDb={
//    brand:[
//        //{
//        //    brandId:'',
//        //    brandIconUrl:'',
//        //    brandName:'',
//        //    desc:'',
//        //    product:[
//        //        {
//        //            imageId:'',
//        //            brandIconUrl:'',
//        //            imageName:'',
//        //            imageType:'',
//        //            imageDesc:''
//        //        },
//        //        {
//        //
//        //        }
//        //    ]
//        //}
//    ]
//};

//var surveyId=common.GetRequest();
var answerId=common.GetRequest();

$(document).ready(function(){

    //loadInpireImg_all();

    loadAnswerInfo();

});

//分页获取问卷的分页参数（bootstrapTable由于加载效率高，直接获取全部）
var page={
    pageNumber:1,//页数
    pageSize:9999,//每页获取的记录数
    orderByFieldName:"releaseTime",//排序字段名
    isDesc:true //true:递减，false:递增
};

var list;

//如果是预览(编辑）某个问卷；则加载问卷信息
function loadAnswerInfo(){
    if(answerId){
        answerService.getAllAnswerList(page,"",function(data){
            if(data.result){
                console.log("FastJson");
                console.log(FastJson.format(data));

                //返回的数据为$ref:指向行的调用fastJson.format将其转换为正常格式；
                var data_list=FastJson.format(data).data;

                for(var i=0;i<data_list.length;i++){
                    if(data_list[i].answerId==answerId){
                        list=data_list[i]
                    }
                }
                console.log("answerInfo");
                console.log(list);

                CreateQuery.replayerName=list.replayerName;
                CreateQuery.replayerPosition=list.replayerPosition;
                CreateQuery.answerId = list.answerId
                ////
                CreateQuery.qName=list.survey.name;
                //CreateQuery.qDesc=list.desc;
                //
                ////发布日期：年月日，赋值
                //var time=new Date(list.releaseTime);
                //CreateQuery.qYear=time.getFullYear();
                //CreateQuery.qMonth=time.getMonth()+1;
                //CreateQuery.qDay=time.getDate();

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


function returnToBoard(){
    window.location='board.html';
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