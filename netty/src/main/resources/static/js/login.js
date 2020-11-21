var common = {
    login : function() {
        var param = {
            "username":$("#username").val(),
            "password":$("#password").val()
        };

        $.ajax({
            cache: true,
            type: "POST",
            url: '/admin/user/doLogin',
            data: param,
            dataType:'json',
            async: false,
            success: function (data) {
                if (data.code == 0) {
                    layer.msg('操作成功');
                    this.href = "/index";
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }
}