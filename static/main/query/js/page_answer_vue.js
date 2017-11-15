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

        ],
        selProDetailList:[

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
            var obj=event.currentTarget;
            //选中的品牌信息保存
            CreateQuery.answer_image.push(
                {
                    //图片id
                    imageId:obj.id
                }
            );
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


