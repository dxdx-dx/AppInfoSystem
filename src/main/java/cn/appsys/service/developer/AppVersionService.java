package cn.appsys.service.developer;

import cn.appsys.pojo.AppVersion;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:01
 */
public interface AppVersionService {


    /**
     * 新增app版本信息，并更新app_info表的versionId字段
     *
     * @param appVersion
     * @return
     * @throws Exception
     */
    public boolean appsysadd(AppVersion appVersion) throws Exception;

    /**
     * 修改app信息（修改状态）
     */
    int modifystatus(Integer status, Integer id);

    List<AppVersion> appVersion(int id);

    /**
     * 修改app版本信息
     *
     * @param appVersion
     * @return
     * @throws Exception
     */
    boolean modify(AppVersion appVersion) throws Exception;

    /**
     * 根据appId查询相应的app版本列表
     *
     * @param appId
     * @return
     * @throws Exception
     */
    List<AppVersion> getAppVersionList(Integer appId) throws Exception;

    /**
     * 根据id获取AppVersion
     *
     * @param id
     * @return
     * @throws Exception
     */
    AppVersion getAppVersionById(Integer id) throws Exception;
}
