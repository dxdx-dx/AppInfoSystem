package cn.appsys.controller.backend;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 后台用户控制类
 *
 * @author Matrix
 * @date 2020/5/16 2:46
 */
@Controller
@RequestMapping("/backend")
public class UserLoginController {
    @Resource
    private BackendUserService backendUserService;

    /**
     * 跳转到登陆页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:48
     */
    @RequestMapping("/login")
    public String login() {
        return "backendlogin";
    }

    /**
     * 跳转到主页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:49
     */
    @RequestMapping("/main")
    public String main() {
        return "backend/main";
    }

    /**
     * 登陆
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:49
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String dologin(@RequestParam String userCode, @RequestParam String userPassword,
                          HttpSession sesion, HttpServletRequest request) {
        BackendUser backendUser = backendUserService.login(userCode, userPassword);
        if (backendUser != null) {
            if (sesion.getAttribute(Constants.USER_SESSION) != null) {
                sesion.removeAttribute(Constants.USER_SESSION);
            }
            sesion.setAttribute(Constants.USER_SESSION, backendUser);
            return "redirect:/backend/main";
        } else {
            request.setAttribute("error", "用户名或者密码不正确！！！");
            return "backendlogin";
        }
    }

    /**
     * 注销
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/19 8:59
     */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession.getAttribute(Constants.USER_SESSION) != null) {
            httpSession.removeAttribute(Constants.USER_SESSION);
        }
        return "backendlogin";
    }

}
