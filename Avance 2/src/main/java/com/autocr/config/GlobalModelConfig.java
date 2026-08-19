package com.autocr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Atributos disponibles en todas las vistas Thymeleaf. Se usa para la
 * llave publica de Google Maps (googleMapsScript en general/fragmentos.html
 * solo la incluye si viene configurada).
 */
@ControllerAdvice
public class GlobalModelConfig {

    @Value("${google.maps.api-key:}")
    private String googleMapsApiKey;

    @ModelAttribute("googleMapsApiKey")
    public String googleMapsApiKey() {
        return googleMapsApiKey;
    }
}
