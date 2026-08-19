# Datos de demostracion del Avance 3

Con MySQL iniciado y el esquema base de `autocr.sql` ya creado, ejecutar en este orden:

1. `catalogo-avance3.sql`: agrega o actualiza 3 marcas, 8 categorias y 151 productos.
2. `seed-pedidos-historicos.sql`: carga 500 pedidos con sus lineas e historial.

Ambos archivos son reejecutables. El catalogo usa sus llaves unicas y el seed registra la migracion `avance3-pedidos-historicos-v1` para no duplicar informacion.

Conteos esperados partiendo del seed base del proyecto:

- 18 marcas.
- 21 categorias.
- 186 productos.
- 500 pedidos historicos.
- 1242 lineas de pedido.
- 1412 cambios de estado.
