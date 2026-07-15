-- =========================================================
-- AutoCR Pro - Esquema y datos iniciales (Avance 2)
-- =========================================================
-- Este archivo es autocontenido: crea la base, las tablas y los datos
-- iniciales. Puede ejecutarse nuevamente sin borrar datos existentes ni
-- duplicar el catalogo inicial.
--
-- Los productos, marcas, categorias, precios (en colones) e imagenes
-- fueron tomados del sitio real de la empresa (autocr.net), tal como
-- se indico para el rediseno.
-- =========================================================

CREATE DATABASE IF NOT EXISTS autocr
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE autocr;

-- ---------------------------------------------------------
-- Tablas (ordenadas segun sus dependencias)
-- Los nombres, tipos y longitudes corresponden a las entidades JPA.
-- ---------------------------------------------------------
CREATE TABLE IF NOT EXISTS rol (
    id_rol INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(30) NOT NULL,
    PRIMARY KEY (id_rol),
    CONSTRAINT uk_rol_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    correo VARCHAR(150) NOT NULL,
    telefono VARCHAR(30) NULL,
    contrasena VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    id_rol INT NOT NULL,
    PRIMARY KEY (id_usuario),
    CONSTRAINT uk_usuario_correo UNIQUE (correo),
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol)
        REFERENCES rol (id_rol)
        ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS marca (
    id_marca INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_marca),
    CONSTRAINT uk_marca_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(80) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_categoria),
    CONSTRAINT uk_categoria_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS producto (
    id_producto BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NULL,
    precio DECIMAL(12,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    imagen_url VARCHAR(500) NULL,
    destacado BOOLEAN NOT NULL DEFAULT FALSE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    id_marca INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_producto),
    CONSTRAINT uk_producto_nombre UNIQUE (nombre),
    CONSTRAINT chk_producto_precio CHECK (precio >= 0),
    CONSTRAINT chk_producto_stock CHECK (stock >= 0),
    CONSTRAINT fk_producto_marca FOREIGN KEY (id_marca)
        REFERENCES marca (id_marca)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    CONSTRAINT fk_producto_categoria FOREIGN KEY (id_categoria)
        REFERENCES categoria (id_categoria)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_producto_busqueda (activo, nombre),
    INDEX idx_producto_marca (id_marca),
    INDEX idx_producto_categoria (id_categoria)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS carrito (
    id_carrito BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    estado VARCHAR(20) NOT NULL DEFAULT 'CERRADO',
    PRIMARY KEY (id_carrito),
    CONSTRAINT fk_carrito_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_carrito_usuario (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS carrito_item (
    id_carrito_item BIGINT NOT NULL AUTO_INCREMENT,
    id_carrito BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id_carrito_item),
    CONSTRAINT chk_carrito_item_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_carrito_item_precio CHECK (precio_unitario >= 0),
    CONSTRAINT fk_carrito_item_carrito FOREIGN KEY (id_carrito)
        REFERENCES carrito (id_carrito)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_carrito_item_producto FOREIGN KEY (id_producto)
        REFERENCES producto (id_producto)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_carrito_item_carrito (id_carrito),
    INDEX idx_carrito_item_producto (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pedido (
    id_pedido BIGINT NOT NULL AUTO_INCREMENT,
    numero_orden VARCHAR(30) NOT NULL,
    id_usuario BIGINT NOT NULL,
    fecha DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    total DECIMAL(12,2) NOT NULL,
    metodo_entrega VARCHAR(20) NOT NULL,
    metodo_pago VARCHAR(20) NOT NULL,
    nombre_entrega VARCHAR(150) NULL,
    telefono_entrega VARCHAR(30) NULL,
    provincia VARCHAR(60) NULL,
    canton VARCHAR(60) NULL,
    distrito VARCHAR(60) NULL,
    direccion_exacta TEXT NULL,
    nota_entrega TEXT NULL,
    PRIMARY KEY (id_pedido),
    CONSTRAINT uk_pedido_numero_orden UNIQUE (numero_orden),
    CONSTRAINT chk_pedido_total CHECK (total >= 0),
    CONSTRAINT chk_pedido_estado CHECK (estado IN ('PENDIENTE', 'PROCESANDO', 'ENTREGADO', 'CANCELADO')),
    CONSTRAINT chk_pedido_entrega CHECK (metodo_entrega IN ('DOMICILIO', 'TIENDA')),
    CONSTRAINT chk_pedido_pago CHECK (metodo_pago IN ('SINPE', 'TARJETA', 'EFECTIVO')),
    CONSTRAINT fk_pedido_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_pedido_usuario_fecha (id_usuario, fecha),
    INDEX idx_pedido_estado_fecha (estado, fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pedido_detalle (
    id_pedido_detalle BIGINT NOT NULL AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id_pedido_detalle),
    CONSTRAINT chk_pedido_detalle_cantidad CHECK (cantidad > 0),
    CONSTRAINT chk_pedido_detalle_precio CHECK (precio_unitario >= 0),
    CONSTRAINT chk_pedido_detalle_subtotal CHECK (subtotal >= 0),
    CONSTRAINT fk_pedido_detalle_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido (id_pedido)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    CONSTRAINT fk_pedido_detalle_producto FOREIGN KEY (id_producto)
        REFERENCES producto (id_producto)
        ON UPDATE RESTRICT ON DELETE RESTRICT,
    INDEX idx_pedido_detalle_pedido (id_pedido),
    INDEX idx_pedido_detalle_producto (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS pedido_estado_historial (
    id_historial BIGINT NOT NULL AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,
    estado_anterior VARCHAR(20) NULL,
    estado_nuevo VARCHAR(20) NOT NULL,
    fecha DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    PRIMARY KEY (id_historial),
    CONSTRAINT chk_historial_estado_anterior CHECK (
        estado_anterior IS NULL OR estado_anterior IN ('PENDIENTE', 'PROCESANDO', 'ENTREGADO', 'CANCELADO')
    ),
    CONSTRAINT chk_historial_estado_nuevo CHECK (
        estado_nuevo IN ('PENDIENTE', 'PROCESANDO', 'ENTREGADO', 'CANCELADO')
    ),
    CONSTRAINT fk_historial_pedido FOREIGN KEY (id_pedido)
        REFERENCES pedido (id_pedido)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    INDEX idx_historial_pedido_fecha (id_pedido, fecha)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS recuperacion_contrasena (
    id_recuperacion BIGINT NOT NULL AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    token VARCHAR(10) NOT NULL,
    fecha_creacion DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    fecha_expiracion DATETIME(6) NOT NULL,
    usado BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id_recuperacion),
    CONSTRAINT fk_recuperacion_usuario FOREIGN KEY (id_usuario)
        REFERENCES usuario (id_usuario)
        ON UPDATE RESTRICT ON DELETE CASCADE,
    INDEX idx_recuperacion_token_usado (token, usado),
    INDEX idx_recuperacion_usuario (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Roles
-- ---------------------------------------------------------
INSERT INTO rol (nombre) VALUES
('CLIENTE'), ('VENDEDOR'), ('INVENTARIO'), ('ADMINISTRADOR')
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- ---------------------------------------------------------
-- Usuarios de prueba
-- Contrasenas (en texto plano, solo de referencia para pruebas):
--   admin@autocr.net      -> Admin123!
--   vendedor@autocr.net   -> Vendedor123!
--   inventario@autocr.net -> Inventario123!
--   juan@email.com        -> Cliente123!
-- Los hashes fueron generados con SHA-256 + salt fija (ver PasswordUtil.java)
-- ---------------------------------------------------------
INSERT INTO usuario (nombre, correo, telefono, contrasena, activo, fecha_registro, id_rol) VALUES
('Administrador AutoCR', 'admin@autocr.net', '88998541',
 '4037c0215407eb0e31e055d83d234b69276d230dcd5f949d129b5e3036b28b23', true, NOW(),
 (SELECT id_rol FROM rol WHERE nombre = 'ADMINISTRADOR')),
('Luis Vendedor', 'vendedor@autocr.net', '87776655',
 '31cd11460c265e3419368550929c2e7cb21b4756fb5f214a8a9b6bbb841a0326', true, NOW(),
 (SELECT id_rol FROM rol WHERE nombre = 'VENDEDOR')),
('Ana Inventario', 'inventario@autocr.net', '86665544',
 '210cb52216754aa3549f53d354b9f4551936862541c93003ae8c4677d69c9313', true, NOW(),
 (SELECT id_rol FROM rol WHERE nombre = 'INVENTARIO')),
('Juan Perez', 'juan@email.com', '88888888',
 '658c340e7b18302659381f89baea18854f9efe7c216807e60e27fc1642b45512', true, NOW(),
 (SELECT id_rol FROM rol WHERE nombre = 'CLIENTE'))
ON DUPLICATE KEY UPDATE correo = VALUES(correo);

-- ---------------------------------------------------------
-- Marcas (reales, tomadas de autocr.net/content/4-compra-por-marcas)
-- ---------------------------------------------------------
INSERT INTO marca (nombre, activo) VALUES
('CarPro', true),
('Rupes', true),
('Gyeon', true),
('Koch-Chemie', true),
('STEK', true),
('Gtechniq', true),
('P&S', true),
('Lubristone', true),
('MaxShine', true),
('Celeste', true),
('The Rag Company', true),
('BLO', true),
('Work Stuff', true),
('Scangrip', true),
('AUTOCR TOOLS', true)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- ---------------------------------------------------------
-- Categorias (reales, tomadas del menu de autocr.net)
-- ---------------------------------------------------------
INSERT INTO categoria (nombre, activo) VALUES
('Ceramicos para pintura', true),
('Abrillantadores de llantas', true),
('Detalladores de pintura', true),
('Champus', true),
('Kits', true),
('Maquinas pulidoras', true),
('Pads', true),
('Microfibras', true),
('Aromatizantes', true),
('Herramientas de trabajo', true),
('Botellas', true),
('Pulidores de metales', true),
('Pulidores de vidrios', true)
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- ---------------------------------------------------------
-- Productos (nombre, precio y foto reales de autocr.net)
-- ---------------------------------------------------------
INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, destacado, activo, id_marca, id_categoria) VALUES
('CQuartz UK 3.0 Ceramic Coating 50ml',
 'Recubrimiento ceramico CarPro de alta durabilidad (24 meses), resistente a quimicos, sal y danio ambiental con excelente proteccion UV.',
 49000.00, 12, 'https://www.autocr.net/175-home_default/cquk-30-ceramic-coating-30ml.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('CeriGlass 150ml - Pulidor de vidrios',
 'Solucion de vanguardia para correccion y pulido de vidrio con abrasivos ceramicos, oxido de cerio y nano-tecnologia.',
 7500.00, 20, 'https://www.autocr.net/170-home_default/ceriglass-150ml-pulidor-de-vidrios-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de vidrios')),

('CeriGlass 500ml - Pulidor de vidrios',
 'Version de mayor volumen del pulidor de vidrios CeriGlass de CarPro.',
 20500.00, 8, 'https://www.autocr.net/168-home_default/ceriglass-500ml-pulidor-de-vidrios-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de vidrios')),

('Metallicut 150ml - Pulidor de metales',
 'Pulimento de metales con abrasivo de diamante de tamanio decreciente, brillo extremo, apto para acero inoxidable, aluminio y cromados.',
 12000.00, 15, 'https://www.autocr.net/165-home_default/metallicut-150ml-pulidor-de-metales-carpro-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de metales')),

('Metallicut 500ml',
 'Version de mayor volumen del pulimento de metales Metallicut de CarPro.',
 19000.00, 6, 'https://www.autocr.net/162-home_default/metallicut-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de metales')),

('Essence PLUS 500ml',
 'Agente de brillo extremo no abrasivo para reparar coatings ceramicos, con base SiO2 y particulas hidrofobicas.',
 26000.00, 10, 'https://www.autocr.net/1999-home_default/essence-plus-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Wool Pad 5'' - Pad de lana para corte extremo',
 'Almohadilla de lana de cordero disenada para accion de corte extrema, con respaldo cosido resistente.',
 12000.00, 25, 'https://www.autocr.net/186-home_default/wool-pad-5-pad-de-lana-para-corte-extremo.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('CoolPad wool/MF 6'' - Pad hibrido de corte',
 'Almohadilla hibrida de lana y microfibra con estructura 3D de enfriamiento por aire.',
 7875.00, 18, 'https://www.autocr.net/184-home_default/coolpad-woolmf-5-pad-hibrido-de-corte-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Dhydrate 100x70 - Toalla de secado',
 'Toalla de secado de doble cara (pintura y vidrios), fabricada en Corea, 540 GSM.',
 15200.00, 14, 'https://www.autocr.net/182-home_default/dhydrate-100x70-toalla-de-secado-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Dhydrate 50x55 - Toalla de secado',
 'Version compacta de la toalla de secado Dhydrate de CarPro.',
 8500.00, 22, 'https://www.autocr.net/180-home_default/dhydrate-50x55-toalla-de-secado-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('GlassFiber - Toalla de vidrios',
 'Microfibra de alta densidad disenada para limpieza de vidrios sin rayas.',
 7300.00, 30, 'https://www.autocr.net/176-home_default/glassfiber-toalla-de-vidrios.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Toalla BOA 40x40cm 350 GSM',
 'Toalla ultra suave sin bordes, ideal para retirar ceras, selladores y recubrimientos.',
 3000.00, 40, 'https://www.autocr.net/171-home_default/toalla-boa-40cm-x-40cm-350-gsm-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Botella Dilute con atomizador 1 Litro',
 'Envase de dilucion de multiple relacion disenado por Detailers, incluye pulverizador resistente a quimicos.',
 3000.00, 35, 'https://www.autocr.net/3585-home_default/botella-dilute-con-atomizador-1-litro-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Botellas')),

('Perl 500ml - Abrillantador versatil',
 'Abrillantador a base de agua para llantas, plasticos y cuero, con proteccion UV e hidrofobica.',
 8250.00, 26, 'https://www.autocr.net/86-home_default/perl-500ml.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Abrillantadores de llantas')),

('Perl 1000ml - Abrillantador versatil',
 'Version de mayor volumen del abrillantador Perl de CarPro.',
 14500.00, 3, 'https://www.autocr.net/97-home_default/perl-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Abrillantadores de llantas')),

('HydrO2 Lite 1000ml - Sellador de pintura hidrofobico',
 'Sellador hidrofilo que reacciona con el agua para crear una capa de silice con brillo extremo.',
 14250.00, 16, 'https://www.autocr.net/120-home_default/hydro2-lite-1000ml-sellador-de-pintura-hidrofobico.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('HydrO2 Lite 4000ml - Sellador de pintura hidrofobico',
 'Presentacion de mayor volumen del sellador HydrO2 Lite.',
 45500.00, 5, 'https://www.autocr.net/1962-home_default/hydro2-lite-4000ml-sellador-de-pintura-hidrofobico.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('HydrO2 Lite 20 Litros - Sellador de pintura hidrofobico',
 'Presentacion industrial del sellador HydrO2 Lite para uso profesional.',
 165000.00, 2, 'https://www.autocr.net/2681-home_default/hydro2-lite-20-litros-sellador-de-pintura-hidrofobico.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('Champu Reset 4000ml',
 'Champu de alto rendimiento para el lavado seguro de la carroceria, presentacion galon.',
 52700.00, 7, 'https://www.autocr.net/826-home_default/champu-reset-4000ml.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='CarPro'), (SELECT id_categoria FROM categoria WHERE nombre='Champus')),

('Kit productos P&S',
 'Kit de productos P&S para el mantenimiento integral del vehiculo.',
 51336.00, 9, 'https://www.autocr.net/2595-home_default/kit-productos-ps.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Kits')),

('Soporte para pulidoras Rupes',
 'Soporte de exhibicion/almacenamiento para pulidoras Rupes.',
 24800.00, 11, 'https://www.autocr.net/972-home_default/soporte-para-pulidoras-rupes.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Pulidora Orbital Rupes 3 pulgadas KIT',
 'Pulidora orbital compacta de 3 pulgadas, kit completo para detallado de precision.',
 298000.00, 4, 'https://www.autocr.net/673-home_default/preventa-pulidora-orbital-rupes-3-pulgadas-kit.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('HLR75 Mini Ibrid Polisher - Pulidora inalambrica 3 pulgadas',
 'Pulidora inalambrica compacta de 3 pulgadas para trabajos de precision sin cables.',
 270000.00, 3, 'https://www.autocr.net/2599-home_default/hlr75-mini-ibrid-polisher-pulidora-inalambrica-3-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Pulidora M312 - Doble accion',
 'Pulidora de doble accion para pulido seguro en superficies delicadas.',
 120000.00, 6, 'https://www.autocr.net/56-home_default/pulidora-m312-doble-accion.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Pulidora Rotativa M1000',
 'Pulidora rotativa de alto rendimiento para correccion de pintura.',
 120000.00, 0, 'https://www.autocr.net/102-home_default/pulidora-rotativa-m1000-maxshine.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Soplador / Secador BLO GT',
 'Soplador secador profesional para eliminar agua de zonas dificiles tras el lavado.',
 198800.00, 5, 'https://www.autocr.net/1170-home_default/soplador-secador-blo-gt.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BLO'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing trolley PRO - Carrito para detallado',
 'Carrito organizador profesional para productos y herramientas de detallado.',
 133000.00, 4, 'https://www.autocr.net/1466-home_default/detailing-trolley-pro-carrito-para-detallado.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing trolley Standard - Carrito para detallado',
 'Version estandar del carrito organizador para detallado.',
 119000.00, 6, 'https://www.autocr.net/1457-home_default/detailing-trolley-standard-carrito-para-detallado-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Ducha Grafitada Negra Unidad',
 'Recubre, protege y lubrica partes metalicas expuestas a corrosion; formula a base de grafito y aceites minerales.',
 5400.00, 20, 'https://www.autocr.net/30-home_default/ducha-grafitada-negra-unidad.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('Ducha Grafitada Transparente Unidad',
 'Version transparente de la ducha grafitada Lubristone para chasis y suspension.',
 5400.00, 19, 'https://www.autocr.net/31-home_default/ducha-grafitada-transparente-unidad.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('Guante de lavado MaxShine',
 'Guante de microfibra multiuso para lavado de carroceria, llantas y pozos de rueda.',
 6500.00, 24, 'https://www.autocr.net/61-home_default/guante-de-lavado-maxshine.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('The Edgeless Pearl - Toalla para nivelar ceramico',
 'Toalla de tejido perlado sin bordes para eliminar y nivelar recubrimientos ceramicos.',
 1600.00, 45, 'https://www.autocr.net/1765-home_default/the-edgeless-pearl-toalla-para-nivelar-ceramico-the-rag-company-naranja.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Pastilla Celeste Fresh Breeze',
 'Desodorante y aromatizante de larga duracion (hasta 60 dias) para autos e interiores.',
 5500.00, 30, 'https://www.autocr.net/838-home_default/pastilla-celeste-fresh-breeze.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Celeste'), (SELECT id_categoria FROM categoria WHERE nombre='Aromatizantes')),

('Wheel Brush - Cepillo para aros',
 'Cepillo de cerdas naturales de Tampico para eliminar suciedad entre las paletas de los aros.',
 7500.00, 1, 'https://www.autocr.net/251-home_default/wheel-brush-cepillo-para-aros.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Atomizador de Alta Resistencia Quimica',
 'Atomizador gris con alta resistencia quimica y patron de rociado extra grande.',
 2350.00, 50, 'https://www.autocr.net/258-home_default/atomizador-de-alta-resistencia-quimica.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Botellas'))
ON DUPLICATE KEY UPDATE nombre = VALUES(nombre);

-- Fin del seed inicial. Las historias de media/baja prioridad (marcas y
-- categorias administrables desde el panel, direcciones, favoritos,
-- reportes, etc.) se agregaran en avances posteriores.
