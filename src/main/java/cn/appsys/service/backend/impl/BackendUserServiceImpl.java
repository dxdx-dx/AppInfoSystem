package cn.appsys.service.backend.impl;

import cn.appsys.dao.backenduser.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Matrix
 * @date 2020/5/16 2:58
 */
@Service
public class BackendUserServiceImpl implements BackendUserService {
    @Resource
    private BackendUserMapper backendUserMapper;

    @Override
    public BackendUser login(String userCode, String password) {
        BackendUser backendUser = null;
        try {
            List<BackendUser> backendUserList = backendUserMapper.findByUserCode(userCode);
            if (backendUserList == null || backendUserList.size() == 0) {
                return null;
            }
            backendUser = backendUserList.get(0);
            if (backendUser.getUserPassword().equals(password)) {
                return backendUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
