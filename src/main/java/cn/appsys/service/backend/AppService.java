package cn.appsys.service.backend;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:57
 */
public interface AppService {

    //根据id获取app详情
    AppInfo appinfodetal(int id);

    //根据id获取版本详情
    AppVersion appVersiondetal(int id);

    //上下架
    int updateSatus(int status, int id);

    //查询所有平台名称
    List<DataDictionary> dataList();

    //查询一级分类
    List<AppCategory> getCategoryLevel1();

    //查询二,三级分类
    List<AppCategory> getCategoryLevel2(Integer parentId);

    //查询信息总条数
    Integer getAppinfoCount(String softwareName, Integer flatformId,
                            Integer categoryLevel1,
                            Integer categoryLevel2, Integer categoryLevel3, Integer status);

    //查询app信息列表
    List<AppInfo> getAppInfoList(String softwareName, Integer flatformId,
                                 Integer categoryLevel1,
                                 Integer categoryLevel2, Integer categoryLevel3, Integer status, Integer pageNo, Integer pageSize);
}
