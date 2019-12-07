package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.AccessTokenDTO;
import spring17.dto.GithubUser;
import spring17.mapper.UserMapper;
import spring17.model.User;
import spring17.provider.GithubProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           //写了HttpServletRequest后，spring会自动将request放入上下文以供使用
                           HttpServletRequest request){
        AccessTokenDTO at = new AccessTokenDTO();
        at.setClientId(clientId);
        at.setClientSecret(clientSecret);
        at.setCode(code);
        at.setRedirectUri(redirectUri);
        at.setState(state);
        String accessToken = gp.getAccessToken(at);
        GithubUser githubUser = gp.getUser(accessToken);
        if(githubUser!=null){
            //登录成功
            User user = new User();
            //javaJDK提供的一个自动生成主键的方法:
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            
            userMapper.insert(user);
            // 写cookie和session
            request.getSession().setAttribute("user",user);
            //有redirect前缀，会把地址栏刷新重定向到index
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:index";
        }
    }
}
