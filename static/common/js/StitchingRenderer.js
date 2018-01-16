/*
    根据页面post数据，在特定DOM位置渲染卡通组合图
    依赖jQuery及p5js
*/

const GET_WHOLE_IMAGE_URL = "http://localhost:8000/api/imageService/getCartoonWholeImage"
const GET_PARTS_IMAGE_URL = "http://localhost:8000/api/imageService/getCartoonReplaceImage"
const DEFAULT_BG_URL = "/static/common/img/feature_line_bg_default.png";

class StitchingRenderer{

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
        
        //template为母版，image为p5.Image对象，imageData为定位数据
        this.templateImageData = null;
        this.templateImage = null;

        //image1-3为部件，具体对应位置见页面UI
        this.partImageData1 = null;
        this.partImageData2 = null;
        this.partImageData3 = null;
        this.partImage1 = null;
        this.partImage2 = null;
        this.partImage3 = null;
    }

    getImage0(imgID){
        let self = this;
        let url = GET_WHOLE_IMAGE_URL+"?imageId="+imgID;
        this.p5Instance.httpGet(
            url,
            "json",
            (response_data)=>{
                //数据获取成功
                let d = JSON.parse(response_data.data)
                self.imageData0.url = d.wholeImageUrl;
                self.imageData0.w = d.wholeImageUrl;
                self.imageData0.h = d.wholeImageUrl;
                self.imageData0.x = d.wholeImageUrl;
                self.imageData0.y = d.wholeImageUrl;
                self.p5Instance.loadImage(self.imageData0.wholeImageUrl)
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.status)
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