package spring17.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:ShiQi
 * Date:2019/12/8-21:43
 * 返回列表及页码信息
 * 待学习：pageHelper
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questionDTOS;
    //是否有向前按钮
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;
    //当前展示的页面列表
    private List<Integer> pages = new ArrayList<>();

    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        //容错处理
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        //改变当前属性
        this.page=page;

        //显示当前页面
        pages.add(page);
        //左右各显示三页
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                //从头部加，往前循环
                pages.add(0,page-i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //是否展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示首页：页码“1”被展示时，不展示“首页”按钮
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示末页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
