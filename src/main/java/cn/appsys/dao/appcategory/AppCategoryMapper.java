package cn.appsys.dao.appcategory;

import cn.appsys.pojo.AppCategory;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:29
 */
public interface AppCategoryMapper {
    //查询一级分类
    List<AppCategory> getCategoryLevel1();

    //查询二,三级分类
    List<AppCategory> getCategoryLevel2(Integer parentId);
}
