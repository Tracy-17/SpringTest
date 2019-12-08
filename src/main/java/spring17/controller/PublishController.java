package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.mapper.QuestionMapper;
import spring17.mapper.UserMapper;
import spring17.model.Question;
import spring17.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/7-23:03
 */
//关于以下@Controller的必要性：https://blog.csdn.net/u010412719/article/details/69710480
@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")//get方法：渲染页面
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            //model可以把数据推送到前端页面
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        //逻辑校验（建议写在前端）
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题描述不能为空！");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空！");
            return "publish";
        }
        //获取当前页面用户信息
        User user = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ((cookie.getName()).equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findByToken(token);
                if (user != null) {
                    //写入session
                    request.getSession().setAttribute("user", user);
                }
                break;//命中结束循环
            }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "/publish";
        }
        //评论数据进库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        question.setCreator(user.getId());//拿到的是登录的id（待改善）
//        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/index";
    }
}
