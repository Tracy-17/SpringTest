package spring17.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring17.dto.CommentDTO;
import spring17.enums.CommentTypeEnum;
import spring17.enums.NotificationStatusEnum;
import spring17.enums.NotificationTypeEnum;
import spring17.exception.CustomizeErrorCode;
import spring17.exception.CustomizeException;
import spring17.mapper.*;
import spring17.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Author:ShiQi
 * Date:2019/12/13-20:02
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

//    Transactional:将整个方法体设为同一个事物
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //增加父评论的评论数
            Comment parentComment =new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incComment(parentComment);
            //创建通知
            createNotify(comment, dbComment.getCommentator(), NotificationTypeEnum.REPLY_COMMENT);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
            //创建通知
            createNotify(comment,question.getCreator(), NotificationTypeEnum.REPLY_QUESTION);
        }
    }

    private void createNotify(Comment comment, Long receiver, NotificationTypeEnum notificationType) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setType(NotificationStatusEnum.UNREAD.getStatus());
        notification.setOuterid(comment.getParentId());
        notification.setNotifier(comment.getCommentator());
        notification.setReceiver(receiver);
        //状态：0：未读，1：已读
        notification.setStatus(0);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                //为问题回复
                //ctrl+alt+P：Extracting parameter to listByQuestionId(Integer)抽参数;ctrl+F6修改参数类型
                .andTypeEqualTo(type.getType());
        //按时间倒序显示回复：
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        // λ ？获取去重的评论者
        //新语法：map（）遍历以下得到结果集，collect得到结果集，toSet转换为Set
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        //获取评论者并转换为map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        //comments与users匹配：
  /*效率过低：for(Comment comment:comments){for(User user:users){}}*/
  //同上，将user做成map
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换Comment为CommentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            //两类之间copy：BeanUtils.copyProperties（source,target）;
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
