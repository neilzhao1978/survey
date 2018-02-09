/*
    根据页面post数据，在特定DOM位置渲染特征线
    依赖jQuery及p5js
*/

const GET_PRODUCT_DATA_URL = ""

class DataVis3DRenderer{

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

        this.featureLineData = null;
        this.featureLine = null;
        this.combinedImage = null;
        
        let self = this;

        //定义并初始化p5实例
        let s = function( sketch ) {
            sketch.setup = function() {
              sketch.pixelDensity(1);
              sketch.createCanvas(w, h, self.p5Instance.WEBGL);
              self.drawBackground();
            };
        };
        this.p5Instance = new p5(s, DOM_ele); 

        
    }

    loadFeatureLine(options){
        let self = this;
        let url = GET_IMAGE_URL;
        let opt_data = options;
        
        this.p5Instance.httpPost(
            url,
            "json",
            opt_data,
            (response_data)=>{
                //数据获取成功
                self.featureLineData = response_data.data;
                let featureLine_data_url = "data:image/png;base64,"+response_data.data.combinedFeature;
                let combinedImage_data_url = "data:image/png;base64,"+response_data.data.combinedImage;
                //调用p5加载图像数据到p5.Graphics对象
                self.p5Instance.loadImage(featureLine_data_url,(img)=>{
                    
                    self.featureLine = img;
                    //p5.Graphics对象创建好以后，开始绘制
                    self.drawFeatureLine();
                })
                self.p5Instance.loadImage(combinedImage_data_url,(img)=>{
                    
                    self.combinedImage = img;
                    //p5.Graphics对象创建好以后，开始绘制
                    self.drawFeatureLine();
                })
                
            },
            (error)=>{
                self.drawMsg(error);
        })
        
    }

    drawFeatureLine(){

        let g = null;
        if(this.featureLineData){
            g = this.p5Instance.createGraphics(
                this.featureLineData.w,
                this.featureLineData.h
            )
        }
        if(this.combinedImage){
            g.tint(255, 80);
            g.image(this.combinedImage,0,0,g.width,g.height)
            g.filter(this.p5Instance.THRESHOLD)
            //g.filter(this.p5Instance.GRAY)
        }
        if(this.featureLine){
            g.noTint();
            g.image(this.featureLine,0,0,g.width,g.height)
        }
        

        this.drawBackground();
        this.p5Instance.clear();
        
        this.p5Instance.imageMode(this.p5Instance.CENTER);
        this.p5Instance.push();
        this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
        this.p5Instance.image(
            g,0,0,
            (this.canvasH-10)/g.height*g.width,
            this.canvasH-10
            
        );
        this.p5Instance.pop();
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

