package spring17.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import spring17.model.Question;
import spring17.model.QuestionExample;

import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/8-0:48
 */
@Mapper
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
}