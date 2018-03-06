/* 
文件说明：页面加载时Loading JS 
文件描述：解决IE或FF下，初始化加载时，页面布局乱掉的问题，参考：http://283433775.iteye.com/blog/720895 
*/  
var width = $(window).width();  
var height = $(window).height();  
  
var html = "<div id='loading' style='z-index:999;position:absolute;left:0;right:0;top:0;background:#ffffff;opacity:0.8;filter:alpha(opacity=80);'>";  
html += "<div style='position:absolute;cursor:wait;width:200px;padding:5px 10px;left:50%;top:0; transform:translate(-50%,0);text-align:center;font-size:12px;";  
html += "background:#fff url(" + "../../../common/lib/easyui/themes/default/images/loading.gif) no-repeat 10px center;color:#000;'>";  
html += "正在加载，请等待...";  
html += "</div>";  
html += "</div>";  
  
window.onload = function () {  
    var mask = document.getElementById('loading');  
    mask.parentNode.removeChild(mask);  
};  
document.write(html);  