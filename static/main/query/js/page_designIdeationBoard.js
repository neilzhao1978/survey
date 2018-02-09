
$(function(){

    

    let olr = new FeatureLineRenderer("featureline-rendering");
    
    let vis3d = new DataVis3DRenderer("design-datavis-3d");
    

    let stitchOptions = {
        "master":"133",
        "driverRoom":"132",
        "wheel":"133",
        "rearHood":"132",
        "mode":"stitch" 
    };
    let overlapOptions = {
        "master":"132",
        "driverRoom":"132",
        "wheel":"133",
        "rearHood":"133",
        "mode":"overlap"
    };
    olr.loadFeatureLine(overlapOptions)


    $("#toggle-featureline-mode").on("click",function(e){
        let isActive = $(this).hasClass("active");
        if (!isActive){
            $(this).html("关闭拼接模式")
        }else{
            $(this).html("启用拼接模式")
        }
    })
    
});


