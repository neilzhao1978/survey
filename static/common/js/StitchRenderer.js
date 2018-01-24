/*
    根据页面post数据，在特定DOM位置渲染卡通组合图
    依赖jQuery及p5js
*/

const GET_WHOLE_IMAGE_URL = "http://localhost:8000/api/imageService/getCartoonWholeImage"
const GET_PARTS_IMAGE_URL = "http://localhost:8000/api/imageService/getCartoonReplaceImageExt"
const DEFAULT_ST_BG_URL = "/static/common/img/feature_line_bg_default.png";

class StitchRenderer{

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

        let self = this;
        

        //定义并初始化p5实例
        let s = function( sketch ) {
            sketch.setup = function() {
              //sketch.pixelDensity(1);
              sketch.createCanvas(w, h);
              self.drawBackground();
            };
        };
        this.p5Instance = new p5(s, DOM_ele);
        
        //masterImage为母版，image为p5.Image对象，imageData为定位数据
        this.masterImageData = null;
        this.masterImage = null;

        //partImage 1-3为部件，具体对应位置见页面UI
        this.part1ImageData = null;
        this.part1Image = null;
        this.part2ImageData = null;
        this.part2Image = null;
        this.part3ImageData = null;
        this.part3Image = null;
    }

    loadMasterImage(imgID){
        let self = this;
        let url = GET_WHOLE_IMAGE_URL+"?imageId="+imgID;
        this.p5Instance.httpGet(
            url,
            "json",
            (response_data)=>{
                //数据获取成功
                let d = response_data.data;
                self.masterImageData = d;
                let url = d.wholeImageUrl;
                self.p5Instance.loadImage(url,(img)=>{
                    self.masterImage = img;
                    //p5.Graphics创建完成，开始绘制
                    self.stitchImage();
                })
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.errorCode)
            }
        )
    }

    loadPart1Image(srcID,trgID){
        let self = this;
        let url = GET_PARTS_IMAGE_URL+"?imageIdScr="+srcID+"&imageIdTarget="+trgID+"&partName=司机室";
        this.p5Instance.httpGet(
            url,
            "json",
            (response_data)=>{
                //数据获取成功
                let d = response_data.data[0];
                self.part1ImageData = d;
                let url = d.url;
                self.p5Instance.loadImage(url,(img)=>{
                    self.part1Image = img
                    //p5.Graphics创建完成，开始绘制
                    self.stitchImage();
                })
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.status)
            }
        )
    }

    loadPart2Image(srcID,trgID){
        let self = this;
        let url = GET_PARTS_IMAGE_URL+"?imageIdScr="+srcID+"&imageIdTarget="+trgID+"&partName=司机室";
        this.p5Instance.httpGet(
            url,
            "json",
            (response_data)=>{
                //数据获取成功
                let d = JSON.parse(response_data.data[0]);
                self.part2ImageData = d;
                let url = d.url;
                self.p5Instance.loadImage(url,(img)=>{
                    self.part2Image=img
                    //p5.Graphics创建完成，开始绘制
                    self.stitchImage();
                })
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.status)
            }
        )
    }

    loadPart3Image(srcID,trgID){
        let self = this;
        let url = GET_PARTS_IMAGE_URL+"?imageIdScr="+srcID+"&imageIdTarget="+trgID+"&partName=司机室";
        this.p5Instance.httpGet(
            url,
            "json",
            (response_data)=>{
                //数据获取成功
                let d = JSON.parse(response_data.data[0]);
                self.part3ImageData = d;
                let url = d.url;
                self.p5Instance.loadImage(url,(img)=>{
                    self.part3Image=img;
                    //p5.Graphics创建完成，开始绘制
                    self.stitchImage();
                })
            },
            (error)=>{
                //处理错误
                self.drawMsg(error.status)
            }
        )
    }

    stitchImage(){
        
        //创建p5.Graphics对象
        let g = null;
        if(this.masterImage){
            g = this.p5Instance.createGraphics(
                // this.masterImageData.w,
                // this.masterImageData.h
                500,500
            )
            g.pixelDensity(1);
            this.p5Instance.imageMode(this.p5Instance.CORNER);
            g.image(this.masterImage,0,0,g.width,g.height);
        }else{
            g = this.p5Instance.createGraphics(
                this.canvasW,
                this.canvasH
            )
        }
        
        if(this.part1Image){
            this.p5Instance.imageMode(this.p5Instance.CORNER);
            this.part1Image.filter(this.p5Instance.BLUR,1);
            g.image(
                this.part1Image,
                this.part1ImageData.x,
                this.part1ImageData.y,
                this.part1ImageData.w,
                this.part1ImageData.h
            )
        }

        if(this.part2Image){
            this.p5Instance.imageMode(this.p5Instance.CORNER);
            g.image(
                this.part2Image,
                this.part2ImageData.x,
                this.part2ImageData.y,
                this.part2ImageData.w,
                this.part2ImageData.h
            )
        }

        if(this.part3Image){
            this.p5Instance.imageMode(this.p5Instance.CORNER);
            g.image(
                this.part3Image,
                this.part3ImageData.x,
                this.part3ImageData.y,
                this.part3ImageData.w,
                this.part3ImageData.h
            )
        }
        
        //将g绘制在画布上
        this.drawBackground();
        this.p5Instance.clear();
        this.p5Instance.imageMode(this.p5Instance.CENTER);
        this.p5Instance.push();
        
        this.p5Instance.translate(this.canvasW/2,this.canvasH/2);
        this.p5Instance.tint(255, 30);
        this.p5Instance.image(
           g,0,0,
            (this.canvasH-10)/g.height*g.width,
            this.canvasH-10
            
        );
        
        this.p5Instance.pop();
        this.p5Instance.filter(this.p5Instance.THRESHOLD)
        //this.p5Instance.filter(this.p5Instance.GRAY)
        
    }
    
    drawBackground(url){
        let img_url = url || DEFAULT_ST_BG_URL;
        $("#"+this.containerDOM).css({
            // "background-image":"url("+img_url+")",
            // "background-size":"cover"
            "background-color":"#fff"
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