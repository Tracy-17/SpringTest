package spring17.model;

import lombok.Data;

/**
 * Author:ShiQi
 * Date:2019/12/8-0:55
 */
@Data
public class Question {
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

}
