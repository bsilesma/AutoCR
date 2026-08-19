package com.autocr.controller.admin;

import com.autocr.config.ProjectConfig;
import com.autocr.service.CategoriaService;
import com.autocr.service.MarcaService;
import com.autocr.service.PedidoService;
import com.autocr.service.ProductoService;
import com.autocr.service.ReporteService;
import com.autocr.service.RolService;
import com.autocr.service.UsuarioService;
import com.autocr.util.SessionKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
        AdminProductoController.class,
        AdminMarcaCategoriaController.class,
        AdminInventarioController.class,
        AdminReporteController.class,
        AdminUsuarioController.class,
        AdminClienteController.class
})
@Import(ProjectConfig.class)
class AdminPanelWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private ProductoService productoService;
    @MockBean private MarcaService marcaService;
    @MockBean private CategoriaService categoriaService;
    @MockBean private ReporteService reporteService;
    @MockBean private UsuarioService usuarioService;
    @MockBean private RolService rolService;
    @MockBean private PedidoService pedidoService;

    @BeforeEach
    void datosBase() {
        when(productoService.listarTodosAdmin()).thenReturn(new ArrayList<>());
        when(marcaService.listarTodas()).thenReturn(List.of());
        when(categoriaService.listarTodas()).thenReturn(List.of());
        when(reporteService.ventasPorDia(30)).thenReturn(List.of());
        when(reporteService.ventasPorMes(12)).thenReturn(List.of());
        when(reporteService.topProductos(8)).thenReturn(List.of());
        when(reporteService.ventasPorEstado()).thenReturn(List.of());
        when(reporteService.ingresosTotales()).thenReturn(BigDecimal.ZERO);
        when(usuarioService.listarTodos()).thenReturn(List.of());
        when(rolService.listarTodos()).thenReturn(List.of());
    }

    @Test
    void todasLasSeccionesNuevasRenderizanParaAdministrador() throws Exception {
        String[] rutas = {
                "/admin/productos",
                "/admin/marcas-categorias",
                "/admin/inventario",
                "/admin/reportes",
                "/admin/usuarios",
                "/admin/clientes"
        };

        for (String ruta : rutas) {
            mockMvc.perform(get(ruta)
                            .sessionAttr(SessionKeys.USUARIO_ROL, "ADMINISTRADOR")
                            .sessionAttr(SessionKeys.USUARIO_ID, 1L)
                            .sessionAttr(SessionKeys.USUARIO_NOMBRE, "Admin"))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("PANEL ADMINISTRATIVO")));
        }
    }

    @Test
    void vendedorPuedeVerReportesPeroNoVeElEnlaceDeUsuarios() throws Exception {
        mockMvc.perform(get("/admin/reportes")
                        .sessionAttr(SessionKeys.USUARIO_ROL, "VENDEDOR")
                        .sessionAttr(SessionKeys.USUARIO_NOMBRE, "Vendedor"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Reportes y metricas")))
                .andExpect(content().string(not(containsString("Usuarios y Roles"))));
    }

    @Test
    void vendedorNoPuedeAdministrarUsuarios() throws Exception {
        mockMvc.perform(get("/admin/usuarios")
                        .sessionAttr(SessionKeys.USUARIO_ROL, "VENDEDOR"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/dashboard"));
    }

    @Test
    void visitanteNoPuedeEntrarAlPanel() throws Exception {
        mockMvc.perform(get("/admin/reportes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?redirect=/admin/reportes"));
    }
}
