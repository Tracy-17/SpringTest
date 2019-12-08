package spring17.dto;

import lombok.Data;
import spring17.model.User;

/**
 * Author:ShiQi
 * Date:2019/12/8-18:14
 * 展示给前端专用
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    //需要展示给前端用户信息，但不存储进数据库
    private User user;
}
