package cn.appsys.service.developer.impl;

import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.developer.AppCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:00
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {
    @Resource
    private AppCategoryMapper appCategoryMapper;


    @Override
    public List<AppCategory> getCategoryLevel1() {
        return appCategoryMapper.getCategoryLevel1();
    }

    @Override
    public List<AppCategory> getCategoryLevel2(Integer parentId) {
        return appCategoryMapper.getCategoryLevel2(parentId);
    }
}
