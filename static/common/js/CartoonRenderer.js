/*
    根据页面post数据，在特定DOM位置渲染卡通组合图
    依赖jQuery及p5js
*/

const CARTOON_WHOLE_GET_URL = "http://localhost:8000/api/imageService/getCartoonWholeImage"
const CARTOON_PARTS_POST_URL = "http://localhost:8000/api/imageService/getCartoonReplaceImage"
const DEFAULT_BG_URL = "/static/common/img/feature_line_bg_default.png";

class FeatureLineRenderer{

    constructor(DOM_ele){
        this.containerDOM = DOM_ele;
        //获取容器
        let container = $("#"+DOM_ele).parent()
        //获取容器尺寸
        let w = container.width(); 
        let h = container.height();
        //存储画布尺寸数据
        this.canvasW = w;
        this.canvasH = h;

        this.renderBackground();

        //定义并初始化p5实例
        let s = function( sketch ) {
            sketch.setup = function() {
              sketch.createCanvas(w, h);
            };
        };
        this.p5Instance = new p5(s, DOM_ele);
        
    }

    showFeatureLine(options){
        let self = this;
        this.p5Instance.httpPost(
            FEATURELINE_POST_URL,
            "json",
            options,
            (response_data)=>{
                //数据获取成功
                let image_data_url = "data:image/png;base64,"+response_data.data;
                self.renderFeatureLine(image_data_url);
            },
            (error)=>{
                //处理错误
                self.renderMsg(error.status)
            }
        )
    }

    renderFeatureLine(img_url){
        let self = this;
        //加载img_url，加载成功后渲染
        if (img_url){
            this.p5Instance.loadImage(img_url,(feature_line)=>{
                this.p5Instance.imageMode(this.p5Instance.CENTER);
                this.p5Instance.push();
                this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
                this.p5Instance.image(feature_line,0,0,
                    this.canvasH-10,
                    (this.canvasH-10)/feature_line.width*feature_line.height);
                this.p5Instance.pop();
            })
        }
    }
    renderBackground(url){
        let img_url = url || DEFAULT_BG_URL;
        $("#"+this.containerDOM).css({
            "background-image":"url("+img_url+")",
            "background-size":"cover"
        })
        
    }
    renderMsg(msg){
        this.p5Instance.textAlign(this.p5Instance.CENTER);
        this.p5Instance.push();
        this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
        this.p5Instance.textSize(36);
        this.p5Instance.text(msg,0,0);
        this.p5Instance.pop();
    }

}