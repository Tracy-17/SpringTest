package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spring17.dto.CommentDTO;
import spring17.dto.ResultDTO;
import spring17.exception.CustomizeErrorCode;
import spring17.mapper.CommentMapper;
import spring17.model.Comment;
import spring17.model.User;
import spring17.service.CommentService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:ShiQi
 * Date:2019/12/13-18:40
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

//  @ResponseBody：将对象自动序列化成json发送到前端
    @ResponseBody
    @RequestMapping(value="/comment",method = RequestMethod.POST)
//  @RequestBody:自动生成json
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();

        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());

        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return ResultDTO.okOf();
    }
}
