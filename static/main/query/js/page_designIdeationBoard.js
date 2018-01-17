
$(function(){

    let flr = new FeatureLineRenderer("featureline-rendering");
    
    let featurelineOptions = {
        "showFeatureLine":"true",
        "oraginalImages":["133","132"]
       }
    flr.loadFeatureLine(featurelineOptions)

    let str = new StitchRenderer("stitch-rendering");
    str.loadMasterImage(133);
    str.loadPart1Image(132);
});


