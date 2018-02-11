/*
    根据页面post数据，在特定DOM位置渲染3D数据可视化
    依赖three.js
*/

const GET_PRODUCT_DATA_URL = host+"";

class Product{
    constructor(_scene,thumb_url){
        this.scene = _scene
        this.geometry = new THREE.PlaneGeometry(8, 5.4);
        this.material = new THREE.MeshBasicMaterial({
            color: 0xCCCCCC,
            side: THREE.DoubleSide
        });
        this.mesh = new THREE.Mesh(this.geometry, this.material);
        this.mesh.up = new THREE.Vector3(0, 0, 1);
        this.scene.add(this.mesh);


        this.thumb = new THREE.TextureLoader();
        this.loadThumbImage(thumb_url);
    }
    loadThumbImage (url) {
        this.thumb.load(url,  (t)=> {
            this.material = new THREE.MeshBasicMaterial({
                map: t,
                side: THREE.DoubleSide
            });
            this.mesh.material = this.material;
        });
    }
    setPos(v3){
        this.mesh.position.set(v3.x,v3.y,v3.z);
    }
    lookAt(v3){
        this.mesh.lookAt(v3)
    }
    addToScene(){
        this.scene.add(this.mesh)
    }
    lightOn(){

    }
    lightOff(){

    }
}

class DataVis3DRenderer{

    constructor(DOM_ele){
        let self = this;
        this.frameId = 0;
        //获取容器
        let DOM_canvas = $("#"+DOM_ele).find("canvas").get(0);
        //创建THREEJS渲染器实例
        this.renderer = new THREE.WebGLRenderer({
            antialias: true,
            alpha: true,
            canvas: DOM_canvas
        });
        this.renderer.setPixelRatio( window.devicePixelRatio )
        this.scene = new THREE.Scene();
	    this.center = new THREE.Vector3();
	    this.camera = new THREE.PerspectiveCamera(60, 1, 0.01, 400);
        this.camera.up = new THREE.Vector3(0, 0, 1);
        this.camera.position.set(100,100,100);
        this.camera.lookAt(this.center);
        this.raycaster = new THREE.Raycaster();  

        //OrbitControls
        this.orbit = new THREE.OrbitControls( this.camera, this.renderer.domElement );

        //显示坐标轴辅助线
        let axisHelper = new THREE.AxisHelper(5);
        this.scene.add(axisHelper);
        
        //加载灯光
        let color = new THREE.Color(0.5, 0.5, 0.5);
	    let ambient = new THREE.AmbientLight(color.getHex());
        this.scene.add(ambient);
        
        //产品数据
        this.productData = null;
        this.products = []
        //加载产品
        this.loadProduct();
        
        this.render()

        //绑定鼠标事件处理
        $(DOM_canvas).on("click",(evt)=>{
            let mousePos = new THREE.Vector2();
            mousePos.x = ( evt.clientX / window.innerWidth ) * 2 - 1;
            mousePos.y = - ( evt.clientY / window.innerHeight ) * 2 + 1;
            //update picking ray
            this.raycaster.setFromCamera( mousePos, this.camera );
            let intersects = this.raycaster.intersectObjects( scene.children );
            //found a target
            if (intersects.length>=1){
                let target = intersects[ 0 ].object;
                for(let i = 0;i<this.products.length;i++){
                    this.products[i].lightOff();
                    if (target === this.products[i].mesh){
                        this.products[i].lightOn();
                    }
                }
            }

        })

    }
    render(){
        requestAnimationFrame( ()=>{
            this.renderer.render(this.scene, this.camera);
            this.render()
        } );
    }
    
    loadProductData(options){
        let self = this;
        let request_url = GET_PRODUCT_DATA_URL;
        let opt_data = options;
        
        $.ajax({
            type: "POST",
            url: request_url,
            data: opt_data,
            success: (response_data)=>{
                
            },
            error:(response_data)=>{
                
            },
            dataType: "json",
            contentType: "application/json"
        })
        
    }
    loadProduct(){

        for (let i=0;i<100;i++){
            let p = new Product(this.scene,"http://design-pinwall.qiniudn.com/5081/1517128537264.png?imageMogr2/thumbnail/200x200")
            
            let x = 50-Math.random()*100;
            let y = 50-Math.random()*100;
            let z = 50-Math.random()*100;
            p.setPos(new THREE.Vector3( x, y, z ));
            p.lookAt(new THREE.Vector3( 0, 0, z ));

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

