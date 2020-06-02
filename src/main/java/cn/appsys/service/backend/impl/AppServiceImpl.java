package cn.appsys.service.backend.impl;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.dao.appcategory.AppCategoryMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.dao.datadictionary.DataDictionaryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:57
 */
@Service
public class AppServiceImpl implements AppService {

    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;
    @Resource
    private DataDictionaryMapper dataDictionaryMapper;

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

    @Override
    public Integer getAppinfoCount(String softwareName, Integer flatformId,
                                   Integer categoryLevel1,
                                   Integer categoryLevel2, Integer categoryLevel3, Integer status) {
        return appInfoMapper.getAppinfoCount(softwareName, flatformId,
                categoryLevel1, categoryLevel2, categoryLevel3, status);
    }

    @Override
    public AppInfo appinfodetal(int id) {
        return appInfoMapper.appinfodetal(id);
    }

    @Override
    public AppVersion appVersiondetal(int id) {
        return appVersionMapper.appVersiondetal(id);
    }

    @Override
    public int updateSatus(int status, int id) {
        return appInfoMapper.updateSatus(status, id);
    }

    @Override
    public List<DataDictionary> dataList() {
        return dataDictionaryMapper.dataList();
    }


    @Override
    public List<AppInfo> getAppInfoList(String softwareName, Integer flatformId,
                                        Integer categoryLevel1,
                                        Integer categoryLevel2, Integer categoryLevel3
            , Integer status, Integer pageNo, Integer pageSize) {
        List<AppInfo> appInfoList = null;
        appInfoList = appInfoMapper.getAppInfoList(softwareName, flatformId,
                categoryLevel1, categoryLevel2, categoryLevel3, status, (pageNo - 1) * pageSize, pageSize);
       /* softwareName, status, categoryLevel1, categoryLevel2,
                categoryLevel3, flatformId, devId, (pageNo - 1) * Constants.PAGE_SIZE, pageSize)*/
        return appInfoList;
    }
}
