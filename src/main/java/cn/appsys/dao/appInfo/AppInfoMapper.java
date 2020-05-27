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
                            @Param("categoryLevel3") Integer categoryLevel3);

    //查询app信息列表
    List<AppInfo> getAppInfoList(@Param("softwareName") String softwareName,
                                 @Param("flatformId") Integer flatformId,
                                 @Param("categoryLevel1") Integer categoryLevel1,
                                 @Param("categoryLevel2") Integer categoryLevel2,
                                 @Param("categoryLevel3") Integer categoryLevel3);

/*
    @Param("softwareName") String softwareName,
    @Param("status") Integer status,
    @Param("categoryLevel1") Integer categoryLevel1,
    @Param("categoryLevel2") Integer categoryLevel2,
    @Param("categoryLevel3") Integer categoryLevel3,

    @Param("devId") Integer devId,
    @Param("pageNo") Integer pageNo,
    @Param("softwareName") Integer pageSize*/


}
