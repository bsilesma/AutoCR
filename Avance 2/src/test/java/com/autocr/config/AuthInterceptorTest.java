package com.autocr.config;

import com.autocr.util.SessionKeys;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthInterceptorTest {

    private final AuthInterceptor interceptor = new AuthInterceptor();

    @Test
    void redirigeAlLoginCuandoNoHaySesion() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/dashboard");
        MockHttpServletResponse response = new MockHttpServletResponse();

        assertFalse(interceptor.preHandle(request, response, new Object()));
        assertEquals("/login?redirect=/admin/dashboard", response.getRedirectedUrl());
    }

    @Test
    void permiteLosTresRolesAdministrativos() throws Exception {
        for (String rol : new String[]{"ADMINISTRADOR", "VENDEDOR", "INVENTARIO"}) {
            MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/dashboard");
            request.getSession().setAttribute(SessionKeys.USUARIO_ROL, rol);

            assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
        }
    }

    @Test
    void bloqueaAlCliente() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/pedidos");
        request.getSession().setAttribute(SessionKeys.USUARIO_ROL, "CLIENTE");

        assertFalse(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
    }
}
