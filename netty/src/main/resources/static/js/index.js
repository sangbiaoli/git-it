var mycook = {
    obj: {},
    init:function(){
        var cookies = document.cookie.split(";");
        for(var i = 0;i < cookies.length;i++){
            var s = cookies[i].split("=");
            mycook.obj[s[0].trim()] = s[1];
        }
    },
    getUserId:function(){
        return mycook.obj["userId"];
    },
    getUserName:function(){
        return mycook.obj["username"];
    },
};
var common = {
    init:function() {
        $.ajax({
            type: "post",
            url: '/admin/chat/loadChatItem',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if (data.code == 0) {
                    $("#chat-users").empty();
                    var lis = "";
                    for(var i = 0;i < data.data.length;i++){
                        var item = data.data[i];
                        if(i == 0){
                            lis += '<li class="chat-user-item active" data-chat-id="'+item.chatId+'" onclick="common.active(this);">'+item.name+'</li>';
                        }else{
                            lis += '<li class="chat-user-item" data-chat-id="'+item.chatId+'" onclick="common.active(this);">'+item.name+'</li>';
                        }
                    }
                    $("#chat-users").html(lis);
                    common.loadChatWinHis();
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    getChatId:function() {
        return $(".active").attr("data-chat-id");
    },
    active:function(obj){
        var li = $(".active");
        var self = $(obj);
        if(li.attr("data-chat-id") != self.attr("data-chat-id")){
            li.removeClass("active");
            self.addClass("active");
            common.loadChatWinHis();
        }
    },
    loadChatWinHis:function () {
        var param = {
            "chatId":common.getChatId()
        };
        $.ajax({
            type: "post",
            url: '/admin/chat/loadChatHis',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(param),
            success: function (data) {
                if (data.code == 0) {
                    var userId = mycook.getUserId();
                    $("#chat-his").empty();
                    var lis = "";
                    for(var i = 0;i < data.data.length;i++){
                        var item = data.data[i];
                        if(item.user.id == userId){
                            lis += '<li class="my-his">'+item.content+'</li>';
                        }else{
                            lis += '<li class="not-my-his">'+item.content+'</li>';
                        }
                    }
                    $("#chat-his").html(lis);
                } else {
                    alert(data.msg);
                }
            }
        });
    },
    send:function () {
        var param = {
            "chatId": common.getChatId(),
            "content":$("#content").val()
        };
        $.ajax({
            type : "post",
            url: '/admin/chat/send',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(param),
            success : function(data, status) {
                if (data.code == 0) {
                    common.loadChatWinHis();
                } else {
                    alert(data.msg);
                }
            },
        });
    }
};


$(function () {
    mycook.init();
    common.init();
    netty.init();
    $("#username").html(mycook.getUserName());
})