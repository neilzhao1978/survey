
var flr,vis3d,currentSlot;

var flrOptions = {
    "master":"",
    "driverRoom":"",
    "wheel":"",
    "rearHood":"",
    "mode":"overlap" 
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

/* 初始化页面*/
$(function(){

    flr = new FeatureLineRenderer("featureline-svg");
    vis3d = new DataVis3DRenderer("design-datavis-3d");

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
    
});


