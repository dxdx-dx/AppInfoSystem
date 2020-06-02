package cn.appsys.interceptor;

import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器
 *
 * @author Matrix
 * @date 2020/4/30 9:29
 */
public class SysInterceptor2 extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        DevUser user = (DevUser) session.getAttribute(Constants.DEVUSER_SESSION);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/403.jsp");
            return false;
        } else {
            return true;
        }
    }
}
