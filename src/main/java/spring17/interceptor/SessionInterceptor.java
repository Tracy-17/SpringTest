package spring17.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import spring17.mapper.UserMapper;
import spring17.model.Notification;
import spring17.model.User;
import spring17.model.UserExample;
import spring17.service.NotificationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/9-17:06
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired//没有@Service，这里的自动注入不生效
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;//用于显示页面通知数

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0)//???这里不加{}也可以验证吗？
            for (Cookie cookie : cookies) {
                if ((cookie.getName()).equals("token")) {
                    String token = cookie.getValue();

                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if(users.size()!=0){
                        //写入session
                        request.getSession().setAttribute("user",users.get(0));
                        int unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadCount",unreadCount);
                    }
                    break;//命中结束循环
                }
            }
        //true:handler继续执行，false停止
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
