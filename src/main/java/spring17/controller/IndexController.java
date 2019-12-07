package spring17.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author:ShiQi
 * Date:2019/12/6-20:11
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String hello(){
//        model.addAttribute("name",name);
        //去模板寻找hello：
        return "index";
    }
}
