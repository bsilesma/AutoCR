package com.autocr.config;

import com.autocr.controller.AuthController;
import com.autocr.service.RecuperacionService;
import com.autocr.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(ProjectConfig.class)
class InternationalizationWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RecuperacionService recuperacionService;

    @Test
    void cambiaAInglesYPersisteElIdiomaEnLaSesion() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(get("/login").param("lang", "en").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome back")))
                .andExpect(content().string(containsString("Continue with Google")));

        mockMvc.perform(get("/login").session(session))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Welcome back")));
    }

    @Test
    void espanolEsElIdiomaPredeterminado() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Bienvenido de vuelta")))
                .andExpect(content().string(containsString("Continuar con Google")));
    }
}
