package spring17.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import spring17.dto.AccessTokenDTO;
import spring17.dto.GithubUser;

import java.io.IOException;

/**
 * Author:ShiQi
 * Date:2019/12/6-23:29
 */
//以下注解可以自动对象实例化
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO atd){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //okhttp可以模拟post请求
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(atd));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token?client_id="
                        +atd.getClientId()+"&client_secret="+atd.getClientSecret()+"&code="
                        +atd.getCode()+"&redirect_uri="+atd.getRedirectUri()+"&state="+atd.getState())
                .post(body)
       //         .addHeader("Accept","application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
//access_token=25a9159657c86bbaa18a89db3c957a037238dc17&scope=user&token_type=bearer
            String token=string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .addHeader("Accept","application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String s=response.body().string();
            //将s的java对象自动解析成类对象
            GithubUser githubUser = JSON.parseObject(s, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
