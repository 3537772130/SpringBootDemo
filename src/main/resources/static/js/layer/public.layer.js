/**
 * layer
 * 
 * @time 20160217
 * 需引用layer.js
 * <script type="text/javascript" src="/scripts/layer/layer.js" merge="true"></script>
 */
layer.config({
	path : basePath+'/scripts/layer/' // layer.js所在的目录，可以是绝对目录，也可以是相对目录
});
// 可以自动关闭的提示
function jsprint(msgtitle, url, msgcss) {	
	var ic = 0;
	switch (msgcss) {
	case "Success":
		ic = 1;
		break;
	case "Error":
		ic = 2;
		break;
	default:
		ic = 0;
		break;
	}
	layer.msg(msgtitle, {
		icon : ic
	}, function() {
		if (url == "back") {
			window.history.back(-1);
		} else if (url != "") {
			window.location.href = url;
		}				
	});			
}
//询问框
function confi(msg,fun1,fun2){
	layer.confirm(msg, {
	    btn: ['确定','取消'] //按钮
	}, fun1,fun2);
	/*function(){
	    layer.msg('的确很重要', {icon: 1});
	}, function(){
	    layer.msg('也可以这样', {
	        time: 20000, //20s后自动关闭
	        btn: ['明白了', '知道了']
	    });
	});*/
}
//加载层
function openload(){
	layer.load(1,{time:20*1000});//设定最长等待20秒
	setTimeout('exitload()',5000);
}
//关闭加载层
function exitload(){
	var index=layer.load();
	layer.close(index);	
}
//页面一打开就执行弹层
layer.ready(function(){
    layer.msg('很高兴一开场就见到你',{icon:6});
}); 