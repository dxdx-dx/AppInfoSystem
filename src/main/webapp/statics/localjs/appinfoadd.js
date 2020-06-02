$("#queryCategoryLevel1").change(function () {
    var queryCategoryLevel1 = $("#queryCategoryLevel1").val();
    if (queryCategoryLevel1 != '' && queryCategoryLevel1 != null) {
        $.ajax({
            type: "GET",//请求类型
            url: "categorylevellist",//请求的url
            data: {pid: queryCategoryLevel1},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                $("#queryCategoryLevel2").html("");
                var options = "<option value=\"\">--请选择--</option>";
                for (var i = 0; i < data.length; i++) {


                    options += "<option value=\"" + data[i].id + "\">" + data[i].categoryName + "</option>";
                }
                $("#queryCategoryLevel2").html(options);
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                alert("加载二级分类失败！");
            }
        });
    } else {
        $("#queryCategoryLevel2").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#queryCategoryLevel2").html(options);
    }
    $("#queryCategoryLevel3").html("");
    var options = "<option value=\"\">--请选择--</option>";
    $("#queryCategoryLevel3").html(options);
});

$("#queryCategoryLevel2").change(function () {
    var queryCategoryLevel2 = $("#queryCategoryLevel2").val();
    if (queryCategoryLevel2 != '' && queryCategoryLevel2 != null) {
        $.ajax({
            type: "GET",//请求类型
            url: "categorylevellist",//请求的url
            data: {pid: queryCategoryLevel2},//请求参数
            dataType: "json",//ajax接口（请求url）返回的数据类型
            success: function (data) {//data：返回数据（json对象）
                $("#queryCategoryLevel3").html("");
                var options = "<option value=\"\">--请选择--</option>";
                for (var i = 0; i < data.length; i++) {
                    //alert(data[i].id);
                    //alert(data[i].categoryName);
                    options += "<option value=\"" + data[i].id + "\">" + data[i].categoryName + "</option>";
                }
                $("#queryCategoryLevel3").html(options);
            },
            error: function (data) {//当访问时候，404，500 等非200的错误状态码
                alert("加载三级分类失败！");
            }
        });
    } else {
        $("#queryCategoryLevel3").html("");
        var options = "<option value=\"\">--请选择--</option>";
        $("#queryCategoryLevel3").html(options);
    }
});

$("#back").on("click", function () {
    window.location.href = "list";
});

$("#APKName").bind("blur", function () {
    //ajax后台验证--APKName是否已存在
    $.ajax({
        type: "GET",//请求类型
        url: "apkexist.json",//请求的url
        data: {APKName: $("#APKName").val()},//请求参数
        dataType: "json",//ajax接口（请求url）返回的数据类型
        success: function (data) {//data：返回数据（json对象）
            if (data.APKName == "empty") {//参数APKName为空，错误提示
                alert("APKName为不能为空！");
            } else if (data.APKName == "exist") {//账号不可用，错误提示
                alert("该APKName已存在,请重新输入！");
            } else if (data.APKName == "noexist") {//账号可用，正确提示
                alert("该APKName可以使用！");
            }
        },
        error: function (data) {//当访问时候，404，500 等非200的错误状态码
            alert("请求错误！");
        }
    });
});


      
      
      