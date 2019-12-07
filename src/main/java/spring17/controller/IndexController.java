package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import spring17.mapper.UserMapper;
import spring17.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/6-20:11
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    //这里只写/ 火狐测试ok，chrome需要在所有地方补全/index
    public String index(HttpServletRequest request) {
        //去模板寻找hello：
//        model.addAttribute("name",name);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ((cookie.getName()).equals("token")) {
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if(user!=null){
                    //写入session
                    request.getSession().setAttribute("user",user);
                }
                break;//命中结束循环
            }
        }
        return "index";
    }
}
