package com.ccut.bbs.controller;


import com.ccut.bbs.dto.AccessTokenDTO;
import com.ccut.bbs.dto.GithubUser;
import com.ccut.bbs.mapper.UserMapper;
import com.ccut.bbs.model.User;
import com.ccut.bbs.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 负责接收github给callback回来的内容
 */
@Controller
public class AuthorizeController {

    @Autowired  //自动把spring容器里写好的实例化的实例加载到要使用的上下文中
    private GithubProvider githubProvider;

    @Value("${github.client.id}")  //配置文件里读key是github.client.id的value，把它赋值到client_id
    private String clientId;

   @Value("${github.client.secret}")   //配置文件里读key是github.client_secret的value，把它赋值到client_secret
    private String clientSecret;

    @Value("${github.redirect.uri}") //配置文件里读key是github..redirect_uri的value，把它赋值到redirect_uri
    //private String redirectUri;
    private String redirectUri = "http://localhost:8887/callback";

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,  //请求参数接收网站的code
                           @RequestParam(name = "state") String state, //请求参数接收网站的state
                           HttpServletRequest request,
                           HttpServletResponse response
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId())); //使用string。valueof强转一下
            user.setGmtCreate(System.currentTimeMillis()); //用当前的毫秒数传进去
            user.setGmtModified(user.getGmtCreate()); //在create里获取传进去的毫秒值
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";  //跳转到主页
        }else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
