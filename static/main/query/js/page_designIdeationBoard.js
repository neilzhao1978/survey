
$(function(){

    let olr = new FeatureLineRenderer("overlap-rendering");
    
    
    olr.loadFeatureLine(133)

    let stitchOptions = {
        "mode":"stitch",
        "master":"133",
        "driverRoom":"133",
        "wheel":"133",
        "rearHood":"133"    
    };
    let overlapOptions = {
        "mode":"overlap",
        "master":"133",
        "driverRoom":"133",
        "wheel":"133",
        "rearHood":"133"  
    };

    // let str = new StitchRenderer("stitch-rendering");
    // str.loadMasterImage(132);
    // str.loadPart1Image(133,132);
});


