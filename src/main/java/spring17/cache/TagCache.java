package spring17.cache;

import org.apache.commons.lang3.StringUtils;
import spring17.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Author:ShiQi
 * Date:2019/12/17-20:49
 * 标签库
 */
public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO animation = new TagDTO();
        animation.setCategoryName("动画");
        animation.setTags(Arrays.asList("后宫","热血","穿越","奇幻","泡面","萌系","治愈","校园","架空","运动","社团","偶像","乙女","职场","励志","智斗","催泪"));

        TagDTO comic = new TagDTO();
        comic.setCategoryName("漫画");
        comic.setTags(Arrays.asList("冒险","搞笑","恋爱","少女"));

        TagDTO drama = new TagDTO();
        drama.setCategoryName("电视剧");
        drama.setTags(Arrays.asList("古装","武侠","奇幻","都市"));

        tagDTOS.add(animation);
        tagDTOS.add(comic);
        tagDTOS.add(drama);
        return tagDTOS;
    }
    public static String filterInvalid(String tags){
        String[] split=StringUtils.split(tags,"[,\\，]");
        List<TagDTO> tagDTOS=get();
        //flatMap:二维数组两层循环
        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        //目前不在标签库的都无效（待改进）
        String invalid = Arrays.stream(split).filter(t ->! tagList.contains(t)).collect(Collectors.joining("，"));
       return invalid;
    }
}
