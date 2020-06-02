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
    List<AppInfo> getAppInfoList(@Param("softwareName") String softwareName,
                                 @Param("flatformId") Integer flatformId,
                                 @Param("categoryLevel1") Integer categoryLevel1,
                                 @Param("categoryLevel2") Integer categoryLevel2,
                                 @Param("categoryLevel3") Integer categoryLevel3,
                                 @Param("status") Integer status,
                                 @Param("pageNo") Integer pageNo,
                                 @Param("pageSize") Integer pageSize);

    AppInfo getAppInfo(@Param(value = "id") Integer id, @Param(value = "APKName") String APKName);

    AppInfo appinfo(@Param("id") int id);

    int appdelete(@Param("id") int id);

    public int modify(AppInfo appInfo);

    boolean updateSatus001(AppInfo appInfo);

    AppInfo appinfodetal(@Param("id") int id);

    int updateSatus(@Param("status") int status, @Param("id") int id);

    /**
     * 根据id查询app基础信息
     */
    public AppInfo getAppinfoById(@Param("id") Integer id);

    /**
     * 修改APP基础信息
     */
    public int appinfomodify(AppInfo info);

    /**
     * 根据appId，更新最新versionId
     *
     * @param versionId
     * @param appId
     * @return
     * @throws Exception
     */
    public int updateVersionId(@Param(value = "versionId") Integer versionId, @Param(value = "id") Integer appId) throws Exception;

    public int add(AppInfo appInfo) throws Exception;
}

