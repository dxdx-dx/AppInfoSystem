package cn.appsys.dao.backenduser;

import cn.appsys.pojo.BackendUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:32
 */
public interface BackendUserMapper {
    //根据UserCode查询用户
    List<BackendUser> findByUserCode(@Param("userCode") String userCode);

}
