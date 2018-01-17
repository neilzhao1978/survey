/*
    根据页面post数据，在特定DOM位置渲染特征线
    依赖jQuery及p5js
*/

const FEATURELINE_POST_URL = "http://localhost:8000/api/imageService/geneProfileImage"
const DEFAULT_FL_BG_URL = "/static/common/img/feature_line_bg_default.png";

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

        this.featureLine = null;
        let self = this;

        //定义并初始化p5实例
        let s = function( sketch ) {
            sketch.setup = function() {
              sketch.pixelDensity(1);
              sketch.createCanvas(w, h);
              self.drawBackground();
            };
        };
        this.p5Instance = new p5(s, DOM_ele);

        
    }

    loadFeatureLine(options){
        let self = this;
        this.p5Instance.httpPost(
            FEATURELINE_POST_URL,
            "json",
            options,
            (response_data)=>{
                //数据获取成功
                let image_data_url = "data:image/png;base64,"+response_data.data;
                //调用p5加载图像数据到p5.Graphics对象
                self.p5Instance.loadImage(image_data_url,(img)=>{
                    self.featureLine = self.p5Instance.createGraphics(
                        img.width,
                        img.height
                    )
                    self.featureLine.image(img,0,0);
                    //p5.Graphics对象创建好以后，开始绘制
                    self.drawFeatureLine();
                })
                
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.status)
            }
        )
    }

    drawFeatureLine(){
        this.drawBackground();
        this.p5Instance.clear();
        
        this.p5Instance.imageMode(this.p5Instance.CENTER);
        this.p5Instance.push();
        this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
        this.p5Instance.image(
            this.featureLine,0,0,
            (this.canvasH-10)/this.featureLine.height*this.featureLine.width,
            this.canvasH-10
            
        );
        this.p5Instance.pop();
    }

    drawBackground(url){
        let img_url = url || DEFAULT_FL_BG_URL;
        $("#"+this.containerDOM).css({
            "background-image":"url("+img_url+")",
            "background-size":"cover"
        })
        
    }

    drawMsg(msg){
        this.drawBackground();
        this.p5Instance.clear();
        this.p5Instance.textAlign(this.p5Instance.CENTER);
        this.p5Instance.push();
        this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
        this.p5Instance.textSize(36);
        this.p5Instance.text(msg,0,0);
        this.p5Instance.pop();
    }

}