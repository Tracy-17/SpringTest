package spring17.service;

import org.springframework.beans.BeanUtils;//spring的性能较好
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring17.dto.PaginationDTO;
import spring17.dto.QuestionDTO;
import spring17.mapper.QuestionMapper;
import spring17.mapper.UserMapper;
import spring17.model.Question;
import spring17.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/8-18:19
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO List(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        //容错处理
        if(page<1){
            page=1;
        }
        if(page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }

        //size*(page-1)
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.List(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties:对象之间属性的赋值
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        paginationDTO.setQuestionDTOS(questionDTOS);

        return paginationDTO;
    }
}
