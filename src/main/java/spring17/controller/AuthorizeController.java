package spring17.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.AccessTokenDTO;
import spring17.dto.GithubUser;
import spring17.model.User;
import spring17.provider.GithubProvider;
import spring17.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Author:ShiQi
 * Date:2019/12/6-23:22
 * Slf4j:lombok的注解，生成日志文件
 */
@Controller
@Slf4j
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

    //针对重复登录创建的优化：
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           //写了HttpServletRequest后，spring会自动将request放入上下文以供使用
                           HttpServletResponse response) {
        AccessTokenDTO at = new AccessTokenDTO();
        at.setClientId(clientId);
        at.setClientSecret(clientSecret);
        at.setCode(code);
        at.setRedirectUri(redirectUri);
        at.setState(state);
        String accessToken = gp.getAccessToken(at);
        GithubUser githubUser = gp.getUser(accessToken);

        if (githubUser != null) {
            //登录成功，获取用户信息
            User user = new User();
            String token = UUID.randomUUID().toString();//javaJDK提供的一个自动生成主键的方法:
            //疑问：token是每次都变的吗？
            //是的，token是cookie，每次退出登录会清除。
            user.setToken(token);

            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatar_url());
            user.setBio(githubUser.getBio());
            userService.createOrUpdate(user);
            // 写cookie和session
            response.addCookie(new Cookie("token", token));
            //有redirect前缀，会把地址栏刷新重定向到index
            return "redirect:/index";
        } else {
            //日志文件：{}：将后面的内容打印到前面
            log.error("callback get github error,{}",githubUser);
            //登录失败，重新登录
            return "redirect:/index";
        }
    }
    //退出登录：
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        //清除缓存数据
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
//        deleteNewCookie.setPath("/");设置本cookie的存放路径（本项目不需要）
        response.addCookie(cookie);
        return "redirect:/index";
    }
}
/*    12.9在登陆问题解决前我的逻辑
    //判断此账户是否存在
    Boolean isExist(String accountId) {
        return userMapper.findByAccountID(accountId) == null ? true : false;
    }

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           //写了HttpServletRequest后，spring会自动将request放入上下文以供使用
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO at = new AccessTokenDTO();
        at.setClientId(clientId);
        at.setClientSecret(clientSecret);
        at.setCode(code);
        at.setRedirectUri(redirectUri);
        at.setState(state);
        String accessToken = gp.getAccessToken(at);
        GithubUser githubUser = gp.getUser(accessToken);
        if (githubUser != null) {
            //登录成功，获取用户信息
            User user = new User();
            String token = UUID.randomUUID().toString();//javaJDK提供的一个自动生成主键的方法:
            //疑问：token是每次都变的吗？
            user.setToken(token);
            //修改登录记录
            long nowTime = System.currentTimeMillis();
            user.setGmtModified(nowTime);
            //如果数据库有这个id，就不新建用户了
            String accountId = user.getAccountId();
            if (isExist(accountId)) {
                //存在
                //创建session
                request.getSession().setAttribute("user",user);
                                                                                  //名字和头像可能变化，故必须创建更新方法
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                user.setAvatarUrl(githubUser.getAvatar_url());
                //注册时间
                user.setGmtCreate(nowTime);
            } else {
                //不存在，新用户
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setName(githubUser.getName());
                user.setAvatarUrl(githubUser.getAvatar_url());
                //注册时间
                user.setGmtCreate(nowTime);
                //当前用户总数：
                Integer userNum=userMapper.count();
                user.setId(userNum+1);//手动自增（勉为其难.jpg）
                //存入数据库
                userMapper.insert(user);//作为session
            }


            // 写cookie和session
            response.addCookie(new Cookie("token", token));
            //有redirect前缀，会把地址栏刷新重定向到index
            return "redirect:/index";
        } else {
            //登录失败，重新登录
            return "redirect:/index";
        }
    }
*/
