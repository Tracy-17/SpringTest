package spring17.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.cache.TagCache;
import spring17.dto.QuestionDTO;
import spring17.model.Question;
import spring17.model.User;
import spring17.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/7-23:03
 */
//关于以下@Controller的必要性：https://blog.csdn.net/u010412719/article/details/69710480
@Controller
public class PublishController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id")Long id,
                       Model model){
        QuestionDTO question = questionService.getById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @GetMapping("/publish")//get方法：渲染页面
    public String publish(Model model) {
        //标签库
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value ="title",required = false) String title,
            @RequestParam(value ="description",required = false) String description,
            @RequestParam(value ="tag",required = false) String tag,
            @RequestParam(value = "id",required = false)Long id,
            HttpServletRequest request,
            //model可以把数据推送到前端页面
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());
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

        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error", "输入非法标签："+invalid);
            return "publish";
        }
        //获取当前页面用户信息
        //验证登录：
        User user = (User)request.getSession().getAttribute("user");
        //未登录跳转：
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "/publish";
        }
        //提问数据进库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);


        question.setCreator(user.getId());

        //存在风险：非法修改
        questionService.createOrUpdate(question);
        return "redirect:/index";
    }
}
