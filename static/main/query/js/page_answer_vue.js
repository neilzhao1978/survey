//Vue.component('product-grid',{
//    template:"#product-template",
//    props: {
//        data: Array
//    }
//});

var CreateQuery = new Vue({
    el: '#CreateQuery',
    data: {
        qName: "",
        qDesc: "",
        qTime: common.dateFormatter_hyphen(new Date()),
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
        allBrand:[

        ],
        brandList:[

        ],
        selProList:[

        ],
        answer_replyerName:"",
        answer_replyerPosition:'技术类',
        answer_brand:[

        ],
        answer_image:[

        ]

    },
    methods: {
        answer_doCheck_brand:function(){
            var obj=event.currentTarget;
            obj.checked?c++:c--;
            if(c>CreateQuery.brandLimit){
                obj.checked=false;
                alert("已达到选择上限！");
                c--;
            }else{
                //选中的品牌信息保存
                CreateQuery.answer_brand.push(
                    {
                        //品牌id
                        brandId:obj.id
                    }
                );
            }
        },
        answer_doCheck_product:function(){
            var obj=event.currentTarget;
            //选中的品牌信息保存
            CreateQuery.answer_image.push(
                {
                    //图片id
                    imageId:obj.id
                }
            );
        },
        answer_doCheck_productDetail:function(){

        },
        answer_doCheck_inspireImg:function(){
            var obj=event.currentTarget;
            //选中的品牌信息保存
            CreateQuery.answer_image.push(
                {
                    //图片id
                    imageId:obj.id
                }
            );
        },
        //品牌选择tab中的checkbox 选中、取消选中方法
        doCheck:function (){
            var obj=event.currentTarget;
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
                        CreateQuery.brandList.splice(i,1);
                        break
                    }
                }
                console.log(CreateQuery.brandList)
            }

        },
        //产品选择tab中的checkbox 选中、取消选中方法
        doCheck_pro: function(){
            var thisEle=event.currentTarget;
            //如果是选中；则将数据添加进vue的data中
            if(thisEle.checked){
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
            }
            //若是取消选中，则是将其从vue的data中剔除
            else{
                //在vue 的selProList中找到取消选中的产品信息；并将其剔除。
                for(i=0;i<CreateQuery.selProList.length;i++){
                    if(CreateQuery.selProList[i].imageUrl==thisEle.nextElementSibling.src){
                        CreateQuery.selProList.splice(i,1);
                        break
                    }
                }
                console.log(CreateQuery.selProList)

            }


        },

        //产品图片中的取消选中按钮
        cancelSelect:function(){
            var obj=event.currentTarget;
            for(var i=0;i<CreateQuery.brandList.length;i++) {
                if (CreateQuery.brandList[i].brandId == obj.name) {
                    for (var j = 0; j < CreateQuery.brandList[i].product.length; j++) {
                        //将选中状态设为选中
                        CreateQuery.brandList[i].product[j].checked = false;
                        var sel_bra_pro = CreateQuery.brandList[i].product[j];

                        //从selProList中删掉选中的图片
                        var flag = false;//false时，意为没有相同；反之相同；
                        var index=-1;
                        for (var k = 0; k < CreateQuery.selProList.length; k++) {
                            var selPro = CreateQuery.selProList[k];
                            if (selPro.imageId != sel_bra_pro.imageId) {
                                flag = false;
                            } else {
                                flag = true;
                                index=k;
                                break
                            }
                        }
                        if(index>=0){
                            CreateQuery.selProList.splice(index,1);
                        }

                    }
                    console.log(CreateQuery.selProList)
                } else {

                }
            }
        },
        //产品图片中的全部选中按钮
        selectAll:function(){
            var obj=event.currentTarget;
            for(var i=0;i<CreateQuery.brandList.length;i++){
                if(CreateQuery.brandList[i].brandId==obj.name){
                    for(var j=0;j<CreateQuery.brandList[i].product.length;j++){
                        //将选中状态设为选中
                        CreateQuery.brandList[i].product[j].checked=true;
                        var sel_bra_pro=CreateQuery.brandList[i].product[j];

                        //从selProList中加入选中的图片
                        var temp;
                        var flag=false;//false时，意为没有相同；反之相同；
                        for(var k=0;k<CreateQuery.selProList.length;k++){
                            var selPro=CreateQuery.selProList[k];
                            if(selPro.imageId!=sel_bra_pro.imageId){
                                flag=false;

                            }else{
                                flag=true;
                                break
                            }
                        }
                        //如果有相同的则不予理睬
                        if(flag){

                        }
                        //若没有相同的，则保存于selProList
                        else{
                            CreateQuery.selProList.push(sel_bra_pro);

                        }


                    }

                }else{

                }
            }
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


