//Vue.component('product-grid',{
//    template:"#product-template",
//    props: {
//        data: Array
//    }
//});

var CreateQuery = new Vue({
    el: '#CreateQuery',
    data: {
        qName: "测试问卷",
        qDesc: "此问卷用于开发测试阶段",
        qTime: "2017-11-09 12:13:14",
        brandLimit:999,
        type_1:{
            typeId:'INDUSTRY',
            typeName:"工业产品类",
            limit:"",
            imgList:[

            ]
        },
        type_2:{
            typeId:'ANIMAL',
            typeName:"动物类",
            imgList:[

            ]
        },
        type_3:{
            typeId:'BUILDING',
            typeName:"建筑类",
            imgList:[

            ]
        },
        type_4:{typeId:'ART',
            typeName:"艺术品类",
            imgList:[

            ]
        },
        type_5:{
            typeId:'OTHERS',
            typeName:"其他类",
            imgList:[

            ]
        },

        allBrand:[

        ],
        brandList:[

        ],
        selProList:[

        ]
    },
    methods: {
        doCheck_pro: function(){
            var thisEle=event.currentTarget;
            console.log(event.currentTarget);
            for(var i=0;i<CreateQuery.brandList.length;i++){
                //遍历brandList.找到对应id的brandList信息；
                if(CreateQuery.brandList[i].brandId==thisEle.alt){
                    for(var j=0;j<CreateQuery.brandList[i].product.length;j++){
                        //id为this.alt的brandList下的product中找到imageUrl为选中的图片路径的记录，并将其保存于selProduct属性中
                        if(CreateQuery.brandList[i].product[j].imageUrl==thisEle.nextElementSibling.src){
                            //选中的产品列表保存（放入相应的brandList下的selProduct中）
                            CreateQuery.brandList[i].selProduct.push(CreateQuery.brandList[i].product[j]);
                            //选中的产品列表保存（保存于无序列表）
                            CreateQuery.selProList.push(CreateQuery.brandList[i].product[j]);
                            //
                            console.log(CreateQuery.brandList[i]);
                            break
                        }
                    }
                }
            }
            console.log(CreateQuery.brandList)

        },
        //产品图片中的取消选中按钮
        cancelSelect:function(){
            var thisEle=event.currentTarget;

            alert("取消选中");
            console.log(thisEle.parentElement.nextElementSibling.getElementsByTagName("input"));

        },
        //产品图片中的全部选中按钮
        selectAll:function(){
            alert("全部选中")
        }

    }
});


var searchProInfoById=function(bId,pId){

}

