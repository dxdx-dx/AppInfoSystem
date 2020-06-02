package cn.appsys.dao.appversion;

import cn.appsys.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:32
 */
public interface AppVersionMapper {
    //删除apk文件
    int deleteApkFile(@Param("id") Integer id) throws Exception;

    //根据id查询列表
    List<AppVersion> appVersion(@Param("id") int id);

    //添加
    int add(AppVersion appVersion) throws Exception;

    //修改app信息（修改状态）

    public int modifystatus(@Param("status") Integer status, @Param("id") Integer id);

    //根据id查询信息
    AppVersion getAppVersionById(@Param("id") Integer id) throws Exception;

    //根据appid获取信息
    List<AppVersion> getAppVersionList(@Param("appId") Integer appId) throws Exception;

    //根据id查询信息
    AppVersion appVersiondetal(@Param("id") int id);

    //修改信息
    int modify(AppVersion appVersion);
}
