package cn.appsys.interceptor;

import cn.appsys.pojo.BackendUser;
import cn.appsys.tools.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器-后台
 *
 * @author Matrix
 * @date 2020/4/30 9:29
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
//        DevUser user = (DevUser) session.getAttribute(Constants.USER_SESSION);
        BackendUser user = (BackendUser) session.getAttribute(Constants.USER_SESSION);

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/403.jsp");
            return false;
        } else {
            return true;
        }
    }
}
