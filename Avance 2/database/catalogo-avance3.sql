-- Productos reales adicionales tomados de autocr.net (marcas, categorias
-- y productos), mas ~500 pedidos historicos para poblar metricas y
-- reportes. Ver seedPedidosHistoricos.sql para los pedidos.
-- =========================================================
-- ---------------------------------------------------------
-- AutoCR Pro - Migracion reejecutable del catalogo (Avance 3).
-- Incluye 3 marcas, 8 categorias y 151 productos reales de autocr.net.
-- Ejecutar antes de seed-pedidos-historicos.sql.
USE autocr;

-- Ampliacion de catalogo (Avance 3) - productos reales adicionales
-- tomados de autocr.net, generados automaticamente.
-- ---------------------------------------------------------
INSERT INTO marca (nombre, activo) VALUES
('3D Car Care', true),
('BIGBOI', true),
('Meguiars', true)
ON DUPLICATE KEY UPDATE activo = VALUES(activo);

INSERT INTO categoria (nombre, activo) VALUES
('Proteccion de pintura (PPF y Wrap)', true),
('Iluminacion y equipo', true),
('Equipo de lavado', true),
('Limpieza de interiores', true),
('Ceras y selladores', true),
('Restauradores de plasticos', true),
('Limpiadores de aros y llantas', true),
('Pulidores de pintura', true)
ON DUPLICATE KEY UPDATE activo = VALUES(activo);

INSERT INTO producto (nombre, descripcion, precio, stock, imagen_url, destacado, activo, id_marca, id_categoria) VALUES
('Pulidora Orbital Rupes 15 Mark III',
 'Disenada para manejar cualquier situacion de detalle, la LHR 15 MarkIII permite correccion precisa de la pintura con total comodidad, incluidas superficies curvas y areas de dificil acceso.',
 280000.00, 13, 'https://www.autocr.net/645-home_default/pulidora-orbital-rupes-15-mark-iii.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('DA COARSE - Pulidor de corte 1000ml',
 'RUPES DA COARSE es el compuesto de alto rendimiento disenado para maxima eficiencia, ideal para eliminacion rapida de defectos con experiencia de usuario suave.',
 31500.00, 20, 'https://www.autocr.net/4141-home_default/da-coarse-pulidor-de-corte-rupes-1000ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('DA FINE - Pulidor de corte medio 1000ml',
 'RUPES DA FINE combina un rendimiento de corte notable con un acabado impresionante, disenado para funcionar con pulidores de doble accion.',
 28500.00, 13, 'https://www.autocr.net/2889-home_default/da-fine-pulidor-de-corte-medio-1000ml-rupes-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Pad de lana Rupes corte medio 5 pulgadas',
 'Pad de lana con longitud constante de 15mm de fibra, recomendado para trabajos de acabado, suave y flexible con soporte de espuma amarilla.',
 12000.00, 39, 'https://www.autocr.net/656-home_default/pad-de-lana-rupes-corte-medio-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Pad de lana Rupes corte medio 6 pulgadas',
 'Pad de lana con longitud constante de 15mm de fibra, recomendado para trabajos de acabado, suave y flexible con soporte de espuma amarilla.',
 12500.00, 3, 'https://www.autocr.net/658-home_default/pad-de-lana-rupes-corte-medio-6-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Pulidora Orbital Rupes 3 pulgadas',
 'La LHR 75E es ideal para lugares dificiles y operaciones de correccion puntual, imprescindible para obtener resultados perfectos incluso en areas dificiles.',
 221000.00, 25, 'https://www.autocr.net/666-home_default/pulidora-orbital-rupes-3-pulgadas-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Pulidora iBrid Nano Cuello Largo Kit',
 'La ultima maquina de RUPES es mas que tecnologia, una filosofia de diseno que inaugura una era de innovacion, flexibilidad, versatilidad y sostenibilidad.',
 558000.00, 23, 'https://www.autocr.net/674-home_default/preventa-pulidora-ibrid-nano-cuello-largo-kit.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Pad Rupes corte 6 espuma azul',
 'Almohadilla de espuma DA COARSE especialmente disenada para tareas de corte, con material de espuma de celda abierta que elimina defectos leves a severos.',
 10500.00, 17, 'https://www.autocr.net/703-home_default/pad-rupes-corte-6-espuma-azul.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Pad Rupes pulido 3 espuma amarillo',
 'Almohadilla de espuma DA FINE, la mas versatil en la gama RUPES BigFoot, brinda eliminacion de defectos y acabado de alto brillo en la mayoria de sistemas de pintura.',
 5000.00, 3, 'https://www.autocr.net/708-home_default/pad-rupes-corte-3-espuma-amarillo.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Pad Rupes pulido 5 espuma amarillo',
 'Almohadilla de espuma DA FINE, la mas versatil en la gama RUPES BigFoot, brinda eliminacion de defectos y acabado de alto brillo en la mayoria de sistemas de pintura.',
 8250.00, 29, 'https://www.autocr.net/712-home_default/pad-rupes-pulido-5-espuma-amarillo.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Pad Rupes pulido 6 espuma amarillo',
 'Almohadilla de espuma DA FINE, la mas versatil en la gama RUPES BigFoot, brinda eliminacion de defectos y acabado de alto brillo en la mayoria de sistemas de pintura.',
 8750.00, 34, 'https://www.autocr.net/718-home_default/pad-rupes-pulido-6-espuma-amarillo.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Backing Plate Rupes orbital 5 pulgadas',
 'Plato de pulido para pulidora orbital RUPES BigFoot, disponible para LHR15 (125mm).',
 25500.00, 33, 'https://www.autocr.net/741-home_default/backing-plate-rupes-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Rupes'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Crystal Serum Light 30ml - Ceramico para pintura',
 'Version consumidor del mundialmente famoso Gtechniq Crystal Serum Ultra, ofrece el 80% del desempeno profesional y puede pulirse a maquina si se aplica mal.',
 57000.00, 20, 'https://www.autocr.net/2980-home_default/crystal-serum-light-30ml-ceramico-para-pintura.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Crystal Serum Light 50ml - Ceramico para pintura',
 'Version consumidor del mundialmente famoso Gtechniq Crystal Serum Ultra, ofrece el 80% del desempeno profesional y puede pulirse a maquina si se aplica mal.',
 81500.00, 28, 'https://www.autocr.net/2979-home_default/crystal-serum-light-50ml-ceramico-para-pintura.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('C2 Ceramic Sellant 500ml - Sellador ceramico',
 'El cristal liquido C2 hace que su automovil brille y se mantenga limpio por mas tiempo, gracias a Smart Surface Science de Gtechniq, con proteccion instantanea a la pintura.',
 24000.00, 25, 'https://www.autocr.net/2972-home_default/c2-ceramic-selant-500ml-sellador-ceramico.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('C2 Ceramic Sellant 1000ml',
 'El cristal liquido C2 hace que su automovil brille y se mantenga limpio por mas tiempo, gracias a Smart Surface Science de Gtechniq, con proteccion instantanea a la pintura.',
 38500.00, 25, 'https://www.autocr.net/3102-home_default/c2-liquid-crystal-1000ml.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('C1 Crystal Lacquer+ 30ml',
 'Recubrimiento de pintura extremadamente facil de aplicar, ideal para quien es nuevo en cerámicos o busca un revestimiento duradero pero simple de aplicar.',
 37000.00, 21, 'https://www.autocr.net/235-home_default/crystal-lacquer-30ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('C1 Crystal Lacquer+ 50ml',
 'Recubrimiento de pintura extremadamente facil de aplicar, ideal para quien es nuevo en cerámicos o busca un revestimiento duradero pero simple de aplicar.',
 49500.00, 3, 'https://www.autocr.net/236-home_default/crystal-lacquer-30ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('C4 Permanent Trim Restorer 15ml',
 'C4 usa un enlace quimico para convertirse en extension de la molecula de plastico que protege, con capa protectora de durabilidad extendida.',
 19500.00, 22, 'https://www.autocr.net/238-home_default/c4-permanent-trim-restorer-15ml-recubrimiento-ceramico-para-plasticos-gtechniq.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('C4 Permanent Trim Restorer 30ml',
 'C4 usa un enlace quimico para convertirse en extension de la molecula de plastico que protege, con capa protectora de durabilidad extendida.',
 35000.00, 28, 'https://www.autocr.net/241-home_default/c4-permanent-trim-restorer-30ml-recubrimiento-ceramico-para-plasticos-gtechniq.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('C5 Wheel Armour 30ml - Ceramico para aros',
 'Basado en tecnologia similar a C1 y C4, C5 Wheel Armour proporciona la mejor proteccion para aros, estandar o de aleacion, con rendimiento excepcional.',
 35750.00, 39, 'https://www.autocr.net/243-home_default/c5-wheel-armour-30ml-ceramico-para-aros.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('G1 ClearVision Smart Glass 15ml',
 'A diferencia de recubrimientos repelentes que usan enlaces fisicos debiles, G1 usa un enlace quimico que le otorga una durabilidad inigualable.',
 14800.00, 26, 'https://www.autocr.net/245-home_default/g1-clearvision-smart-glass-15ml-ceramico-para-vidrios.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('G1 ClearVision Smart Glass 100ml',
 'A diferencia de recubrimientos repelentes que usan enlaces fisicos debiles, G1 usa un enlace quimico que le otorga una durabilidad de hasta dos anos.',
 49000.00, 30, 'https://www.autocr.net/1815-home_default/g1-clearvision-smart-glass-100ml-ceramico-para-vidrios-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('G4 Nanotech Glass Polish 100ml',
 'Pulidor a base de agua para vidrio con nanoabrasivos y acido citrico que aseguran un acabado libre de rayones y manchas.',
 11500.00, 7, 'https://www.autocr.net/3097-home_default/g4-nanotech-glass-polish-100ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Gtechniq'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de vidrios')),

('Pistola para aplicar Ducha Grafitada',
 'Pistola ideal para aplicar la Ducha Grafitada Lubristone: llena el envase de producto, conecta al compresor y protege el auto.',
 12000.00, 23, 'https://www.autocr.net/2265-home_default/pistola-para-aplicar-ducha-grafitada.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Ducha Grafitada Negra 20 litros',
 'Disenada para recubrir, proteger y lubricar las partes metalicas de vehiculos y maquinaria pesada expuestas a la corrosion.',
 72000.00, 28, 'https://www.autocr.net/616-home_default/ducha-grafitada-negra-20-litros.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Ducha Grafitada Transparente 20 litros',
 'Disenada para recubrir, proteger y lubricar las partes metalicas de vehiculos y maquinaria pesada expuestas a la corrosion.',
 72000.00, 15, 'https://www.autocr.net/617-home_default/ducha-grafitada-transparente-20-litros.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Renovador de Partes Negras 500ml',
 'Renueva el color negro de las partes a tratar y genera brillo en superficies negras deterioradas por el sol y el tiempo.',
 5200.00, 10, 'https://www.autocr.net/1085-home_default/renovador-de-partes-negras-lubristone-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Renovador de Partes Negras 125ml',
 'Renueva el color negro de las partes a tratar y genera brillo en superficies negras deterioradas por el sol y el tiempo.',
 2800.00, 4, 'https://www.autocr.net/1087-home_default/renovador-de-partes-negras-lubristone-125ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Renovador de Partes Negras 12 unidades 500ml',
 'Renueva el color negro de las partes a tratar y genera brillo en superficies negras deterioradas por el sol y el tiempo.',
 39000.00, 40, 'https://www.autocr.net/2874-home_default/renovador-de-partes-negras-12-unidades-lubristone-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Lubristone'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Magic Wheel Cleaner - Limpiador de aros 500ml',
 'Limpiador potente, espeso y libre de acidos que brinda desempeno innovador para una limpieza suave y profunda de aros.',
 12000.00, 15, 'https://www.autocr.net/1636-home_default/magic-wheel-cleaner-limpiador-de-aros-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Limpiadores de aros y llantas')),

('Botella y atomizador Koch-Chemie 1 litro',
 'Botella cilindrica premium de 1 litro con escala de volumen y dilucion, incluye atomizador.',
 4300.00, 34, 'https://www.autocr.net/2058-home_default/botella-y-atomizador-koch-chemie-1-litro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Botellas')),

('Micro Cut Pad 6 pulgadas',
 'Pad de espuma especial de alta calidad para eliminar rasgunos finos, hologramas y marcas de pulido.',
 10500.00, 18, 'https://www.autocr.net/1625-home_default/micro-cut-pad-pad-de-acabado-6-pulgadas.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Micro Cut Pad 5 pulgadas',
 'Pad de espuma especial de alta calidad para eliminar rasgunos finos, hologramas y marcas de pulido.',
 9500.00, 8, 'https://www.autocr.net/1624-home_default/micro-cut-pad-pad-de-acabado-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Fine Cut Pad 6 pulgadas',
 'Pad abrasivo de grado medio para eliminar intemperie moderada y rasgunos junto con el pulidor Fine Cut F6.01.',
 10500.00, 37, 'https://www.autocr.net/1623-home_default/fine-cut-pad-pad-de-corte-fino-6-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Fine Cut Pad 5 pulgadas',
 'Pad abrasivo de grado medio para eliminar intemperie moderada y rasgunos junto con el pulidor Fine Cut F6.01.',
 9500.00, 31, 'https://www.autocr.net/1622-home_default/fine-cut-pad-pad-de-corte-fino-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Heavy Cut Pad 6 pulgadas',
 'Pad de espuma de corte abrasivo ideal para remover desgaste intenso de pintura e imperfecciones severas.',
 10500.00, 37, 'https://www.autocr.net/1621-home_default/heavy-cut-pad-pad-de-corte-6-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Heavy Cut Pad 5 pulgadas',
 'Pad de espuma de corte abrasivo ideal para remover desgaste intenso de pintura e imperfecciones severas.',
 9500.00, 32, 'https://www.autocr.net/1620-home_default/heavy-cut-pad-pad-de-corte-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Top Star - Acondicionador de plasticos internos 1000ml',
 'Producto de muy alta calidad para el cuidado de plasticos del interior del auto, limpia y acondiciona en un solo paso con acabado satinado natural.',
 15000.00, 30, 'https://www.autocr.net/1619-home_default/top-star-acondicionador-de-plasticos-internos-1000ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Spray Sealant - Sellador en spray 500ml',
 'Sellador en spray eficaz para conseguir una superficie de alto brillo con acabado aterciopelado de forma rapida y sencilla.',
 16000.00, 8, 'https://www.autocr.net/1615-home_default/spray-sealant-sellador-en-spray-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Plast Star - Proteccion para plasticos externos y llantas 1000ml',
 'Producto premium para el cuidado de plasticos, cuida y conserva durante meses siendo extremadamente estable a la radiacion UV.',
 17500.00, 11, 'https://www.autocr.net/1614-home_default/plast-star-proteccion-para-plasticos-externos-y-llantas-1000ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Motorplast - Protector de motor acabado mate 500ml',
 'Protege del agua y suciedad la parte exterior de motores, carcasas plasticas y mangueras de goma, con acabado mate.',
 10000.00, 26, 'https://www.autocr.net/1611-home_default/motorplast-protector-de-motor-acabado-mate-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Koch-Chemie'), (SELECT id_categoria FROM categoria WHERE nombre='Detalladores de pintura')),

('STEK Formula Slip-Tac 500ml',
 'Solucion de montaje de pelicula con tensioactivos y pH adecuado para reducir tension superficial, diseñada para asegurar facil posicionamiento del PPF.',
 26500.00, 2, 'https://www.autocr.net/1821-home_default/stek-formula-slip-tac-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('STEK Formula 16 Finish - Detallador para mantenimiento de PPF',
 'El mejor detallador rapido disenado para agregar maximo brillo y capas avanzadas de proteccion a la pintura o PPF del vehiculo.',
 15500.00, 32, 'https://www.autocr.net/2119-home_default/stek-formula-16-finish-detallador-para-mantenimiento-de-ppf.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('PPF Stek DYNOshield 1 metro x 152cm',
 'Pelicula protectora autorreparable de 4a generacion que combina ventajas del poliuretano con los mejores recubrimientos hidrofobicos.',
 76000.00, 2, 'https://www.autocr.net/2003-home_default/ppf-stek-dynoshield-1-metro-x-152cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Manillas PPF universales 4 unidades',
 'Pelicula protectora DYNOshield para proteger la copa de la manija de la puerta contra rayones de unas, anillos o llaves.',
 7500.00, 11, 'https://www.autocr.net/2112-home_default/manillas-ppf-universales-stek-4-unidades.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Filos de puertas PPF 5 metros x 12cm',
 'Pelicula protectora de pintura DYNOshield con tecnologia de autorreparacion integrada que garantiza resistencia a los rayones.',
 8000.00, 4, 'https://www.autocr.net/2115-home_default/filos-de-puertas-ppf-5-metros-x-12cm.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('PPF Stek DYNOshield 1 metro x 76cm',
 'Pelicula protectora autorreparable de 4a generacion que combina ventajas del poliuretano con los mejores recubrimientos hidrofobicos.',
 40500.00, 16, 'https://www.autocr.net/2118-home_default/ppf-stek-dynoshield-1-metro-x-76cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('DYNOsmoke - PPF para ahumado focos 1 metro x 60cm',
 'Pelicula de proteccion para focos color gris humo con capa superior patentada, proteccion optima contra manchas de agua e insectos.',
 38000.00, 21, 'https://www.autocr.net/3293-home_default/dynosmoke-ppf-para-ahumado-focos-1-metro-x-60cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Squeegee para Dynoflex, Wrap o Polarizado',
 'Squeegee BlueMAX estandar de la industria, perfecto para aplicaciones de wrap, polarizado y especialmente para instalar Dynoflex.',
 16000.00, 34, 'https://www.autocr.net/2182-home_default/squeegee-para-dynoflex-wrap-o-polarizado-con-repuesto-incluido.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Formula 19 Cure - Mantenimiento para Dynoflex 500ml',
 'Solucion de cuidado posterior de primera calidad para mantener peliculas de parabrisas como STEK DYNOflex en optimas condiciones.',
 22000.00, 6, 'https://www.autocr.net/2351-home_default/formula-19-cure-mantenimiento-para-dynoflex-500ml.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Polarizado NEX Control Clima 152cm y 100cm',
 'Pelicula de nanoceramica de primera calidad que nunca se desvanece, con tecnologia a base de grafeno para mantener el interior del auto fresco.',
 28000.00, 37, 'https://www.autocr.net/2526-home_default/polarizado-nex-control-clima-152cm-y-100cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Alcohol isopropilico 98% Galon',
 'Producto idoneo para la instalacion de PPF.',
 19000.00, 8, 'https://www.autocr.net/2624-home_default/alcohol-isopropilico-98-galon.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('NT Cutter 9mm blade 10pcs pack',
 'Cuchilla de repuesto de acero con alto contenido de carbono, proceso de tratamiento termico japones, filo afilado y duradero.',
 4600.00, 19, 'https://www.autocr.net/4049-home_default/nt-cutter-9mm-blade-10pcs-pack-30-hojas-de-cutter-de-repuesto-carpro.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='STEK'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Bead Maker 16 oz.',
 'Protector de pintura que lleva el brillo al siguiente nivel y agrega proteccion, con aplicacion rapida contra suciedad y rayos UV.',
 8250.00, 7, 'https://www.autocr.net/463-home_default/bead-maker-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Bead Maker Galon - Sellador de pintura',
 'Protector de pintura que lleva el brillo al siguiente nivel y agrega proteccion, con aplicacion rapida contra suciedad y rayos UV.',
 24000.00, 34, 'https://www.autocr.net/471-home_default/bead-maker-galon-sellador-de-pintura-ps.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Brake Buster 16 oz.',
 'Elimina de forma segura el polvo de frenos, aceite, suciedad y corrosion leve de ruedas sin danar la superficie.',
 6000.00, 18, 'https://www.autocr.net/487-home_default/ps-brake-buster-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Limpiadores de aros y llantas')),

('Brake Buster Galon',
 'Elimina de forma segura el polvo de frenos, aceite, suciedad y corrosion leve de ruedas sin danar la superficie.',
 19000.00, 28, 'https://www.autocr.net/493-home_default/ps-brake-buster-galon.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Limpiadores de aros y llantas')),

('Kit limpieza de aros y llantas',
 'Kit con Brake Buster Galon, cepillo de aros y cepillo de llantas para una correcta limpieza.',
 27450.00, 30, 'https://www.autocr.net/558-home_default/kit-limpieza-de-aros-y-llantas.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Limpiadores de aros y llantas')),

('Carnauba Creme Wax - Cera liquida galon',
 'Cera liquida de Carnauba que desarrolla un excelente acabado brillante y limpio de la pintura del vehiculo.',
 25500.00, 8, 'https://www.autocr.net/1097-home_default/carnauba-creme-wax-cera-liquida-con-carnauba-galon.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('EZ Blue Fas Wax - Cera en pasta 19 oz.',
 'Cera en pasta de carnauba de grado puro sin limpiadores que deposita un acabado rico y profundo, facil de aplicar y quitar.',
 15500.00, 14, 'https://www.autocr.net/1098-home_default/ez-blue-fas-wax-cera-en-pasta-19-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Rags to Riches - Detergente para toallas de microfibra 32 oz.',
 'Detergente de microfibra de proxima generacion que limpia en profundidad y restaura la capacidad de absorcion y color de las toallas.',
 13000.00, 20, 'https://www.autocr.net/1103-home_default/rags-to-riches-detergente-para-toallas-de-microfibra-32-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Carpet Bomber - Limpiador de alfombras y tapiceria galon',
 'Derivados de citricos y limpiadores biodegradables disuelven la grasa y levantan la suciedad de alfombras, vinil y superficies pintadas.',
 15000.00, 35, 'https://www.autocr.net/1105-home_default/carpet-bomber-limpiador-de-alfombras-y-tapiceria-galon.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Limpieza de interiores')),

('XPRESS Interior Cleaner galon',
 'Limpiador de interiores que sirve para plastico, vinil, cuero, caucho y metal: puertas, dash, consolas y molduras.',
 15500.00, 19, 'https://www.autocr.net/3733-home_default/xpress-interior-cleaner-galon-limpiador-de-interiores-ps.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Limpieza de interiores')),

('Wipe N Shine - Abrillantador de llantas en gel galon',
 'Nueva tecnologia originalmente formulada para lavados de autos industriales, apósito de gel unico aplicable en seco o humedo.',
 31000.00, 37, 'https://www.autocr.net/1108-home_default/wipe-n-shine-abrillantador-de-llantas-en-gel-galon.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Abrillantadores de llantas')),

('No Rub - Abrillantador de plasticos en aerosol',
 'Producto para partes negras en aerosol, ideal para plastico, caucho y vinil, ahorra tiempo en zonas incomodas como rejillas.',
 8000.00, 32, 'https://www.autocr.net/1110-home_default/no-rub-abrillantador-de-plasticos-en-aerosol.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='P&S'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Pulidora M15 PRO - Doble accion',
 'Pulidora DA de 15mm y orbita de 15mm que ofrece mayor eficiencia del motor, garantizando mas potencia y par en cualquier superficie.',
 148500.00, 25, 'https://www.autocr.net/50-home_default/pulidora-m15-pro-doble-accion-.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Maquinas pulidoras')),

('Machine Polisher Wall Holder - Soporte para pulidoras',
 'Soporte de pared para dos pulidoras con anillo de goma para mantenerlas suaves y comodas al guardarlas.',
 33500.00, 16, 'https://www.autocr.net/1258-home_default/machine-polisher-wall-holder-soporte-para-pulidoras-maxshine.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de pintura')),

('Esponjas aplicadoras 8pack',
 'Esponjas para aplicar cera o abrillantador, espuma super suave, duradera y de celulas finas, paquete de 8 unidades.',
 8000.00, 15, 'https://www.autocr.net/65-home_default/esponjas-aplicadoras-8pack-maxshine-.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Cepillo para lavar llantas MaxShine',
 'Perfecto para la limpieza de llantas e incluso alfombras.',
 4000.00, 4, 'https://www.autocr.net/66-home_default/cepillo-para-lavar-llantas-maxshine.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Backing Plate Rotativa 5 pulgadas',
 'Plato MaxShine de reemplazo para maquina rotativa en 5 pulgadas.',
 9000.00, 13, 'https://www.autocr.net/107-home_default/backing-plate-rotativa-5-pulgadas-maxshine.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Backing Plate Rotativa 3 pulgadas',
 'Plato MaxShine de reemplazo para maquina rotativa en 3 pulgadas.',
 6000.00, 23, 'https://www.autocr.net/108-home_default/backing-plate-rotativa-3-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Pads microfibra celestes pack 4',
 'Esponja envuelta en microfibra super suave que evita arananzos en pintura, plastico o vidrios, ideal para ceras y selladores.',
 5000.00, 24, 'https://www.autocr.net/2357-home_default/pads-microfibra-celestes-maxshine-pack-4.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de vidrios')),

('Brochas para detallado 2 pack',
 'Brochas para eliminar polvo y suciedad de superficies, funcionan bien para limpiar alrededor de insignias sin rayar acabados.',
 9500.00, 23, 'https://www.autocr.net/111-home_default/brochas-para-detallado-2pack.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Backing Plate Pulidora doble accion 5 pulgadas',
 'Backing plate ideal para todas las marcas de pulidoras de doble accion con rosca de 5/16 de diametro, tamano 5 pulgadas.',
 9000.00, 10, 'https://www.autocr.net/116-home_default/backing-plate-pulidora-doble-accion-5-pulgadas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='MaxShine'), (SELECT id_categoria FROM categoria WHERE nombre='Pads')),

('Creature Eagle Edgeless 420 gsm',
 'Toalla de detalles de uso multiple de microfibra de grado profesional de 420 gsm, sin bordes que puedan rayar superficies.',
 2500.00, 11, 'https://www.autocr.net/36-home_default/creature-eagle-edgeless-420-gsm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Liquid8r - Toalla de secado 50x60cm',
 'Toalla de secado de bucle giratorio con peso satisfactorio, mezcla premium 70/30 y borde oculto, resistente al agua pero suave con la pintura.',
 14250.00, 5, 'https://www.autocr.net/1137-home_default/liquid8r-toalla-de-secado-50x60cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Creature Eagle Edgeless 420 gsm Negro',
 'Toalla de detalles de uso multiple de microfibra de grado profesional de 420 gsm, sin bordes que puedan rayar superficies.',
 2500.00, 17, 'https://www.autocr.net/1228-home_default/creature-eagle-edgeless-420-gsm-negro.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Creature Eagle Edgeless 420 gsm Verde',
 'Toalla de detalles de uso multiple de microfibra de grado profesional de 420 gsm, sin bordes que puedan rayar superficies.',
 2500.00, 18, 'https://www.autocr.net/1226-home_default/creature-eagle-edgeless-420-gsm-verde.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('The Edgeless Pearl - Toalla para nivelar ceramico Gris',
 'Toalla excelente para eliminar y nivelar revestimientos ceramicos, tejido perlado de circuito cerrado, sin pelusa y suave.',
 1600.00, 33, 'https://www.autocr.net/1883-home_default/the-edgeless-pearl-toalla-para-nivelar-ceramico-the-rag-company-gris.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Rags to Riches Galon - Detergente para toallas de microfibra',
 'Detergente de microfibra de proxima generacion que limpia en profundidad y restaura absorcion y color de las toallas.',
 24500.00, 8, 'https://www.autocr.net/1987-home_default/rags-to-riches-galon-detergente-para-toallas-de-microfibra.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Kit de limpieza profunda interna',
 'Kit de productos para limpiar plasticos, cuero y tapicerias, combinando productos de varias marcas.',
 51255.00, 26, 'https://www.autocr.net/1993-home_default/kit-de-limpieza-profunda-interna.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Limpieza de interiores')),

('Waffle Weave - Toallas para vidrios 3 pack',
 'Toalla de microfibra de excelente calidad con bolsillos que atrapan liquidos, dando tiempo adicional para absorber.',
 6700.00, 23, 'https://www.autocr.net/2084-home_default/waffle-weave-toallas-para-limpiar-vidrios-3pack.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Pulidores de vidrios')),

('Creature Eagle Edgeless 420 gsm Azul',
 'Toalla de detalles de uso multiple de microfibra de grado profesional de 420 gsm, sin bordes que puedan rayar superficies.',
 2500.00, 25, 'https://www.autocr.net/2090-home_default/creature-eagle-edgeless-420-gsm-azul.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Microfibras')),

('Vinyl & Leather Interior Scrub Brush',
 'Cepillo para limpiar profundamente interiores de cuero y vinil, elimina suciedad y mugre del grano profundo.',
 8000.00, 16, 'https://www.autocr.net/2097-home_default/vinyl-leather-interior-scrub-brush-cepillo-para-limpieza-de-cuero.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='The Rag Company'), (SELECT id_categoria FROM categoria WHERE nombre='Limpieza de interiores')),

('Detailing Bucket Black Rinse - Balde de lavado',
 'Balde para enjuague profesional de color negro, hecho de resistente material HDPE con medidas laterales para dosificar producto.',
 8250.00, 19, 'https://www.autocr.net/2982-home_default/detailing-bucket-black-rinse-balde-de-lavado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing Bucket Gray Wheels - Balde de lavado',
 'Balde profesional disenado especialmente para limpiar llantas y aros, fabricado en HDPE resistente con capacidad de 20 litros.',
 8250.00, 29, 'https://www.autocr.net/2986-home_default/detailing-bucket-gray-wheels-balde-de-lavado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Bucket Hanger - Colgador para balde',
 'Accesorio practico que se fija al costado de cualquier balde y admite varios cepillos y herramientas de limpieza.',
 8750.00, 38, 'https://www.autocr.net/2989-home_default/bucket-hanger-colgador-para-balde-work-stuff.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing Bucket Yellow Wash - Balde de lavado',
 'Balde de lavado de autos profesional en HDPE duradero con marcas de medicion laterales.',
 8250.00, 6, 'https://www.autocr.net/2990-home_default/detailing-bucket-yellow-wash-balde-de-lavado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Clay Block - Herramienta para descontaminacion mecanica',
 'Bloque de arcilla que proporciona la friccion necesaria para eliminar polvo de frenos, savia y otros contaminantes persistentes.',
 8000.00, 37, 'https://www.autocr.net/2993-home_default/clay-block-herramienta-para-descontaminacion-mecanica-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Clean Hands Tire Applicator',
 'Aplicador de abrillantador de llantas con mango de plastico y esponja de alta calidad, sin residuos en las manos.',
 3950.00, 10, 'https://www.autocr.net/2994-home_default/clean-hands-tire-applicator-aplicador-para-llantas-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Abrillantadores de llantas')),

('Coating Application Kit - Kit aplicador de ceramicos',
 'Kit esencial para aplicar recubrimientos ceramicos, incluye aplicadores en forma de cubo con ranuras laterales y microfibras.',
 6750.00, 20, 'https://www.autocr.net/2995-home_default/coating-application-kit-kit-aplicador-de-ceramicos.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Coating Applicator - Bloc aplicador de ceramico',
 'Aplicador de recubrimientos ceramicos y de cuarzo, forma de cubo con ranuras laterales para microfibra Suede.',
 1300.00, 38, 'https://www.autocr.net/2996-home_default/coating-applicator-bloc-aplicador-de-ceramico-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Detailing Brush 30 mm - Brocha para detallado',
 'Brocha versatil disenada para tareas de limpieza dentro y fuera del vehiculo, resistente al uso regular y productos quimicos.',
 4450.00, 31, 'https://www.autocr.net/3001-home_default/detailing-brush-30-mm-brocha-para-detallado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing Brush 40 mm - Brocha para detallado',
 'Brocha versatil disenada para tareas de limpieza dentro y fuera del vehiculo, resistente al uso regular y productos quimicos.',
 6250.00, 22, 'https://www.autocr.net/3000-home_default/detailing-brush-40-mm-brocha-para-detallado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing Brush Albino 24 mm',
 'Brocha con cerdas sinteticas para limpieza suave y efectiva en interiores del vehiculo, incluso areas de dificil acceso.',
 6450.00, 40, 'https://www.autocr.net/3002-home_default/detailing-brush-albino-24-mm-brocha-para-detallado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Detailing Brush Albino 3 pack',
 'Set de brochas Detailing ALBINO con cerdas sinteticas de alta calidad para acceder a los lugares mas dificiles.',
 14800.00, 12, 'https://www.autocr.net/3006-home_default/detailing-brush-albino-3-pack-brochas-para-detallado-work-stuff.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Work Stuff'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Lampara de inspeccion de cabeza iMatch 3',
 'Linterna frontal recargable con tecnologia LED COB que proporciona iluminacion extremadamente poderosa y uniforme.',
 67500.00, 26, 'https://www.autocr.net/1791-home_default/lampara-de-inspeccion-de-cabeza-imatch-3-scangrip.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Lampara de inspeccion MiniMatch',
 'Lampara portatil con 2 colores de luz (calido y frio), especialmente util para superficies de colores brillantes y oscuros.',
 50000.00, 24, 'https://www.autocr.net/921-home_default/lampara-de-inspeccion-minimatch-scangrip.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Lamparas Scangrip Essential Detailing Work Light Kit',
 'Kit de iluminacion esencial con lampara I-MATCH 2, MINIMATCH y MULTIMATCH R, mas bolso de nailon.',
 222000.00, 26, 'https://www.autocr.net/1318-home_default/lamparas-scangrip-essential-detailing-work-light-kit.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Lampara de Inspeccion Multimatch R',
 'Luz de trabajo comodo y eficaz para inspeccion y detalle de hasta 1200 lumen, con 5 colores de temperatura diferentes.',
 107500.00, 18, 'https://www.autocr.net/1321-home_default/lampara-de-inspeccion-scangrip-multimatch-r.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Scangrip Wheelstand - Tripode con ruedas',
 'Tripode con ruedas para posicionamiento movil de iluminacion en el taller, facilita el traslado de la luz de un lugar a otro.',
 139000.00, 35, 'https://www.autocr.net/1799-home_default/scangrip-wheelstand-tripode-con-ruedas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('ScanGrip Powerbank - Banco de bateria para I-Match 3',
 'Banco de energia compacto disenado para la linterna frontal SCANGRIP I-MATCH 3, tambien recarga otras luces y dispositivos.',
 29500.00, 33, 'https://www.autocr.net/2622-home_default/scangrip-powerbank-banco-de-bateria-para-i-match-3.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Lampara de inspeccion Multimatch 3 Connect',
 'Potente luz de trabajo de hasta 3000 lumenes, disenada para inspeccionar superficies grandes en pulido, pintura y combinacion de colores.',
 131000.00, 4, 'https://www.autocr.net/3065-home_default/lampara-de-inspeccion-multimatch-3-connect-scangrip.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('UniMatch - Lampara de inspeccion para cabeza',
 'Solucion de iluminacion universal que se transforma de linterna frontal a linterna de bolsillo, versatil y comoda.',
 51000.00, 3, 'https://www.autocr.net/4093-home_default/unimatch-lampara-de-inspeccion-para-cabeza-scangrip.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Lampara de inspeccion Line Light C+R',
 'Linterna de inspeccion y luz de trabajo extremadamente potente, diseño ultradelgado de 25mm de diametro con hasta 8 horas de autonomia.',
 69500.00, 18, 'https://www.autocr.net/4136-home_default/lampara-de-inspeccion-line-light-cr-scangrip.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Scangrip'), (SELECT id_categoria FROM categoria WHERE nombre='Iluminacion y equipo')),

('Squeegee para instalacion de PPF 10cm x 75cm',
 'Squeegee elegante para instalacion de PPF, su ergonomia evita danar el PPF durante la instalacion.',
 6500.00, 26, 'https://www.autocr.net/2845-home_default/squeegee-para-instalacion-de-ppf-10cm-x-75cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Squeegee de goma para polarizado',
 'Ideal para usar durante la instalacion de polarizado en vehiculos, tambien util en puertas de bano y superficies de vidrio.',
 8500.00, 15, 'https://www.autocr.net/2841-home_default/squeegee-de-goma-para-polarizado.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Cinta para cortar Wrap 50 metros',
 'Cinta y filamento flexibles disenados para crear facilmente disenos contorneados y dejar un borde limpio en instalaciones de wrap.',
 15500.00, 23, 'https://www.autocr.net/2839-home_default/cinta-para-cortar-wrap-50-metros.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Centimetro magnetico de 3 metros',
 'Cinta magnetica flexible y portatil para medir superficies planas o curvas, con imanes que permanecen firmes en el acero.',
 4800.00, 9, 'https://www.autocr.net/2838-home_default/centimetro-magnetico-de-3-metros.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Imanes verdes para instalacion de Wrap 2 unidades',
 'Imanes con fuerte fuerza magnetica, funcionan como soporte magnetico para envolturas de wrap durante la instalacion.',
 5500.00, 31, 'https://www.autocr.net/2836-home_default/imanes-verdes-para-instalacion-de-wrap-2-unidades.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Prensa para almacenamiento de rollos PPF o Wrap 11cm',
 'Prensa plastica que permite tener control de los rollos de PPF o Wrap, prensados sin danar el material.',
 2275.00, 9, 'https://www.autocr.net/2833-home_default/prensa-para-almacenamiento-de-rollos-ppf-o-wrap-11cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Removedor de emblemas',
 'Forma ergonomica que permite una eliminacion facil de emblemas y placas de fabrica, con polimeros redondeados que evitan rayar la pintura.',
 13250.00, 23, 'https://www.autocr.net/2832-home_default/removedor-de-emblemas.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Prensa para almacenamiento de rollos PPF o Wrap 15cm',
 'Prensa plastica que permite tener control de los rollos de PPF o Wrap, prensados sin danar el material.',
 2475.00, 7, 'https://www.autocr.net/2827-home_default/prensa-para-almacenamiento-de-rollos-ppf-o-wrap-15cm.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Bomba pulverizadora para PPF o polarizado',
 'Tanque de acero inoxidable, bomba portatil para rociar el liquido necesario al instalar PPF o polarizado, capacidad de 5 galones.',
 149500.00, 22, 'https://www.autocr.net/2826-home_default/bomba-pulverizadora-para-ppf-o-polarizado.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Kit de micro squeegees para PPF y Wrap',
 'Kit de herramientas con iman oculto dentro de cada escobilla, practico y facil de transportar para areas curvas.',
 14100.00, 16, 'https://www.autocr.net/2825-home_default/kit-de-micro-squeegees-para-ppf-y-wrap.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Contenedor para desechar cutter',
 'Contenedor de aleacion de aluminio para retirar y desechar de forma segura las cuchillas usadas.',
 13200.00, 24, 'https://www.autocr.net/2821-home_default/contenedor-para-desechar-cutter.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('Kit de moldes circulares para PPF y Wrap',
 'Herramienta ideal para sacar moldes o circulos de sensores en instalaciones de PPF o Wrap, con varios tamanos.',
 14500.00, 16, 'https://www.autocr.net/2817-home_default/kit-de-moldes-circulares-para-ppf-y-wrap.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='AUTOCR TOOLS'), (SELECT id_categoria FROM categoria WHERE nombre='Proteccion de pintura (PPF y Wrap)')),

('Cleaner Wax Paste',
 'La cera mas vendida en Estados Unidos, formulada para producir resultados deslumbrantes en un solo paso, descontamina, pule y protege.',
 11000.00, 6, 'https://www.autocr.net/306-home_default/cleaner-wax-paste.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Gold Class Paste Wax',
 'Cera con la mas alta concentracion de carnauba, fortificada con polimeros de larga duracion que protegen y dan brillo y profundidad.',
 16500.00, 5, 'https://www.autocr.net/307-home_default/gold-class-paste-wax.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Hybrid Ceramic Wax Spray 26 oz.',
 'Formula que una vez curada crea una capa protectora que agrega brillo y protege la carroceria, con gran repelencia al agua.',
 14500.00, 17, 'https://www.autocr.net/426-home_default/hybrid-ceramic-wax-spray-26-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('3-in-1 Wax 16 oz.',
 'Formula unica que limpia, pule y protege en un solo paso, resultados asombrosos sin multiples pasos.',
 17000.00, 27, 'https://www.autocr.net/429-home_default/3-in-1-wax-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('M66 Quick Detailer Wax Galon',
 'Cera limpiadora de un solo paso para reacondicionamiento de alta produccion, elimina manchas de agua y oxidacion.',
 33500.00, 26, 'https://www.autocr.net/433-home_default/m66-quick-detailer-wax.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Car Wash Deep Crystal 64 oz.',
 'Shampoo mas famoso de Meguiars, formula de pH neutro y alta espuma que remueve contaminantes sin retirar la cera.',
 9000.00, 30, 'https://www.autocr.net/441-home_default/car-wash-deep-crystal-64-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Champus')),

('Liquid Ceramic Wax 16 oz.',
 'Cera liquida hibrida con tecnologia avanzada de proteccion en base a SiO2, facil de usar para todos.',
 16000.00, 23, 'https://www.autocr.net/460-home_default/liquid-ceramic-wax-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Gold Class Wash 64 oz. - Champu',
 'Shampoo disenado especialmente para limpiar y realzar el brillo de la pintura, no requiere mas de una tapa cada 4 litros de agua.',
 11500.00, 2, 'https://www.autocr.net/462-home_default/gold-class-wash-64-oz-champu.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Champus')),

('Hybrid Ceramic Detailer 24 oz.',
 'Detallador eslabon perdido entre lavado y encerado, proporciona proteccion rapida con tecnologia de ceramica hibrida.',
 11500.00, 6, 'https://www.autocr.net/552-home_default/hybrid-ceramic-detailer-24oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Natural Shine 16 oz.',
 'Renueva el dash, viniles y otros plasticos devolviendo el color original, con bloqueadores UV y compuestos limpiadores suaves.',
 7500.00, 9, 'https://www.autocr.net/553-home_default/natural-shine-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('Ultimate Wax Paste',
 'Cera sintetica hidrofobica mas avanzada de Meguiars, ofrece mayor proteccion y la mas larga duracion en la superficie de la pintura.',
 25500.00, 34, 'https://www.autocr.net/1317-home_default/ultimate-wax-pasta.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Ceras y selladores')),

('Endurance Tire Gel 16 oz.',
 'Producto favorito para mejorar el desempeno de los neumaticos, con acabado profundo y capa duradera para llantas.',
 10000.00, 6, 'https://www.autocr.net/571-home_default/endurance-tire-gel-16-oz.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='Meguiars'), (SELECT id_categoria FROM categoria WHERE nombre='Abrillantadores de llantas')),

('Pink Car Soap 16oz. - Champu pH neutro',
 'Shampoo viscoso y super concentrado disenado para limpiar suavemente el vehiculo sin danar la pintura.',
 14500.00, 37, 'https://www.autocr.net/3894-home_default/pink-car-soap-16oz-champu-ph-neutro-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Champus')),

('One Hybrid 32 Oz. - Pulidor de corte y acabado',
 'Compuesto hibrido innovador que combina corte y pulido en una solucion integral con abrasivos de alumina nano ceramicos.',
 26250.00, 34, 'https://www.autocr.net/3891-home_default/one-hybrid-16-oz-pulidor-de-corte-y-acabado-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('One Hybrid 16 Oz. - Pulidor de corte y acabado',
 'Compuesto hibrido innovador que combina corte y pulido en una solucion integral con abrasivos de alumina nano ceramicos.',
 14200.00, 23, 'https://www.autocr.net/3890-home_default/one-hybrid-16-oz-pulidor-de-corte-y-acabado-3d.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('Grand Blast Galon - Desengrasante de motor',
 'Desengrasante mas fuerte de 3D, disenado para limpieza rapida de motores, llantas, suspension y bisagras.',
 15000.00, 18, 'https://www.autocr.net/3889-home_default/grand-blast-galon-desengrasante-de-motor-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Herramientas de trabajo')),

('GLW Series SiO2 Ceramic Wash 16 oz.',
 'Shampoo pH-neutro enriquecido con dioxido de silicio para limpieza efectiva y acabado hidrofobico en una sola aplicacion.',
 8250.00, 10, 'https://www.autocr.net/3888-home_default/glw-series-sio2-ceramic-wash-16-oz-champu-con-ceramico-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('GLW Series SiO2 Ceramic Trim Restore 16 oz.',
 'Restaurador cerámico que transforma plasticos exteriores opacos y desgastados en superficies con acabado ultra-negro.',
 8250.00, 2, 'https://www.autocr.net/3887-home_default/glw-series-sio2-ceramic-trim-restore-16-oz-restaurador-de-plasticos-negros-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Restauradores de plasticos')),

('GLW Series SiO2 Ceramic Tire Gloss 16 oz.',
 'Brillador ceramico para llantas que realza color y brillo del caucho y protege contra polvo, agua y rayos UV.',
 8250.00, 36, 'https://www.autocr.net/3885-home_default/glw-series-sio2-ceramic-tire-gloss-16-oz-protector-de-llantas-brillante-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('GLW Series SiO2 Ceramic Matte Tire 16 oz.',
 'Producto premium para el cuidado de llantas con acabado mate profundo y proteccion contra decoloracion y agrietamiento.',
 8250.00, 38, 'https://www.autocr.net/3884-home_default/glw-series-sio2-ceramic-matte-tire-16-protector-de-llantas-mate-3d.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('GLW Series SiO2 Ceramic Interior 16 oz.',
 'Limpiador interior premium con tecnologia ceramica SiO2 que elimina polvo y suciedad mejorando el brillo del interior.',
 8250.00, 12, 'https://www.autocr.net/3883-home_default/glw-series-sio2-ceramic-interior-16-oz-limpiador-de-interiores-hidrofobico-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('GLW Series SiO2 Ceramic Glass Cleaner 16 oz.',
 'Limpiacristales ceramico SiO2 que establece un nuevo estandar en repelencia a la lluvia y proteccion.',
 7000.00, 10, 'https://www.autocr.net/3895-home_default/glw-series-sio2-ceramic-glass-cleaner-16-oz-limpiador-de-vidrios-con-ceramico-3d.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('GLW Series SiO2 Ceramic Detailer 16 oz.',
 'Detallador ceramico avanzado con polimeros de SiO2 que crea una barrera protectora contra agua, suciedad y manchas.',
 9000.00, 29, 'https://www.autocr.net/3881-home_default/glw-series-sio2-ceramic-detailer-16-oz-detallador-con-ceramico-3d.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='3D Car Care'), (SELECT id_categoria FROM categoria WHERE nombre='Ceramicos para pintura')),

('WASHR PRO20 - Hidrolavadora electrica',
 'Hidrolavadora de calidad profesional con Sistema Total Stop, tecnologia de induccion sin escobillas y cabezal de bomba triple piston.',
 515000.00, 2, 'https://www.autocr.net/3841-home_default/washr-pro20-hidrolavadora-electrica-bigboi.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('Wash Pro Hose Pack - Kit de manguera, lanza y pistola',
 'Paquete de mangueras probado para brindar el mejor rendimiento con la hidrolavadora WASHR PRO.',
 128000.00, 27, 'https://www.autocr.net/3834-home_default/wash-pro-hose-pack-kit-de-manguera-lanza-y-pistola-para-hidrolavadora-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('Manguera de 9 metros para soplador',
 'Manguera comercial de 9 metros perfecta para secar zonas altas o de dificil acceso en vehiculos grandes.',
 49500.00, 40, 'https://www.autocr.net/3833-home_default/manguera-de-9-metros-para-soplador-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('BlowR Pro MK2 - Soplador para secado de autos',
 'Secador profesional para autos que permite secado completo sin contacto, eliminando el riesgo de rayones.',
 285000.00, 21, 'https://www.autocr.net/3832-home_default/blowr-pro-mk2-soplador-para-secado-de-autos-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('D-IONIZR1 - Sistema de filtracion de agua',
 'Separador de agua de dos etapas con filtro de carbon compuesto disenado para ablandar el agua dura.',
 312000.00, 27, 'https://www.autocr.net/3831-home_default/d-ionizr1-sistema-de-filtracion-de-agua-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('SuckR Pro - Aspiradora seco y humedo',
 'Aspiradora potente con deposito de 30 litros y tecnologia de filtracion de aire con filtros HEPA reemplazables.',
 320000.00, 36, 'https://www.autocr.net/3857-home_default/suckr-pro-aspiradora-seco-y-humedo-y-succion-para-limpieza-tapiceria-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('SuckR Pro Plus - Aspiradora comercial',
 'Aspiradora en seco y humedo de doble motor de calidad comercial, unidad 2 en 1 que se puede montar en la pared.',
 497000.00, 35, 'https://www.autocr.net/3838-home_default/suckr-pro-plus-aspiradora-comercial-para-seco-y-humedo-bigboi.jpg', true, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('WASHRFLO Pressure Washer - Hidrolavadora electrica',
 'Unidad compacta que ofrece limpieza potente y uniforme con nivel de ruido minimo para profesionales del detailing.',
 285000.00, 12, 'https://www.autocr.net/4032-home_default/washrflo-pressure-washer-hidrolavadora-electrica-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('Bolsas de papel para aspiradora SuckR Pro Plus 2 unidades',
 'Bolsa de papel de repuesto para la SUCKR PRO+.',
 14000.00, 12, 'https://www.autocr.net/4033-home_default/bolsas-de-papel-para-aspiradora-suckr-pro-plus-2-unidades-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado')),

('SuckrBag disposable filter bags 3 unidades',
 'Paquete de 3 bolsas de filtro desechables para el SUCKR PRO.',
 15000.00, 8, 'https://www.autocr.net/4034-home_default/suckrbag-disposable-filter-bags-3-unidades-bigboi.jpg', false, true,
 (SELECT id_marca FROM marca WHERE nombre='BIGBOI'), (SELECT id_categoria FROM categoria WHERE nombre='Equipo de lavado'))
ON DUPLICATE KEY UPDATE
 descripcion = VALUES(descripcion),
 precio = VALUES(precio),
 stock = VALUES(stock),
 imagen_url = VALUES(imagen_url),
 destacado = VALUES(destacado),
 activo = VALUES(activo),
 id_marca = VALUES(id_marca),
 id_categoria = VALUES(id_categoria);
