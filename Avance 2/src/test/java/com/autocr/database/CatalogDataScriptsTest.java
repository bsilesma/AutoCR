package com.autocr.database;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CatalogDataScriptsTest {

    @Test
    void catalogoContieneLaAmpliacionEsperada() throws IOException {
        String sql = leer("catalogo-avance3.sql");
        int filas = contar(sql, Pattern.compile("(?m)^\\('").matcher(sql));

        assertEquals(162, filas, "Deben existir 3 marcas, 8 categorias y 151 productos");
        assertTrue(sql.contains("ON DUPLICATE KEY UPDATE"), "La migracion debe ser reejecutable");
        assertTrue(sql.contains("('3D Car Care', true)"));
        assertTrue(sql.contains("('BIGBOI', true)"));
        assertTrue(sql.contains("('Meguiars', true)"));
    }

    @Test
    void seedContieneQuinientosPedidosYProteccionContraDuplicados() throws IOException {
        String sql = leer("seed-pedidos-historicos.sql");

        assertEquals(500, contar(sql, Pattern.compile("(?m)^\\('ACR-").matcher(sql)));
        assertTrue(sql.contains("avance3-pedidos-historicos-v1"));
        assertTrue(sql.contains("START TRANSACTION"));
        assertTrue(sql.contains("ROLLBACK"));
        assertTrue(sql.contains("COMMIT"));
    }

    private String leer(String archivo) throws IOException {
        return Files.readString(Path.of("database", archivo));
    }

    private int contar(String texto, Matcher matcher) {
        int total = 0;
        while (matcher.find()) {
            total++;
        }
        return total;
    }
}
