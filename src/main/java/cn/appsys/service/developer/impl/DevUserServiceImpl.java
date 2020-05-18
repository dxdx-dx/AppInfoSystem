package cn.appsys.service.developer.impl;

import cn.appsys.dao.devuser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 3:03
 */
@Service
public class DevUserServiceImpl implements DevUserService {

    @Resource
    private DevUserMapper devUserMapper;

    @Override
    public DevUser devLogin(String devCode, String devPassword) {
        DevUser devUser = null;
        try {
            List<DevUser> devUserList = devUserMapper.findBydevCode(devCode);
            if (devUserList == null || devUserList.size() == 0) {
                return null;
            }
            devUser = devUserList.get(0);
            if (devUser.getDevPassword().equals(devPassword)) {
                return devUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
