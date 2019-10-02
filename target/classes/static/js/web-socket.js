var socket;
var sId;

function getId() {
    var scripts = document.scripts;
    for (var i = 0; i < scripts.length; i++) {
        var src = scripts[i].src;
        if (src.match(/web-socket.js/g)) {       //匹配指定js文件
            sId = src.match(/\?sId=[a-zA-z0-9]+/g).toString();  //获取参数，正则表达式自己写
            sId = sId.substring(5, sId.length)
            break;
        }
    }
}

if (!window.WebSocket) {
    window.WebSocket = window.MozWebSocket;
}

if (window.WebSocket) {
    getId();
    socket = new WebSocket("ws://127.0.0.1:8060/websocket/" + sId);
    socket.onmessage = function (event) {
        var ta = document.getElementById('responseText');
        ta.value += event.data + "\r\n";
    };
    socket.onopen = function (event) {
        var ta = document.getElementById('responseText');
        ta.value = "Netty-WebSocket服务器。。。。。。连接  \r\n";
    };
    socket.onclose = function (event) {
        var ta = document.getElementById('responseText');
        ta.value = "Netty-WebSocket服务器。。。。。。关闭 \r\n";
    };
} else {
    alert("您的浏览器不支持WebSocket协议！");
}

function send(message) {
    if (!window.WebSocket) {
        return;
    }
    if (socket.readyState == WebSocket.OPEN) {
        socket.send(message);
    } else {
        alert("WebSocket 连接没有建立成功！");
    }
}

function sendImitateSystemNews() {
    var index = layer.load();
    $.ajax({
        url: "/web/manage/socket/sendImitateSystemNews",
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            layer.msg(data.data, {time: 2000});
        },
        complete: function () {
            layer.close(index);
        }
    });
}
