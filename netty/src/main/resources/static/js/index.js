var common = {
    loadChatItem:function() {
        $.ajax({
            type: "post",
            url: '/admin/user/loadChatItem',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(param),
            success: function (data) {
                if (data.code == 0) {
                    var lis = "";
                    for(var i = 0;i < data.data.length;i++){
                        var item = data.data[i];
                        if(i == 0){
                            lis += '<li class="chat-user-item active" data-id="'+item.id+'" data-type="'+item.type+'">'+item.name+'</li>';
                        }else{
                            lis += '<li class="chat-user-item" data-id="'+item.id+'" data-type="'+item.type+'">'+item.name+'</li>';
                        }
                    }
                    $("#chat-users").innerHTML = lis;
                    loadChatWinHis();
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
                "id":item.attr("id"),
                "type":item.attr("type")
            };
            alert(param);
        }
    }
}

$(function () {
    common.loadChatItem();
})