package spring17.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import spring17.model.Comment;
import spring17.model.CommentExample;
import spring17.model.Question;

import java.util.List;

public interface CommentExtMapper {
    int incComment(Comment comment);
}