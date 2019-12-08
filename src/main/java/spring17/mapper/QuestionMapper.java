package spring17.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import spring17.model.Question;

import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/8-0:48
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag)values" +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    //传非数据库对象需要自己做映射
    List<Question> List(@Param(value="offset") Integer offset, @Param(value="size")Integer size);

    @Select("select count(1) from question")
    Integer count();
}
