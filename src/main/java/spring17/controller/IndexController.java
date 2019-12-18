package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.PaginationDTO;
import spring17.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/6-20:11
 * 访问index--检查登录状态--展示内容数据
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/index")
    //这里只写/ 火狐测试ok，chrome需要在所有地方补全/index
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1")Integer page,
                        @RequestParam(name="size",defaultValue = "5")Integer size) {

        //获取question数据
        PaginationDTO pagination=questionService.List(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
