
var flr,vis3d,currentSlot,voterChart;
var candidate_data = [];
var styleMatrix = [
    {text: "现代", weight: 1},
    {text: "传统", weight: 1},
    {text: "圆润", weight: 1},
    {text: "硬朗", weight: 1},
    {text: "简洁", weight: 1},
    {text: "复杂", weight: 1}
]

var flrOptions = {
    "master":"",
    "driverRoom":"",
    "wheel":"",
    "rearHood":"",
    "mode":"overlap" 
};

//人员背景统计环形图

var voterChartOption = {
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


/*
    var styleChartOption = {
        title: {
            text: '基础雷达图'
        },
        tooltip: {},
        legend: {
            //data: ['预算分配（Allocated Budget）', '实际开销（Actual Spending）']
        },
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
               }
            },
            indicator: [
               { name: '销售（sales）', max: 6500},
               { name: '管理（Administration）', max: 16000},
               { name: '信息技术（Information Techology）', max: 30000},
               { name: '客服（Customer Support）', max: 38000},
               { name: '研发（Development）', max: 52000},
               { name: '市场（Marketing）', max: 25000}
            ]
        },
        series: [{
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            // areaStyle: {normal: {}},
            data : [
                {
                    value : [4300, 10000, 28000, 35000, 50000, 19000],
                    name : '预算分配（Allocated Budget）'
                },
                 {
                    value : [5000, 14000, 28000, 31000, 42000, 21000],
                    name : '实际开销（Actual Spending）'
                }
            ]
        }]
    };
*/
var surveyId=common.GetRequest();
var GET_SURVEY_DATA_URL = host+"/surveyService/getSurveyStat?surveyId=";


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
function initPageData(product_id,thumb_url,survey_title){
    $(".slot0").find("img").get(0).src = thumb_url;
    flrOptions.master = product_id;
    flr.loadFeatureLine(flrOptions)
    if (survey_title){
        $("#survey-title").html(survey_title)
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
    flrOptions.rearHood ="";
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
                response_data.candidates[0].productData.id,
                response_data.candidates[0].productData.thumb_url,
                response_data.surveyTitle
            );
            vis3d.loadProducts(response_data.products);
            loadVoters(response_data.voters);
            loadPhotoWall(response_data.candidates);
            calcStyleMatrix(response_data.style_matrix);
            //loadTagCloud(response_data.style_matrix);
            console.log(response_data)
        },
        error:(response_data)=>{
            alert(response_data)
        },
        dataType: "json",
        contentType: "application/json"
    })
    
}

function loadVoters(voters){
    //后台得到数组myData，数组分开传入图表的value值，就不用重新设置颜色，内容只需各个职位参与调研的人数，如下
    voterChartOption.series[0].data[0].value = voters.manager;
    voterChartOption.series[0].data[1].value = voters.engineer;
    voterChartOption.series[0].data[2].value = voters.designer;
    voterChartOption.series[0].data[3].value = voters.sale;
    voterChartOption.series[0].data[4].value = 0;
    voterChart.setOption(voterChartOption);
}
function loadPhotoWall(candidates){
    candidate_data = candidates;
    //先隐藏所有图片
    $(".photoWall>li").hide();
    for (var i=0;i<candidate_data.length;i++){
        $(".photoWall>.item"+i+">img").get(0).src = candidate_data[i].productData.thumb_url;
        $(".photoWall>.item"+i).show()
    }
}
function loadCandidateDetail(candidate_id){
    var c_data = candidate_data[candidate_id].productData;
    $(".proDetails .brand").html(c_data.brand);
    $(".proDetails .model").html(c_data.model)
    $(".proDetails .year").html(c_data.year);
    $(".proDetails .style").html(c_data.style_keyword);
    $(".proDetails .texture").html(c_data.texture);
    var votes = candidate_data[candidate_id].votes
    var a = votes.engineer;
	var b = votes.designer;
	var c = votes.sale;
    //var d = votes.user;
    var d = 0;
	var e = votes.manager;
	var sum = a+b+c+d+e;
    $(".proDetails .engineer-votes").width(a/sum*80+4);
    $(".proDetails .designer-votes").width(b/sum*80+4);
    $(".proDetails .sale-votes").width(c/sum*80+4);
    $(".proDetails .user-votes").width(d/sum*80+4);
    $(".proDetails .manager-votes").width(e/sum*80+4);
}
function calcStyleMatrix(style_matrix){
    if(style_matrix.x1){
        styleMatrix[0].weight=style_matrix.x1+1
    }
    if(style_matrix.x2){
        styleMatrix[1].weight=style_matrix.x2+1
    }
    if(style_matrix.y1){
        styleMatrix[2].weight=style_matrix.y1+1
    }
    if(style_matrix.y2){
        styleMatrix[3].weight=style_matrix.y2+1
    }
    if(style_matrix.z1){
        styleMatrix[4].weight=style_matrix.z1+1
    }
    if(style_matrix.z2){
        styleMatrix[5].weight=style_matrix.z2+1
    }

}


/* 初始化页面*/
$(function(){

    flr = new FeatureLineRenderer("featureline-svg");
    vis3d = new DataVis3DRenderer("design-datavis-3d");
    //图表初始化
    voterChart = echarts.init(document.getElementById('voterChart'));
    //styleChart = echarts.init(document.getElementById('styleChart'));
    loadSurveyData(surveyId)

    
   

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
        var link = $(this).find("img").attr("src");
        $("#photoWall").removeClass("active");
        $("#proDetails").addClass("active");
        $("#bigPic").attr("src",link);

        var id = $(this).data("candidateId");
        loadCandidateDetail(id)
    });

    $("#hide-product-detail").on("click",function(){
        $("#photoWall").addClass("active");
        $("#proDetails").removeClass("active");
    })

    //显示统计数据弹窗
    $("#show-info-panel").on("click",function(){
        $("#informationBoard").modal();
        $("#style-tag-cloud").html("")
        $("#style-tag-cloud").jQCloud(styleMatrix);
    })
    
});


