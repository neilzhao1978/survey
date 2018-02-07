//Vue.component('product-grid',{
//    template:"#product-template",
//    props: {
//        data: Array
//    }
//});

//基础设置中的发布时间：年份参数，可选择2017——2025年
var t_years=[2017,2018,2019,2020,2021,2022,2023,2024,2025];
//月份信息
var t_months=[1,2,3,4,5,6,7,8,9,10,11,12];

var inspireImgLimits=[
    "maxUserIndustryImageCount",
    "maxUserAnimalImageCount",
    "maxUserBuildingImageCount",
    "maxUserArtImageCount",
    "maxUserOthersImageCount"
];

var CreateQuery = new Vue({
    el: '#CreateQuery',
    data: {
        //问卷名称
        qName: "",
        //问卷描述
        qDesc: "",
        //发布时间；
        qYear: 2017,
        qMonth: 1,
        qDay: 1,
        //可选年数
        s_year: t_years,
        //可选月份
        s_month: t_months,
        //qTime: "123",
        //品牌上限
        brandLimit:2,
        //工业类意向图片选择上限
        //maxUserIndustryImageCount:1,
        //动物类意向图片选择上限
        //建筑类意向图片选择上限
        //maxUserBuildingImageCount:1,
        //maxUserAnimalImageCount:1,
        //艺术类意向图片选择上限
        //maxUserArtImageCount:1,
        //其他类意向图片选择上限
        //maxUserOthersImageCount:1,
        //图库定义
        gallery:[
            {
                jtabId:'#g_tab0',
                tabId:'g_tab0',
                typeId:'INDUSTRY',
                typeName:"工业产品类",
                imgList:[

                ]
            },
            {
                jtabId:'#g_tab1',
                tabId:'g_tab1',
                typeId:'ANIMAL',
                typeName:"动物类",
                imgList:[

                ]
            },
            {
                jtabId:'#g_tab2',
                tabId:'g_tab2',
                typeId:'BUILDING',
                typeName:"建筑类",
                imgList:[

                ]
            },
            {
                jtabId:'#g_tab3',
                tabId:'g_tab3',
                typeId:'ART',
                typeName:"艺术品类",
                imgList:[

                ]
            },
            {
                jtabId:'#g_tab4',
                tabId:'g_tab4',
                typeId:'OTHERS',
                typeName:"其他类",
                imgList:[

                ]
            }
        ],
        //从图库中选取的意向图片
        inspire_type:[
            {
                jtabId:'#tab0',
                tabId:'tab0',
                typeId:'INDUSTRY',
                typeName:"工业产品类",
                limit:1,
                //mode:1,
                isSelected:false,
                imgList:[

                ]
            },
            {
                jtabId:'#tab1',
                tabId:'tab1',
                typeId:'ANIMAL',
                typeName:"动物类",
                limit:1,
                //mode:1,
                isSelected:false,
                imgList:[

                ]
            },
            {
                jtabId:'#tab2',
                tabId:'tab2',
                typeId:'BUILDING',
                typeName:"建筑类",
                limit:1,
                //mode:1,
                isSelected:false,
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
                //mode:1,
                isSelected:false,
                imgList:[

                ]
            }
        ],
        //所有品牌
        allBrand:[

        ],
        //设计者选取的品牌列表：每个品牌中有product对象，其中存放该品牌下的所有产品
        brandList:[

        ],
        //设计者选中的产品
        selProList:[

        ],
        //设计者选中的产品局部图
        selProDetailList:[

        ]
    },
    methods: {
        //品牌选择tab中的checkbox 选中、取消选中方法
        doCheck:function (){
            var obj=event.currentTarget;
            if(obj.checked){
                //选中的品牌信息保存
                CreateQuery.brandList.push(
                    {
                        checked:"checked",
                        //品牌id
                        brandId:obj.alt,
                        //品牌icon的url
                        brandIconUrl:$(obj).next("label").find("img").get(0).src,
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
            var obj=event.currentTarget;
            //如果是选中；则将数据添加进vue的data中
            if(obj.checked){
                console.log(event.currentTarget);
                for(var i=0;i<CreateQuery.brandList.length;i++){
                    //遍历brandList.找到对应id的brandList信息；
                    if(CreateQuery.brandList[i].brandId==obj.alt){
                        for(var j=0;j<CreateQuery.brandList[i].product.length;j++){
                            //id为this.alt的brandList下的product中找到imageUrl为选中的图片路径的记录，并将其保存于selProduct属性中
                            if(CreateQuery.brandList[i].product[j].imageUrl==$(obj).next("label").find("img").get(0).src){
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
                console.log(CreateQuery.brandList);
                loadProductDetail(obj.alt);

            }
            //若是取消选中，则是将其从vue的data中剔除
            else{
                //在vue 的selProList中找到取消选中的产品信息；并将其剔除。
                for(i=0;i<CreateQuery.selProList.length;i++){
                    if(CreateQuery.selProList[i].imageUrl==$(obj).next("label").find("img").get(0).src){
                        CreateQuery.selProList.splice(i,1);
                        break
                    }
                }
                //删除整体造型后，连带需要删除该整体造型的局部造型
                //找到要删除的 元素的 index,
                var delIndex=[];
                for(i=0;i<CreateQuery.selProDetailList.length;i++){
                    if(CreateQuery.selProDetailList[i].parentImageId==obj.name){
                        delIndex.push(i);
                        //delete CreateQuery.selProDetailList[i];
                    }
                }
                //删除相应的局部造型
                for(i=delIndex.length-1;i>=0;i--){
                    CreateQuery.selProDetailList.splice(delIndex[i],1);
                }
                console.log(CreateQuery.selProList)
            }
        },
        //在图库中选择意向图片
        doCheck_inspire:function(){
            var obj=event.currentTarget;
            //如果是选中；则将数据添加进vue的data中
            if(obj.checked){
                var g_imgList=CreateQuery.gallery[INSP_TAB_INDEX_GAL].imgList;
                var temp;
                for(var k=0;k<g_imgList.length;k++){
                    if(g_imgList[k].imageId==obj.alt){
                        temp=g_imgList[k];
                    }
                }

                CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].imgList.push(temp);
                //选中后，将isSelected改为true表示此类型有选中；在预览中 以if方式 进行展示
                CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].isSelected=true;
            }
            //若是取消选中，则是将其从vue的data中剔除
            else{
                //找到在vue 的inspire_type 对应的类当中的imgList数组里的图片，并将其剔除。
                for(var i=0;i<CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].imgList.length;i++){
                    if(CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].imgList[i].imageId==obj.alt){
                        CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].imgList.splice(i,1);
                        break
                    }
                }

                //取消选中后，判断是否还有选中的图片，若无 isSelected改为false，以if方式 进行隐藏
                if(CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].imgList.length==0){
                    CreateQuery.inspire_type[INSP_TAB_INDEX_GAL].isSelected=false;
                }
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
        //问卷预览中的选择监听：若超过品牌选择上限，则给出提示
        preview_doCheck:function (){
            var obj=event.currentTarget;
            obj.checked?c++:c--;
            if(c>CreateQuery.brandLimit){
                obj.checked=false;
                alert("已达到选择上限！");
                c--;
            }
        },
        //问卷预览中的选择监听：若超过该类型的意向图片选择上限，则给出提示
        preview_doCheck_inspireImg:function (){
            var obj=event.currentTarget;
            //获取当前点击的意向图片的类型
            var type=obj.alt;
            console.log(type);
            console.log(_c_inspireImg);
            var index=INS_IMG_TYPE.indexOf(type);
            obj.checked?_c_inspireImg[index]++:_c_inspireImg[index]--;
            if(_c_inspireImg[index]>CreateQuery.inspire_type[index].limit){
                obj.checked=false;
                alert("已达到选择上限！");
                _c_inspireImg[index]--;
            }
        }
    },
    computed:{
        //计算属性：根据年月日算出 时间
        qReleaseTime:function(){
            return this.qYear+"-"+this.qMonth+"-"+this.qDay+" "+"00:00:00"
        }
    },
    //watch:{
    //    brandLimit:function(val){
    //        if(typeof this.brandLimit!="number"){
    //            alert('用户上限必须为数字')
    //        }
    //    }
    //},
    created:function(){
        //this.$nextTick(function(){
        //    $('#defaultForm').bootstrapValidator();
        //});
    },
    mounted:function(){
        //$('#defaultForm').bootstrapValidator(validatorSettings);
        //$('#defaultForm_1').bootstrapValidator(validatorSettings);
    }
});

var validatorSettings={
    message:'this value is not valid',
    feedbackIcons:{
        valid:'glyphicon glyphicon-ok',
        invalid:'glyphicon glyphicon-remove',
        validating:'glyphicon glyphicon-refresh'
    },
    fields:{
        brandLimit:{
            message:'验证失败',
            validators:{
                notEmpty:{
                    message:'不能为空'
                }
            }
        }
    }
};

