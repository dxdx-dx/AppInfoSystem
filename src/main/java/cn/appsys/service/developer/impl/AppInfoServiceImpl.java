package cn.appsys.service.developer.impl;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.developer.AppInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:01
 */
@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Resource
    private AppInfoMapper appInfoMapper;

    @Resource
    private AppVersionMapper appVersionMapper;

    /**
     * 删除logo图片
     */
    @Override
    public boolean deleteAppLogo(Integer id) throws Exception {
        boolean flag = false;
        if (appInfoMapper.deleteAppLogo(id) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据id查询app基础信息
     */
    @Override
    public AppInfo appinfo(int id) {
        return appInfoMapper.appinfo(id);
    }

    /**
     * 根据id查询app基础信息
     */
    public AppInfo getAppinfoById(Integer id) {
        return appInfoMapper.getAppinfoById(id);
    }

    /**
     * 新增app
     */
    @Override
    public boolean add(AppInfo appInfo) throws Exception {
        boolean flag = false;
        if (appInfoMapper.add(appInfo) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 根据id、apkName查找appInfo
     */
    @Override
    public AppInfo getAppInfo(Integer id, String APKName) throws Exception {
        return appInfoMapper.getAppInfo(id, APKName);
    }


    /**
     * 修改app基础信息
     */
    public int appinfomodify(AppInfo info) {
        // TODO Auto-generated method stub
        return appInfoMapper.appinfomodify(info);
    }

    /**
     * 修改状态（上下架）
     */
    @Override
    public boolean updateSatus(AppInfo appInfoObj) throws Exception {
        Integer operator = appInfoObj.getModifyBy();
        AppInfo appInfo = appInfoMapper.getAppInfo(appInfoObj.getId(), null);
        if (null == appInfo) {
            return false;
        } else {
            switch (appInfo.getStatus()) {
                case 2:
                    onSale(appInfo, operator, 4, 2);
                    break;
                case 5:
                    onSale(appInfo, operator, 4, 2);
                    break;
                case 4:
                    offSale(appInfo, operator, 5);
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /**
     * 刪除
     */
    @Override
    public boolean appdelete(int id) {
        //判断数据库执行后返回的结果是否大于0
        int result = appInfoMapper.appdelete(id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 上架
     */
    private void onSale(AppInfo appInfo, Integer operator,
                        Integer appInfStatus, Integer versionStatus)
            throws Exception {
        offSale(appInfo, operator, appInfStatus);
        setSaleSwitchToAppVersion(appInfo, operator, versionStatus);
    }

    /**
     * 下架
     */
    private boolean offSale(AppInfo appInfo, Integer operator,
                            Integer appInfStatus) throws Exception {
        AppInfo _appInfo = new AppInfo();
        _appInfo.setId(appInfo.getId());
        _appInfo.setStatus(appInfStatus);
        _appInfo.setModifyBy(operator);
        _appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
        appInfoMapper.modify(_appInfo);
        return true;
    }

    /**
     * 设置上下架
     */
    private boolean setSaleSwitchToAppVersion(AppInfo appInfo,
                                              Integer operator, Integer saleStatus)
            throws Exception {
        AppVersion appVersion = new AppVersion();
        appVersion.setId(appInfo.getVersionId());
        appVersion.setPublishStatus(saleStatus);
        appVersion.setModifyBy(operator);
        appVersion.setModifyDate(new Date(System.currentTimeMillis()));
        appVersionMapper.modify(appVersion);
        return false;
    }

    /**
     * 查询信息总条数
     */
    @Override
    public Integer getAppinfoCount(String softwareName, Integer flatformId,
                                   Integer categoryLevel1, Integer categoryLevel2,
                                   Integer categoryLevel3, Integer status) {
        return appInfoMapper.getAppinfoCount(softwareName, flatformId,
                categoryLevel1,
                categoryLevel2, categoryLevel3, status);
    }

    /**
     * 查询app信息列表
     */
    @Override
    public List<AppInfo> getAppInfoList(String softwareName, Integer flatformId,
                                        Integer categoryLevel1,
                                        Integer categoryLevel2,
                                        Integer categoryLevel3, Integer status,
                                        Integer pageNo, Integer pageSize) {
        return appInfoMapper.getAppInfoList(softwareName, flatformId,
                categoryLevel1, categoryLevel2, categoryLevel3,
                status, (pageNo - 1) * pageSize, pageSize);
    }
}
