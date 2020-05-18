package cn.appsys.service.backend;

import cn.appsys.pojo.BackendUser;
import org.springframework.stereotype.Service;

/**
 * @author Matrix
 * @date 2020/5/16 2:58
 */

public interface BackendUserService {
    //登陆
    BackendUser login(String userCode, String password);

}
