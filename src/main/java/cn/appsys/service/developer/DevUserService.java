package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;

/**
 * @author Matrix
 * @date 2020/5/16 3:03
 */
public interface DevUserService {
    //登陆
    DevUser devLogin(String devCode, String devPassword);
}
