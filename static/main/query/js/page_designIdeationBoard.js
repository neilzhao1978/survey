
var flr,vis3d
var flrOptions = {
    "master":"133",
    "driverRoom":"132",
    "wheel":"133",
    "rearHood":"132",
    "mode":"overlap" 
};

$(function(){

    flr = new FeatureLineRenderer("featureline-svg");
    
    vis3d = new DataVis3DRenderer("design-datavis-3d");
    
    flr.loadFeatureLine(flrOptions)


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
    
});


