
var flr,vis3d,currentSlot,myChart;

var flrOptions = {
    "master":"",
    "driverRoom":"",
    "wheel":"",
    "rearHood":"",
    "mode":"overlap" 
};
var dummy_survey_data = {
    "surveyTitle":"",
    "voters":{
        "total":"12",
        "engineer":"6",
        "manager":"1",
        "sale":"2",
        "designer":"3"
    },
    "style_matrix":{
        "x1":"3",
        "x2":"1",
        "y1":"4",
        "y2":"1",
        "z1":"2",
        "z2":"0"
    },
    "products":[],
    "candidates":[
        {
            "votes":{
                "total":"5",
                "engineer":"2",
                "manager":"1",
                "sale":"0",
                "designer":"2"
            },
            "productData":{
                "brand":"",
                "model":"",
                "year":"",
                "style_keywrod":"",
                "texture":"",
                "thumb_url":""
            }
        }
    ]
}

//人员背景统计环形图

var myChartOption = {
	    series: [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius: ['50%', '80%'],
	            avoidLabelOverlap: false,
	            label: {
	                normal: {
	                    show: false,
	                    position: 'center'
	                },
	                emphasis: {
	                	formatter: "{c}",
	                    show: true,
	                    textStyle: {
	                        fontSize: '20',
	                        fontWeight: 'bold'
	                    }
	                }
	            },
	            labelLine: {
	                normal: {
	                    show: false
	                }
	            },
	            
	            data:[
	                {value:0, name:'管理',itemStyle:{color:'#E57C77'}},
	                {value:0, name:'技术',itemStyle:{color:'#44B3A5'}},
	                {value:0, name:'设计',itemStyle:{color:'#F2AA3C'}},
	                {value:0, name:'销售',itemStyle:{color:'#6BB7E2'}},
	                {value:0, name:'终端客户',itemStyle:{color:'#7B75C4'}}
	            ]
	        }
	    ]
	};



var surveyId=common.GetRequest();
var GET_SURVEY_DATA_URL = host+"/surveyService/getSurveyResult?surveyId=";


function fillSlot(product_id,thumb_url){
    if(currentSlot == 0){
        $(".slot0").find("img").get(0).src = thumb_url;
        flrOptions.master = product_id;
    }else if(currentSlot == 1){
        $(".slot1").find("img").get(0).src = thumb_url;
        flrOptions.driverRoom = product_id;
    }else if(currentSlot == 2){
        $(".slot2").find("img").get(0).src = thumb_url;
        flrOptions.wheel = product_id;
    }else if(currentSlot == 3){
        $(".slot3").find("img").get(0).src = thumb_url;
        flrOptions.rearHood = product_id;
    }
    flr.loadFeatureLine(flrOptions)
}
function initPageData(product_id,thumb_url,surveyTitle){
    $(".slot0").find("img").get(0).src = thumb_url;
    flrOptions.master = product_id;
    flr.loadFeatureLine(flrOptions)
    if (surveyTitle){
        $("#survey-title").html(surveyTitle)
    }
}
function clearAllSlots(){
    // $(".slot0").find("img").get(0).src = "";
    $(".slot1").find("img").get(0).src = "";
    $(".slot2").find("img").get(0).src = "";
    $(".slot3").find("img").get(0).src = "";
    // flrOptions.master ="";
    flrOptions.driverRoom ="";
    flrOptions.wheel ="";
    flrOptions.mode ="";
    flr.loadFeatureLine(flrOptions)
}
function printFeatureLine(){
    var printWindow = window.open('', 'PRINT', 'height=400,width=600');
    printWindow.document.write('<html><head></head><body>');
    printWindow.document.write($(".featureline-rendering").html());
    printWindow.document.write('</body></html>');

    printWindow.document.close(); // necessary for IE >= 10
    printWindow.focus(); // necessary for IE >= 10*/

    printWindow.print();
    printWindow.close();

    return true;

}
function loadSurveyData(surveyId){
    var request_url = GET_SURVEY_DATA_URL+surveyId;
    
    $.ajax({
        type: "GET",
        url: request_url,
        success: (response_data)=>{
            
            initPageData(
                response_data.products[0].id,
                response_data.products[0].thumb_url
            );
            vis3d.loadProducts(response_data.products)
            console.log(response_data)
        },
        error:(response_data)=>{
            alert(response_data)
        },
        dataType: "json",
        contentType: "application/json"
    })
    
}


//客户喜好长度统计图
//需输入参数voters,一个包含各个职位参与人数的数组
function cusLike(voters){
	var a = voters[0];
	var b = voters[1];
	var c = voters[2];
	var d = voters[3];
	var e = voters[4];
	var sum = a+b+c+d+e;
	// var tech = document.getElementById("engineer");
	// var design = document.getElementById("designer");
	// var sale = document.getElementById("sale");
	// var user = document.getElementById("user");
	// var manage = document.getElementById("manager");
	
	// tech.style.width=a/sum*80+"px";
	// design.style.width=b/sum*80+"px";
	// sale.style.width=c/sum*80+"px";
	// user.style.width=d/sum*80+"px";
    // manage.style.width=e/sum*80+"px";
    $("#engineer").width(a/sum*80);
    $("#designer").width(b/sum*80);
    $("#sale").width(c/sum*80);
    $("#user").width(d/sum*80);
    $("#manager").width(e/sum*80);
}
//window.onload(cusLike(number));

/* 初始化页面*/
$(function(){

    flr = new FeatureLineRenderer("featureline-svg");
    vis3d = new DataVis3DRenderer("design-datavis-3d");
    //图表初始化
    myChart = echarts.init(document.getElementById('circleChart'));

    loadSurveyData(surveyId)

    
    myChart.setOption(myChartOption);
    // 环状图传入数据
    //后台得到数组myData，数组分开传入图表的value值，就不用重新设置颜色，内容只需各个职位参与调研的人数，如下
    //option.series[0].data[0].value = myData[0];
    //option.series[0].data[1].value = myData[1];
    //option.series[0].data[2].value = myData[2];
    //option.series[0].data[3].value = myData[3];
    //option.series[0].data[4].value = myData[4];

    //绑定UI事件处理
    $("#toggle-featureline-mode").on("click",function(e){
        let isActive = $(this).hasClass("active");
        if (!isActive){
            $(this).html("关闭拼接模式")
            flrOptions.mode = "stitch"
        }else{
            $(this).html("启用拼接模式")
            flrOptions.mode = "overlap"
        }
        flr.loadFeatureLine(flrOptions)
    })
    $("#clear-all-slots").on("click",function(){
        clearAllSlots();
    })
    $("#print-featureline").on("click",function(){
        printFeatureLine();
    })
    $(document).on("click",function(){
        currentSlot = null;
    })
    $(window).on("resize",function(){
        vis3d.resize();
    })
    $(".slot0").on("click",function(e){
        currentSlot = 0;
        e.preventDefault();
        e.stopPropagation();
    })
    $(".slot1").on("click",function(e){
        currentSlot = 1;
        e.preventDefault();
        e.stopPropagation();
    })
    $(".slot2").on("click",function(e){
        currentSlot = 2;
        e.preventDefault();
        e.stopPropagation();
    })
    $(".slot3").on("click",function(e){
        currentSlot = 3;
        e.preventDefault();
        e.stopPropagation();
    })
    $("#design-datavis-3d").on("click",function(e){
        e.stopPropagation();
    })

    //点击图片墙图片，切换至产品详情的div,并获得所选的图片
    $("#mytab>li").on("click",function(){	
        var s1 = $(this).find("img").attr("src");
        $("#photoWall").removeClass("active");
        $("#proDetails").addClass("active");
        $("#bigPic").attr("src",s1)
    });
    $("#hide-product-detail").on("click",function(){
        $("#photoWall").addClass("active");
        $("#proDetails").removeClass("active");
    })

    //显示统计数据弹窗
    $("#show-info-panel").on("click",function(){
        $("#informationBoard").modal()
    })
    
});


