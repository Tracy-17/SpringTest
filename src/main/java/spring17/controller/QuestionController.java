package spring17.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring17.dto.CommentDTO;
import spring17.dto.QuestionDTO;
import spring17.enums.CommentTypeEnum;
import spring17.service.CommentService;
import spring17.service.QuestionService;

import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/9-18:23
 */
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name="id")Long id,
                           Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        List<QuestionDTO> relatedQuestions=questionService.selectRelated(questionDTO);

        List<CommentDTO> comments=commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
