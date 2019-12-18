package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.PaginationDTO;
import spring17.model.User;
import spring17.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/9-12:09
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")//访问profile+动态页面时调到此处
    public String profile(HttpServletRequest request,
                          @PathVariable(name="action")String action,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1")Integer page,
                          @RequestParam(name="size",defaultValue = "5")Integer size){
        //验证登录：
        User user = (User)request.getSession().getAttribute("user");
        //未登录跳转：
        if(user==null){
            return "redirect:/index";
        }

        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的问题");
        }else if("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
