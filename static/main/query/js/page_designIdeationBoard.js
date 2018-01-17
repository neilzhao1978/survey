
$(function(){

    let flr = new FeatureLineRenderer("featureline-rendering");
    
    let featurelineOptions = {
        "showFeatureLine":"true",
        "oraginalImages":["132"]
       }
    flr.loadFeatureLine(featurelineOptions)

    let str = new StitchRenderer("stitch-rendering");
    str.loadMasterImage(132);
    str.loadPart1Image(133,132);
});


