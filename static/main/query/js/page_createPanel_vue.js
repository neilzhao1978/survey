//Vue.component('product-grid',{
//    template:"#product-template",
//    props: {
//        data: Array
//    }
//});

var CreateQuery = new Vue({
    el: '#CreateQuery',
    data: {
        qName: "2017压路机调查问卷",
        qDesc: "此问卷用于开发测试阶段",
        qTime: "2017-11-09 12:13:14",
        brandLimit:2,
        inspire_type:[
            {
                jtabId:'#tab0',
                tabId:'tab0',
                typeId:'INDUSTRY',
                typeName:"工业产品类",
                limit:2,
                mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab1',
                tabId:'tab1',
                typeId:'ANIMAL',
                typeName:"动物类",
                limit:2,
                mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab2',
                tabId:'tab2',
                typeId:'BUILDING',
                typeName:"建筑类",
                limit:1,
                mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab3',
                tabId:'tab3',
                typeId:'ART',
                typeName:"艺术品类",
                limit:2,
                mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab4',
                tabId:'tab4',
                typeId:'OTHERS',
                typeName:"其他类",
                limit:3,
                mode:1,
                imgList:[

                ]
            }
        ],
        type_0:{
            typeId:'INDUSTRY',
            typeName:"工业产品类",
            limit:2,
            mode:1,
            imgList:[

            ]
        },
        type_1:{
            typeId:'ANIMAL',
            typeName:"动物类",
            limit:2,
            mode:1,
            imgList:[

            ]
        },
        type_2:{
            typeId:'BUILDING',
            typeName:"建筑类",
            limit:1,
            mode:1,
            imgList:[

            ]
        },
        type_3:{
            typeId:'ART',
            typeName:"艺术品类",
            limit:2,
            mode:1,
            imgList:[

            ]
        },
        type_4:{
            typeId:'OTHERS',
            typeName:"其他类",
            limit:3,
            mode:1,
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

        doCheck:function (){
            var obj=event.currentTarget;
            //obj.checked?c++:c--;
            //if(c>CreateQuery.brandLimit){
            //    obj.checked=false;
            //    alert("已达到选择上限！");
            //    c--;
            //}
            if(obj.checked){
                //选中的品牌信息保存
                CreateQuery.brandList.push(
                    {
                        //品牌id
                        brandId:obj.alt,
                        //品牌icon的url
                        brandIconUrl:obj.nextElementSibling.src,
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
                loadProductImg(obj.alt)
            }else{
                for(var i=0;i<CreateQuery.brandList.length;i++){
                    if(CreateQuery.brandList[i].brandId==obj.alt){
                        CreateQuery.brandList.del(i);
                        break
                    }
                }
                console.log(CreateQuery.brandList)
            }

        },

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
        },
        //问卷预览中的
        preview_doCheck:function (){
            var obj=event.currentTarget;
            obj.checked?c++:c--;
            if(c>CreateQuery.brandLimit){
                obj.checked=false;
                alert("已达到选择上限！");
                c--;
            }
        }
    }
});


