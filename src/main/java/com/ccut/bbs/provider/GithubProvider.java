package com.ccut.bbs.provider;

import com.alibaba.fastjson.JSON;
import com.ccut.bbs.dto.AccessTokenDTO;
import com.ccut.bbs.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  //仅仅把当前类初始化到spring容器的上下文，可以理解为加了这个注解后,在调用时不需要实例化这个对象；即 Spring IOC实现
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //使用okhttp   post到server
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            try {
                String string =  response.body().string();
                String token = string.split("&")[0].split("=")[1];
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string =  response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class); //把string的JSON对象自动转换解析成java的类对象
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
