package cn.appsys.controller.developer;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 开发者用户的控制类
 *
 * @author Matrix
 * @date 2020/5/16 2:52
 */
@Controller
@RequestMapping("/dev")
public class DevLoginController {

    @Resource
    private DevUserService devUserService;

    /**
     * 跳转到登陆页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:47
     */
    @RequestMapping("/login")
    public String login() {
        return "devlogin";
    }

    /**
     * 跳转到主页面
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:46
     */
    @RequestMapping("/main")
    public String main() {
        return "developer/main";
    }

    /**
     * 登陆
     *
     * @return java.lang.String
     * @author Matrix
     * @date 2020/5/18 18:46
     */
    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public String dologin(@RequestParam String devCode, @RequestParam String devPassword,
                          HttpSession sesion, HttpServletRequest request) {
        DevUser devUser = devUserService.devLogin(devCode, devPassword);
        if (devUser != null) {
            if (sesion.getAttribute(Constants.DEVUSER_SESSION) != null) {
                sesion.removeAttribute(Constants.DEVUSER_SESSION);
            }
            sesion.setAttribute(Constants.DEVUSER_SESSION, devUser);
            System.out.println("ok");
            return "redirect:/dev/main";
        } else {
            System.out.println("用户名或者密码不正确");
            request.setAttribute("error", "用户名或者密码不正确！！！");
            return "devlogin";
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
        return "devlogin";
    }
}
