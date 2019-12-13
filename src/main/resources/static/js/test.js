function post() {
    //jquery获取th:value的值
    let questionId = $("#question_id").val();
    //jquery获取textarea的值
    let content = $("#comment_content").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":content,
            "type":1
    }),
        success:function(response){
            if(response.code==200){
                $("#comment_section").hide();
            }else if(response.code==2003){
                //未登录
                let isAccepted = confirm(response.message);
                if(isAccepted){
                    //点击确定，自动登录（火狐被拦截了。。。）
                    window.open("https://github.com/login/oauth/authorize?client_id=adb444ad45124c00105f&redirect_uri=http://localhost:8080/callback?scope=user&state=1");
                    //设置自动登录成功后自动关掉登录页，并回到评论页
                    window.localStorage.setItem("closable",true);
                }
            }else{
                    alert(response.message);
            }
        },
        dataType:"json"
    });
}