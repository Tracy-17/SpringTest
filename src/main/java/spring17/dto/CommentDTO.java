package spring17.dto;

import lombok.Data;

/**
 * Author:ShiQi
 * Date:2019/12/13-19:12
 */
@Data
public class CommentDTO {
    private Integer parentId;
    private String content;
    private Integer type;
}
