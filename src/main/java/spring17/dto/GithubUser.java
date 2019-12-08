package spring17.dto;

import lombok.Data;

/**
 * Author:ShiQi
 * Date:2019/12/7-0:09
 */
@Data
public class GithubUser {
    private String name;
    private long id;
    private String bio;
    //获取用户头像：
    private String avatar_url;
}
