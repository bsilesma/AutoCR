package com.autocr.controller;

import com.autocr.domain.Usuario;
import com.autocr.service.UsuarioService;
import com.autocr.util.NegocioException;
import com.autocr.util.SessionKeys;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Inicio de sesion con Google (flujo Authorization Code manual, sin Spring
 * Security, para mantener el mismo mecanismo de sesion por HttpSession que
 * usa AuthController).
 */
@Controller
public class GoogleAuthController {

    @Value("${google.oauth.client-id:}")
    private String clientId;

    @Value("${google.oauth.client-secret:}")
    private String clientSecret;

    @Value("${google.oauth.redirect-uri:http://localhost:8080/login/google/callback}")
    private String redirectUri;

    @Autowired
    private UsuarioService usuarioService;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/login/google")
    public String iniciar(HttpSession session, @RequestParam(value = "redirect", required = false) String redirect) {
        if (clientId == null || clientId.isBlank()) {
            return "redirect:/login?googleNoConfigurado=true";
        }
        String state = generarEstado();
        session.setAttribute("googleOauthState", state);
        if (redirect != null && !redirect.isBlank()) {
            session.setAttribute("googleOauthRedirect", redirect);
        }
        String url = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + enc(clientId)
                + "&redirect_uri=" + enc(redirectUri)
                + "&response_type=code"
                + "&scope=" + enc("openid email profile")
                + "&state=" + enc(state)
                + "&prompt=select_account";
        return "redirect:" + url;
    }

    @GetMapping("/login/google/callback")
    public String callback(@RequestParam(required = false) String code,
                            @RequestParam(required = false) String state,
                            @RequestParam(required = false) String error,
                            HttpSession session,
                            Model model) {
        String estadoGuardado = (String) session.getAttribute("googleOauthState");
        session.removeAttribute("googleOauthState");

        // El state que devuelve Google debe ser el mismo que se guardo en sesion (anti-CSRF).
        if (error != null || code == null || estadoGuardado == null || !estadoGuardado.equals(state)) {
            model.addAttribute("error", "No fue posible iniciar sesion con Google.");
            return "auth/login";
        }

        try {
            JsonNode tokenJson = intercambiarCodigoPorToken(code);
            String accessToken = tokenJson.path("access_token").asText(null);
            if (accessToken == null) {
                model.addAttribute("error", "Google no devolvio un token valido.");
                return "auth/login";
            }

            JsonNode perfil = obtenerPerfil(accessToken);
            String googleId = perfil.path("sub").asText(null);
            String correo = perfil.path("email").asText(null);
            String nombre = perfil.path("name").asText(null);
            boolean correoVerificado = perfil.path("email_verified").asBoolean(false);

            if (googleId == null || correo == null || !correoVerificado) {
                model.addAttribute("error", "La cuenta de Google no tiene un correo verificado.");
                return "auth/login";
            }

            Usuario usuario = usuarioService.autenticarOCrearConGoogle(googleId, correo, nombre);
            iniciarSesion(session, usuario);

            String redirect = (String) session.getAttribute("googleOauthRedirect");
            session.removeAttribute("googleOauthRedirect");
            if (redirect != null && !redirect.isBlank()) {
                return "redirect:" + redirect;
            }
            String rol = usuario.getRol().getNombre();
            if ("ADMINISTRADOR".equals(rol) || "VENDEDOR".equals(rol) || "INVENTARIO".equals(rol)) {
                return "redirect:/admin/dashboard";
            }
            return "redirect:/";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrio un error al conectar con Google.");
            return "auth/login";
        }
    }

    private JsonNode intercambiarCodigoPorToken(String code) throws Exception {
        String body = "code=" + enc(code)
                + "&client_id=" + enc(clientId)
                + "&client_secret=" + enc(clientSecret)
                + "&redirect_uri=" + enc(redirectUri)
                + "&grant_type=authorization_code";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://oauth2.googleapis.com/token"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }

    private JsonNode obtenerPerfil(String accessToken) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.googleapis.com/oauth2/v3/userinfo"))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readTree(response.body());
    }

    private void iniciarSesion(HttpSession session, Usuario usuario) {
        session.setAttribute(SessionKeys.USUARIO_ID, usuario.getIdUsuario());
        session.setAttribute(SessionKeys.USUARIO_NOMBRE, usuario.getNombre());
        session.setAttribute(SessionKeys.USUARIO_ROL, usuario.getRol().getNombre());
    }

    private String generarEstado() {
        byte[] bytes = new byte[24];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String enc(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
