
$(function(){

    let olr = new FeatureLineRenderer("overlap-rendering");
    
    
    

    let stitchOptions = {
        "master":"133",
        "driverRoom":"132",
        "wheel":"133",
        "rearHood":"132",
        "mode":"stitch" 
    };
    let overlapOptions = {
        "master":"133",
        "driverRoom":"132",
        "wheel":"133",
        "rearHood":"132",
        "mode":"overlap"
    };
    olr.loadFeatureLine(stitchOptions)
    
});


