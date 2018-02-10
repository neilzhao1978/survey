/*
    根据页面post数据，在特定DOM位置渲染3D数据可视化
    依赖three.js
*/

const GET_PRODUCT_DATA_URL = host+"";

class Product{
    constructor(thumb_url){
        this.geometry = new THREE.PlaneGeometry(8, 5.4);
        this.material = new THREE.MeshBasicMaterial({
            color: 0xCCCCCC,
            side: THREE.DoubleSide
        });
        this.mesh = new THREE.Mesh(this.geometry, this.material);
        this.thumb = new THREE.TextureLoader();
        this.mesh.up = new THREE.Vector3(0, 0, 1)
        this.loadThumbImage(thumb_url)
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
    getMesh () {
        return this.mesh;
    };
}

class DataVis3DRenderer{

    constructor(DOM_ele){
        let self = this;
        this.frameId = 0;
        //获取容器
        let DOM_canvas = $("#"+DOM_ele).find("canvas").get(0)

        this.productData = null;

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

        //OrbitControls
        this.orbit = new THREE.OrbitControls( this.camera, this.renderer.domElement );

        //显示坐标轴辅助线
        let axisHelper = new THREE.AxisHelper(5);
        this.scene.add(axisHelper);
        
        //加载灯光
        let color = new THREE.Color(0.5, 0.5, 0.5);
	    let ambient = new THREE.AmbientLight(color.getHex());
        this.scene.add(ambient);
        
        //加载产品
        this.loadProduct();
        
        
        this.render()

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
            let p = new Product("http://design-pinwall.qiniudn.com/5081/1517128537264.png?imageMogr2/thumbnail/200x200")
            this.scene.add(p.getMesh())
            let x = 50-Math.random()*100;
            let y = 50-Math.random()*100;
            let z = 50-Math.random()*100;
            p.setPos(new THREE.Vector3( x, y, z ));
            p.lookAt(new THREE.Vector3( 0, 0, z ));
        }

        
        
        // let geometry = new THREE.SphereGeometry(1, 32, 32);
	    // let material = new THREE.MeshPhongMaterial({
        //     color: new THREE.Color('grey')
        // });
        // let sphere = new THREE.Mesh(geometry, material);
        // this.scene.add(sphere);
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

