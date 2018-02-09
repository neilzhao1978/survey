/*
    根据页面post数据，在特定DOM位置渲染特征线
    依赖jQuery及p5js
*/

const GET_PRODUCT_DATA_URL = host+"";

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

        this.productData = null;
        
        let self = this;

        //定义并初始化p5实例
        let s = function( sketch ) {
            sketch.setup = function() {
              sketch.pixelDensity(1);
              sketch.createCanvas(w, h, self.p5Instance.WEBGL);
              sketch.background(100);
              sketch.perspective()
            };
            sketch.draw = function(){
                sketch.background(100);
                sketch.orbitControl();
                self.drawProductThumb();
                
            }
        };
        this.p5Instance = new p5(s, DOM_ele); 

        
    }

    loadProductData(options){
        let self = this;
        let url = GET_PRODUCT_DATA_URL;
        let opt_data = options;
        
        this.p5Instance.httpPost(
            url,
            "json",
            opt_data,
            (response_data)=>{
                //数据获取成功
            },
            (error)=>{
                self.drawMsg(error);
        })
        
    }

    drawProductThumb(){
        let self = this;
        for(let i=0;i<10;i++){
            let x = 0;
            let y = 0;
            let z = (i-5)*30;
            this.p5Instance.push();
            this.p5Instance.translate(x,y,z);
            this.p5Instance.plane(30,30);
            this.p5Instance.pop();
        }
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

