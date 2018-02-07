
$(function(){

    let olr = new FeatureLineRenderer("overlap-rendering");
    
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
        "rearHood":"1343",
        "mode":"overlap"
    };
    olr.loadFeatureLine(overlapOptions)
    
});


