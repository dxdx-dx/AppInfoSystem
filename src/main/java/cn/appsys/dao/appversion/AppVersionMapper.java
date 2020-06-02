package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:32
 */
public interface AppVersionMapper {

    List<AppVersion> appVersion(@Param("id") int id);

    public int add(AppVersion appVersion) throws Exception;

    /**
     * 修改app信息（修改状态）
     */
    public int modifystatus(@Param("status") Integer status, @Param("id") Integer id);


    AppVersion getAppVersionById(@Param("id") Integer id) throws Exception;

    List<AppVersion> getAppVersionList(@Param("appId") Integer appId) throws Exception;

    AppVersion appVersiondetal(@Param("id") int id);

    int modify(AppVersion appVersion);
}
