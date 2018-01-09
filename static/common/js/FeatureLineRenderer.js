/*
    根据页面post数据，在特定DOM位置渲染特征线
    依赖jQuery及p5js
*/

const FEATURELINE_POST_URL = "http://localhost:8000/api/imageService/geneProfileImage"

class FeatureLineRenderer{

    constructor(DOM_ele){
        console.log(DOM_ele)
        //获取容器尺寸
        let w = $("#"+DOM_ele).width(); 
        let h = $("#"+DOM_ele).height();

        let s = function( sketch ) {
            sketch.setup = function() {
              sketch.createCanvas(w, h);
              sketch.background(200);
            };
        };

        this.p5Instance = new p5(s, DOM_ele)
    }

    showFeatureLine(options){
        let self = this
        $.post( 
            FEATURELINE_POST_URL, 
            options,
            (data)=>{
                //数据获取成功
                let image_data_url = "data:image/png;base64,"+data;
                self.renderFeatureLine(image_data_url);
            },
            "json"
        ).fail(()=>{
            //数据获取失败
        }); 
    }

    renderFeatureLine(image_data_url){
        //加载data_url，加载成功后渲染
        let self = this;
        this.p5Instance.loadImage(image_data_url,(feature_line)=>{
            this.p5Instance.draw(feature_line,0,0)
        })
    }

}