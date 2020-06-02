package cn.appsys.service.developer;

import cn.appsys.pojo.AppInfo;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:01
 */
public interface AppInfoService {
    //删除logo图片
    boolean deleteAppLogo(Integer id) throws Exception;

    //新增app
    boolean add(AppInfo appInfo) throws Exception;

    // 根据id、apkName查找appInfo
    AppInfo getAppInfo(Integer id, String APKName) throws Exception;

    //根据id查询app基础信息

    AppInfo getAppinfoById(Integer id);

    //修改app基础信息

    int appinfomodify(AppInfo info);

    //修改状态（上下架）
    boolean updateSatus(AppInfo appInfo) throws Exception;

    //根据id获取信息
    AppInfo appinfo(int id);

    //删除信息
    boolean appdelete(int id);

    //查询信息总条数
    Integer getAppinfoCount(String softwareName, Integer flatformId,
                            Integer categoryLevel1,
                            Integer categoryLevel2, Integer categoryLevel3, Integer status);

    //查询app信息列表
    List<AppInfo> getAppInfoList(String softwareName, Integer flatformId,
                                 Integer categoryLevel1,
                                 Integer categoryLevel2, Integer categoryLevel3, Integer status, Integer pageNo, Integer pageSize);
}
