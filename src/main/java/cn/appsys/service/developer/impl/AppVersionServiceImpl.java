package cn.appsys.service.developer.impl;

import cn.appsys.dao.appInfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.developer.AppVersionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:02
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    private AppVersionMapper appVersionMapper;
    @Resource
    private AppInfoMapper appInfoMapper;


    @Override
    public boolean appsysadd(AppVersion appVersion) throws Exception {
        // TODO Auto-generated method stub
        boolean flag = false;
        Integer versionId = null;
        if (appVersionMapper.add(appVersion) > 0) {
            versionId = appVersion.getId();
            flag = true;
        }
        if (appInfoMapper.updateVersionId(versionId, appVersion.getAppId()) > 0 && flag) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean modify(AppVersion appVersion) throws Exception {
        boolean flag = false;
        if (appVersionMapper.modify(appVersion) > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public List<AppVersion> getAppVersionList(Integer appId) throws Exception {
        return appVersionMapper.getAppVersionList(appId);
    }

    @Override
    public AppVersion getAppVersionById(Integer id) throws Exception {
        return appVersionMapper.getAppVersionById(id);
    }

    /**
     * 修改app信息（修改状态）
     */
    public int modifystatus(Integer status, Integer id) {
        // TODO Auto-generated method stub
        return appVersionMapper.modifystatus(status, id);
    }

    @Override
    public List<AppVersion> appVersion(int id) {
        return appVersionMapper.appVersion(id);
    }
}
