package spring17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Author:ShiQi
 * Date:2019/12/6-20:11
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String hello(@RequestParam(name="name")String name, Model model){
//        model.addAttribute("name",name);
        //去模板寻找hello：
        return "index";
    }
}
