package com.autocr.config;

import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Restringe el panel administrativo a los roles internos autorizados.
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Object rol = session == null ? null : session.getAttribute(SessionKeys.USUARIO_ROL);

        boolean autorizado = "ADMINISTRADOR".equals(rol)
                || "VENDEDOR".equals(rol)
                || "INVENTARIO".equals(rol);

        if (!autorizado) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=" + request.getRequestURI());
            return false;
        }
        return true;
    }
}
