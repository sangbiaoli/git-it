var common = {
    login : function() {
        var param = {
            "username":$("#username").val(),
            "passwd":$("#passwd").val()
        };

        $.ajax({
            type: "post",
            url: '/admin/user/doLogin',
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(param),
            success: function (data) {
                if (data.code == 0) {
                    alert('操作成功');
                    window.location.href = "/admin/page/index";
                } else {
                    alert(data.msg);
                }
            }
        });
    }
}