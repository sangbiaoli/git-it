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
                            lis += '<li class="chat-user-item active" data-id="'+item.id+'" data-type="'+item.type+'">'+item.name+'</li>';
                        }else{
                            lis += '<li class="chat-user-item" data-id="'+item.id+'" data-type="'+item.type+'">'+item.name+'</li>';
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
        var item = $(".active");
        if(item){
            var param = {
                "id":item.attr("data-id"),
                "type":item.attr("data-type")
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
        }
    }
}

$(function () {
    common.loadChatItem();
})