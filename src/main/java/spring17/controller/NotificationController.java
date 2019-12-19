package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import spring17.dto.NotificationDTO;
import spring17.dto.PaginationDTO;
import spring17.enums.NotificationTypeEnum;
import spring17.mapper.NotificationMapper;
import spring17.model.Notification;
import spring17.model.User;
import spring17.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

/**
 * Author:ShiQi
 * Date:2019/12/19-14:51
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("notification/{id}")//访问profile+动态页面时调到此处
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {
        //验证登录：
        User user = (User) request.getSession().getAttribute("user");
        //未登录跳转：
        if (user == null) {
            return "redirect:/index";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);
        if(NotificationTypeEnum.REPLY_COMMENT.getType()==notificationDTO.getType()
        ||NotificationTypeEnum.REPLY_QUESTION.getType()==notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }else{
            return "redirect:/index";
        }

    }
}
