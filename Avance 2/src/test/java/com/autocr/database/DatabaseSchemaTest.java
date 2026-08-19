package com.autocr.database;

import com.autocr.config.PasswordUtil;
import com.autocr.domain.Carrito;
import com.autocr.domain.CarritoItem;
import com.autocr.domain.Categoria;
import com.autocr.domain.Marca;
import com.autocr.domain.Pedido;
import com.autocr.domain.PedidoDetalle;
import com.autocr.domain.PedidoEstadoHistorial;
import com.autocr.domain.Producto;
import com.autocr.domain.RecuperacionContrasena;
import com.autocr.domain.Rol;
import com.autocr.domain.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DatabaseSchemaTest {

    private static final List<Class<?>> ENTIDADES = List.of(
            Rol.class, Usuario.class, Marca.class, Categoria.class, Producto.class,
            Carrito.class, CarritoItem.class, Pedido.class, PedidoDetalle.class,
            PedidoEstadoHistorial.class, RecuperacionContrasena.class
    );

    @Test
    void elScriptContieneTodasLasTablasYColumnasMapeadas() throws IOException {
        String sql = leerScript().toLowerCase();

        for (Class<?> entidad : ENTIDADES) {
            Table table = entidad.getAnnotation(Table.class);
            assertTrue(sql.contains("create table if not exists " + table.name().toLowerCase()),
                    () -> "Falta la tabla " + table.name());

            for (Field campo : entidad.getDeclaredFields()) {
                Column column = campo.getAnnotation(Column.class);
                if (column != null) {
                    assertTrue(sql.contains(column.name().toLowerCase()),
                            () -> "Falta la columna " + table.name() + "." + column.name());
                }
                JoinColumn joinColumn = campo.getAnnotation(JoinColumn.class);
                if (joinColumn != null) {
                    assertTrue(sql.contains(joinColumn.name().toLowerCase()),
                            () -> "Falta la llave foranea " + table.name() + "." + joinColumn.name());
                }
            }
        }
    }

    @Test
    void losDatosInicialesSonReejecutables() throws IOException {
        String sql = leerScript();

        assertFalse(sql.contains("INSERT IGNORE"),
                "INSERT IGNORE ocultaria errores de integridad distintos a duplicados");
        assertTrue(contarCoincidencias(sql, "ON DUPLICATE KEY UPDATE") == 5,
                "Cada bloque del seed debe controlar sus llaves duplicadas");
        assertTrue(sql.contains("CONSTRAINT uk_producto_nombre UNIQUE (nombre)"),
                "Los productos requieren una llave natural para evitar duplicados");
    }

    @Test
    void lasContrasenasInicialesCoincidenConPasswordUtil() throws IOException {
        String sql = leerScript();

        assertTrue(sql.contains(PasswordUtil.encriptar("Admin123!")));
        assertTrue(sql.contains(PasswordUtil.encriptar("Vendedor123!")));
        assertTrue(sql.contains(PasswordUtil.encriptar("Inventario123!")));
        assertTrue(sql.contains(PasswordUtil.encriptar("Cliente123!")));
    }

    private String leerScript() throws IOException {
        return Files.readString(Path.of("database", "autocr.sql"));
    }

    private int contarCoincidencias(String texto, String fragmento) {
        return (texto.length() - texto.replace(fragmento, "").length()) / fragmento.length();
    }
}
