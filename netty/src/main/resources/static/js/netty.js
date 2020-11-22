var netty = {
    socket:null,
    init:function () {
        var webSocketUrl = "wss://localhost:8081/websocket";
        if (!window.WebSocket) {
            window.WebSocket = window.MozWebSocket;
        }
        if (window.WebSocket) {
            netty.socket = new WebSocket(webSocketUrl);
            netty.initEvent();
        } else {
            alert("您的浏览器不支持WebSocket协议,建议使用Chrome浏览器！");
        }
    },
    initEvent:function(){
        var socket = netty.socket;
        socket.onopen = function(event) {
            var text = "打开WebSoket 服务正常，浏览器支持WebSoket!" + "\r\n";
            var data = {
                "type":1,
                "userId" : common.getUserId()
            };
            netty.sendMessage(JSON.stringify(data));
            heartCheck.start();
        };
        socket.onclose = function(event) {
            reconnect();
        };
        socket.error = function(event) {
            reconnect();
        };
        socket.onmessage = function(event) {
            netty.receiveMessage(event.data);
            heartCheck.reset();
        };
    },
    sendMessage:function(message) {
        var socket = netty.socket;
        if (socket.readyState == WebSocket.OPEN) {
            socket.send(message);
        } else {
            console.log("WebSocket 连接没有建立成功！");
        }
    },
    receiveMessage:function(data){
        var message = JSON.parse(data);
        console.log(message);
    }
};
/**
 * 心跳检测
 */
var heartCheck = {
    timeout: 30000,//60ms
    timeoutObj: null,
    reset: function(){
        clearTimeout(this.timeoutObj);
        this.start();
    },
    start: function(){
        this.timeoutObj = setTimeout(function(){
            sendMessage("ping");
        }, this.timeout)
    }
};