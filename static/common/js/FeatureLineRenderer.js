/*
    根据页面post数据，在特定DOM位置渲染特征线
    依赖jQuery及p5js
*/

const GET_IMAGE_URL = host+"/imageService/processImage"
const DEFAULT_FL_BG_URL = "/static/common/img/feature_line_bg_default.png";

class FeatureLineRenderer{

    constructor(DOM_ele){
        this.layer1 = $("#"+DOM_ele).find(".layer1").get(0)
        this.layer2 = $("#"+DOM_ele).find(".layer2").get(0)
        this.featureLineData = null;
    }

    loadFeatureLine(options){
        let self = this;
        let url = GET_IMAGE_URL;
        let opt_data = JSON.stringify(options);
        
        $.ajax({
            type: "POST",
            url: url,
            data: opt_data,
            success: (response_data)=>{
                self.featureLineData = response_data.data;
                let featureLine_data_url = "data:image/png;base64,"+response_data.data.combinedFeature;
                let combinedImage_data_url = "data:image/png;base64,"+response_data.data.combinedImage;
                //self.layer1.attr = combinedImage_data_url;
                //self.layer2.src = featureLine_data_url;
                $(self.layer1).attr("xlink:href",combinedImage_data_url)
                $(self.layer2).attr("xlink:href",featureLine_data_url)
            },
            error:(response_data)=>{
                self.drawMsg(response_data)
            },
            dataType: "json",
            contentType: "application/json"
        })
    }
    drawBackground(url){
        let img_url = url || DEFAULT_FL_BG_URL;
        $("#"+this.containerDOM).css({
            // "background-image":"url("+img_url+")",
            // "background-size":"cover"
            "background-color":"#ffffff"
        })
        
    }

    drawMsg(msg){
        //alert(msg)
    }
}

