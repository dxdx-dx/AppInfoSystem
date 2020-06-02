package cn.appsys.service.developer;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.DataDictionary;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:59
 */
public interface AppCategoryService {

    //查询一级分类
    List<AppCategory> getCategoryLevel1();

    //查询二,三级分类
    List<AppCategory> getCategoryLevel2(Integer parentId);


}
