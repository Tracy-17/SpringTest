package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spring17.dto.CommentCreateDTO;
import spring17.dto.ResultDTO;
import spring17.exception.CustomizeErrorCode;
import spring17.model.Comment;
import spring17.model.User;
import spring17.service.CommentService;

import javax.servlet.http.HttpServletRequest;

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
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();

        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());

        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());

        comment.setCommentator(user.getId());
        commentService.insert(comment);

        return ResultDTO.okOf();
    }
}
