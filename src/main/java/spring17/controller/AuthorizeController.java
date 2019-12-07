package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.AccessTokenDTO;
import spring17.dto.GithubUser;
import spring17.provider.GithubProvider;

/**
 * Author:ShiQi
 * Date:2019/12/6-23:22
 */
@Controller
public class AuthorizeController {
    //将实例自动加载入上下文
    @Autowired
    private GithubProvider gp;

    //连接配置文件:
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.client.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO at = new AccessTokenDTO();
        at.setClientId(clientId);
        at.setClientSecret(clientSecret);
        at.setCode(code);
        at.setRedirectUri(redirectUri);
        at.setState(state);
        String accessToken = gp.getAccessToken(at);
        GithubUser user = gp.getUser(accessToken);
        System.out.println(user.getName());
        return"index";
    }
}
