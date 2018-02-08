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
        //qTime: common.dateFormatter_hyphen(new Date()),
        //发布时间；
        qYear: 2017,
        qMonth: 1,
        qDay: 1,
        brandLimit:2,
        inspire_type:[
            {
                jtabId:'#tab0',
                tabId:'tab0',
                typeId:'INDUSTRY',
                typeName:"工业产品类",
                limit:1,
                isSelected:false,
                //mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab1',
                tabId:'tab1',
                typeId:'ANIMAL',
                typeName:"动物类",
                limit:1,
                isSelected:false,
                //mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab2',
                tabId:'tab2',
                typeId:'BUILDING',
                typeName:"建筑类",
                limit:1,
                isSelected:false,
                //mode:1,
                imgList:[

                ]
            },
            {
                jtabId:'#tab3',
                tabId:'tab3',
                typeId:'ART',
                typeName:"艺术品类",
                limit:1,
                //mode:1,
                isSelected:false,
                imgList:[

                ]
            },
            {
                jtabId:'#tab4',
                tabId:'tab4',
                typeId:'OTHERS',
                typeName:"其他类",
                limit:1,
                isSelected:false,
                //mode:1,
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

        ],
        selProDetailList:[

        ]

    },
    computed:{
        //计算属性：根据年月日算出 时间
        qReleaseTime:function(){
            return this.qYear+"-"+this.qMonth+"-"+this.qDay+" "+"00:00:00"
        }
    },
    methods: {
        //选择品牌点击事件
        answer_doCheck_brand:function(){
            var obj=event.currentTarget;
            obj.checked?c++:c--;
            //若超过选择上限
            if(c>CreateQuery.brandLimit){
                obj.checked=false;
                alert("已达到选择上限！");
                c--;
            }
            //若未超过选择上限
            else{
                var item={brandId:obj.alt};
                //若是选择
                if(obj.checked){
                    //选中的品牌信息保存
                    CreateQuery.answer_brand.push(item);
                }
                //若是取消选择,则将其剔除
                else{
                    for(var i=0;i<CreateQuery.answer_brand.length;i++){
                        if(obj.alt==CreateQuery.answer_brand[i].imageId){
                            CreateQuery.answer_brand.splice(i,1);
                            break
                        }
                    }
                }
            }
            console.log(CreateQuery.answer_brand.length)
        },
        //选择产品（整体造型）点击事件
        answer_doCheck_product:function(){
            var obj=event.currentTarget;
            var item={imageId:obj.alt};
            //选中则保存
            if(obj.checked){
                //选中的品牌信息保存
                CreateQuery.answer_image.push(item);
            }
            //取消选中则剔除
            else{
                for(var i=0;i<CreateQuery.answer_image.length;i++){
                    if(obj.alt==CreateQuery.answer_image[i].imageId){
                        CreateQuery.answer_image.splice(i,1);
                        break
                    }
                }
            }
            console.log(CreateQuery.answer_image.length)
        },
        //选择产品细节点击事件
        answer_doCheck_productDetail:function(){
            var obj=event.currentTarget;
            var item={imageId:obj.alt};
            //选中则保存
            if(obj.checked){
                //选中的品牌信息保存
                CreateQuery.answer_image.push(item);
            }
            //取消选中则剔除
            else{
                for(var i=0;i<CreateQuery.answer_image.length;i++){
                    if(obj.alt==CreateQuery.answer_image[i].imageId){
                        CreateQuery.answer_image.splice(i,1);
                        break
                    }
                }
            }
            console.log(CreateQuery.answer_image.length)
        },
        //选择灵感图片点击事件
        answer_doCheck_inspireImg:function(){

            var obj=event.currentTarget;
            //获取当前点击的意向图片的类型
            var item={imageId:obj.alt};
            var type=obj.dataset.imageType;
            console.log(type);
            console.log(_c_inspireImg);
            var index=INS_IMG_TYPE.indexOf(type);
            obj.checked?_c_inspireImg[index]++:_c_inspireImg[index]--;
            if(_c_inspireImg[index]>CreateQuery.inspire_type[index].limit){
                obj.checked=false;
                alert("已达到选择上限！");
                _c_inspireImg[index]--;
            }
            else{
                //选中则保存
                if(obj.checked){
                    //选中的品牌信息保存
                    CreateQuery.answer_image.push(item);
                }
                //取消选中则剔除
                else{
                    for(var i=0;i<CreateQuery.answer_image.length;i++){
                        if(obj.alt==CreateQuery.answer_image[i].imageId){
                            CreateQuery.answer_image.splice(i,1);
                            break
                        }
                    }
                }
            }

            console.log(CreateQuery.answer_image.length)

        }
    }
});


