/*
    根据页面post数据，在特定DOM位置渲染3D数据可视化
    依赖three.js
*/

const GET_SURVEY_DATA_URL = host+"/surveyService/getSurveyResult?surveyId=";

/*
class Product{
    constructor(parent,thumb_url){
        this.scene = parent.scene;
        this.camera = parent.camera;
        this.normalColor = 0xffffff;
        this.highlightColor = 0xff0000;
        //生成图标
        let textureLoader = new THREE.TextureLoader();
        let textureMap = textureLoader.load(thumb_url);
        let material = new THREE.MeshBasicMaterial({ 
            map: textureMap, 
            color: this.normalColor,
            side: THREE.DoubleSide
        });
        let geometry = new THREE.PlaneGeometry()
        this.thumb = new THREE.Mesh(geometry, material)
        this.thumb.scale.set(4,2.7,1);
        this.thumb.up = new THREE.Vector3(0,1,0);
        this.scene.add(this.thumb);
        
    }
    setPos(v3){
        this.thumb.position.set(v3.x,v3.y,v3.z);
    }
    lightOn(){
        this.thumb.material.color.setHex(this.highlightColor);
    }
    lightOff(){
        this.thumb.material.color.setHex(this.normalColor);
    }
    update(){
        this.thumb.lookAt(this.camera.position);
    }
}
*/

class Product{
    constructor(parent,thumb_url){
        this.scene = parent.scene;

        //生成图标
        this.textureLoader = new THREE.TextureLoader();
        this.textureMap = this.textureLoader.load(thumb_url);
        this.material = new THREE.SpriteMaterial({ 
            map: this.textureMap, 
            color: this.normalColor,
            fog: true
        });
        this.thumb = new THREE.Sprite( this.material )
        this.thumb.scale.set(8,5.4,1);
        this.scene.add(this.thumb);

        this.normalColor = new THREE.Color(0xffffff);
        this.highlightColor = new THREE.Color(0x4f7fff);
        this.colorFadingDuration = 0;
    }
    setPos(v3){
        this.thumb.position.set(v3.x,v3.y,v3.z);
    }
    lightOn(){
        this.colorFadingDuration = 60;
    }
    
    update(){
        if(this.colorFadingDuration>0){
            this.colorFadingDuration-=1;
            let c = this.normalColor.clone()
            c.lerp(this.highlightColor,this.colorFadingDuration/60)
            this.thumb.material.color.setHex(c.getHex())
        }

    }
}

class DataVis3DRenderer{

    constructor(DOM_ele){
        let self = this;
        
        //获取容器
        this.DOM_container = $("#"+DOM_ele).get(0);
        this.DOM_canvas = $("#"+DOM_ele).find("canvas").get(0);
        //创建THREEJS渲染器实例
        this.renderer = new THREE.WebGLRenderer({
            antialias: true,
            alpha: true,
            canvas: this.DOM_canvas
        });
        this.renderer.setPixelRatio( window.devicePixelRatio )
        this.renderer.setSize( $(this.DOM_canvas).width(), $(this.DOM_canvas).height());
        this.scene = new THREE.Scene();
        this.scene.fog = new THREE.Fog( 0xffffff, 100, 300 );
        
	    this.camera = new THREE.PerspectiveCamera(60, $(this.DOM_canvas).width()/$(this.DOM_canvas).height(), 0.01, 1000);
        //scale是放大的比例尺度
        this.scale = 30
        this.camera.position.set(
            this.scale*2,
            this.scale*2,
            this.scale*2
        );
        this.camera.lookAt(new THREE.Vector3(0,0,0))
        this.raycaster = new THREE.Raycaster();  
        this.intersects = []
        //OrbitControls
        this.orbit = new THREE.OrbitControls( this.camera, this.renderer.domElement );

        //显示坐标轴辅助线
        let axesHelper = new THREE.AxesHelper(40);
        this.scene.add(axesHelper);
        let axesHelper2 = new THREE.AxesHelper(40);
        axesHelper2.material.color.setHex(0x666666);
        let ms = (new THREE.Matrix4()).identity();
        //set -1 to the corresponding axis
        ms.elements[0] = -1;
        ms.elements[5] = -1;
        ms.elements[10] = -1;
        axesHelper2.applyMatrix(ms);
        
        this.scene.add(axesHelper2);

        //加载灯光
        let color = new THREE.Color(0.5, 0.5, 0.5);
	    let ambient = new THREE.AmbientLight(color.getHex());
        this.scene.add(ambient);
        
        //产品数据
        this.productData = [];
        this.products = []
        //加载产品
        //this.loadProduct();
        
        this.render()

        //绑定鼠标事件处理
        $(this.DOM_canvas).on("click",(evt)=>{
            evt.preventDefault();
            let mousePos = this.getClickMousePos(evt);
            //update picking ray
            this.raycaster.setFromCamera( mousePos, this.camera );
            let intersects = this.raycaster.intersectObjects( this.scene.children );
             
            if (intersects.length>=1){
                for(let i = 0;i<this.products.length;i++){
                    //found a target
                    if(intersects[0].object.uuid===this.products[i].thumb.uuid){
                        this.products[i].lightOn();
                        fillSlot(this.productData[i].id,this.productData[i].thumb_url)
                    }
                }
            }

            evt.stopPropagation();
        })
        $(window).on("resize",()=>{
            let w = $(this.DOM_container).get(0).clientWidth;
            let h = $(this.DOM_container).get(0).clientHeight;
            $(this.DOM_canvas).width(w);
            $(this.DOM_canvas).height(h);
            this.renderer.setViewport(0, 0, w, h);
            this.renderer.setSize( w, h);
            this.camera.aspect = w / h;
		    this.camera.updateProjectionMatrix();
            
        })

    }
    render(){
        requestAnimationFrame( ()=>{
            for(let i=0;i<this.products.length;i++){
                this.products[i].update();
            }
            this.camera.lookAt( this.scene.position );
	        this.camera.updateMatrixWorld();
            this.renderer.render(this.scene, this.camera);
            
            this.render()
        } );
    }
    getClickMousePos(event) {
        let x;
        let y;
        let e = event || window.event;
        if (e.pageX || e.pageY) { 
            x = e.pageX;
            y = e.pageY;
        }else { 
            x = e.clientX + document.body.scrollLeft + document.documentElement.scrollLeft; 
            y = e.clientY + document.body.scrollTop + document.documentElement.scrollTop; 
        } 
            x -= $(this.DOM_canvas).offset().left;
            y -= $(this.DOM_canvas).offset().top;
        let mousePos = new THREE.Vector2();
            mousePos.x = ( x / $(e.currentTarget).width() ) * 2 - 1;
            mousePos.y = - ( y / $(e.currentTarget).height() ) * 2 + 1;
        //alert('x: ' + x + '\ny: ' + y);
        //mousePos.addScalar(this.scale)
        console.log(mousePos)
        return mousePos;
    }
    loadSurveyData(surveyId){
        let self = this;
        let request_url = GET_SURVEY_DATA_URL+surveyId;
        
        $.ajax({
            type: "GET",
            url: request_url,
            success: (response_data)=>{
                this.productData = response_data.products;
                initSlot(this.productData[0].id,this.productData[0].thumb_url);
                this.loadProducts(this.productData)
                console.log(response_data)
            },
            error:(response_data)=>{
                alert(response_data)
            },
            dataType: "json",
            contentType: "application/json"
        })
        
    }
    loadProducts(product_data){

        for (let i=0;i<product_data.length;i++){
            let p = new Product(this,product_data[i].thumb_url)
            
            let x = product_data[i].style_location.x*this.scale;
            let y = product_data[i].style_location.y*this.scale;
            let z = product_data[i].style_location.z*this.scale;
            p.setPos(new THREE.Vector3( x, y, z ));

            this.products.push(p);
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
        
    }
}

