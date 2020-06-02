package cn.appsys.dao.appInfo;

import cn.appsys.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:30
 */
public interface AppInfoMapper {
    //查询信息总条数
    Integer getAppinfoCount(@Param("softwareName") String softwareName,
                            @Param("flatformId") Integer flatformId,
                            @Param("categoryLevel1") Integer categoryLevel1,
                            @Param("categoryLevel2") Integer categoryLevel2,
                            @Param("categoryLevel3") Integer categoryLevel3,
                            @Param("status") Integer status);


    //查询app信息列表
    List<AppInfo> getAppInfoList(
            @Param("softwareName") String softwareName,
            @Param("flatformId") Integer flatformId,
            @Param("categoryLevel1") Integer categoryLevel1,
            @Param("categoryLevel2") Integer categoryLevel2,
            @Param("categoryLevel3") Integer categoryLevel3,
            @Param("status") Integer status,
            @Param("pageNo") Integer pageNo,
            @Param("pageSize") Integer pageSize);

    //根据id或者apk名称查询信息
    AppInfo getAppInfo(@Param(value = "id") Integer id, @Param(value = "APKName") String APKName);

    //根据id询信息
    AppInfo appinfo(@Param("id") int id);

    //根据id删除信息
    int appdelete(@Param("id") int id);

    //修改信息
    public int modify(AppInfo appInfo);

    //修改状态（上下架）
    boolean updateSatus001(AppInfo appInfo);

    //查询app信息
    AppInfo appinfodetal(@Param("id") int id);

    //修改状态
    int updateSatus(@Param("status") int status, @Param("id") int id);

    //根据id查询app基础信息

    AppInfo getAppinfoById(@Param("id") Integer id);

    // 修改APP基础信息

    int appinfomodify(AppInfo info);

    // 根据appId，更新最新versionId

    int updateVersionId(@Param(value = "versionId") Integer versionId, @Param(value = "id") Integer appId) throws Exception;

    //添加信息
    int add(AppInfo appInfo) throws Exception;
}

