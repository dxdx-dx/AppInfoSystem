package cn.appsys.dao.devuser;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:31
 */
public interface DevUserMapper {
    //根据UserCode查询用户
    List<DevUser> findBydevCode(@Param("devCode") String devCode);

}
