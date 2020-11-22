var common = {
    loadChatItem:function() {
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
                            lis += '<li class="chat-user-item active" data-chat-id="'+item.chatId+'">'+item.name+'</li>';
                        }else{
                            lis += '<li class="chat-user-item" data-chat-id="'+item.chatId+'">'+item.name+'</li>';
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
                    loadChatWinHis();
                } else {
                    alert(data.msg);
                }
            },
        });
    },
    getChatId:function() {
        return $(".active").attr("data-chat-id");
    }
}

$(function () {
    common.loadChatItem();
})