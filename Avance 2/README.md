# AutoCR Pro - Avance 3 (SC-403 Desarrollo de Aplicaciones Web y Patrones)

Plataforma web transaccional para AutoCR (tienda costarricense de detailing y
limpieza automotriz), desarrollada con **Java + Spring Boot + Thymeleaf +
Bootstrap + JPA/Hibernate + MySQL**, siguiendo el patron MVC visto durante el
curso (Semanas 1 a 8).

Este avance completa el rediseño de la tienda existente (`autocr.net`) segun
el prototipo visual entregado (`AutoCR Pro`): terminan las historias de
media/baja prioridad que quedaron pendientes del Avance 2, se agrega inicio
de sesion con Google, validacion de direcciones con Google Maps, y se amplia
el catalogo con productos reales adicionales de autocr.net.

## Integrantes

Brandon Siles Mata, Felipe Fernandez Solano, Tommy Alfaro Miranda.

## Estado del avance (historias implementadas)

El 100% del backlog original (Historias 1 a 20) esta implementado, mas dos
historias nuevas que no estaban en el backlog original (login con Google e
integracion con Google Maps):

| # | Historia | Estado |
|---|----------|--------|
| H1 | Inicio con productos destacados, marcas y ofertas | Completa |
| H2 | Busqueda por nombre, marca o categoria | Completa |
| H3 | Registro de cuenta | Completa |
| H4 | Inicio de sesion | Completa |
| H5 | Recuperacion de contrasena (token de un solo uso) | Completa* |
| H6 | Detalle de producto | Completa |
| H7 | Carrito de compras | Completa |
| H8 | Checkout transaccional (entrega, pago, confirmacion) | Completa |
| H9 | Administracion de productos | Completa |
| H10 | Administracion de pedidos | Completa |
| H11 | Historial de pedidos del cliente (`/mis-pedidos`) | Completa |
| H12 | Direcciones guardadas del cliente (`/mis-direcciones`) | Completa |
| - | Favoritos / lista de deseos (`/favoritos`) | Completa |
| - | Alertas de inventario visibles al vendedor | Completa (dashboard + `/admin/inventario`) |
| - | Gestion de marcas y categorias desde el panel | Completa (`/admin/marcas-categorias`) |
| - | Reportes de ventas con graficos | Completa (`/admin/reportes`, Chart.js) |
| - | Internacionalizacion (es/en) | Completa (selector de idioma en el header) |
| - | Administracion de usuarios y roles | Completa (`/admin/usuarios`, solo ADMINISTRADOR) |
| - | Vista de clientes para el vendedor | Completa (`/admin/clientes`) |
| - | Inicio de sesion con Google (OAuth) | Completa** |
| - | Validacion de direcciones con Google Maps (Places Autocomplete) | Completa** |

\* El envio del codigo de recuperacion se **simula en pantalla** (se muestra
el codigo directamente al usuario) porque el envio real de correos requiere
`JavaMailSender`, algo no cubierto en el curso. Sustituirlo por un envio real
es un cambio aislado en `RecuperacionService` + `AuthController`.

\*\* Requieren credenciales propias de Google Cloud (ver seccion "Google
OAuth y Google Maps" mas abajo). Sin esas credenciales, el boton de Google
en login/registro redirige con un mensaje claro de "no configurado" y el
autocompletado de direcciones se degrada a un campo de texto normal — el
resto de la aplicacion funciona igual.

## Arquitectura

Organizacion por capas (patron de la Semana 3):

```
com.autocr
 ├─ domain/        Entidades JPA (Usuario, Rol, Producto, Marca, Categoria,
 │                 Carrito, CarritoItem, Pedido, PedidoDetalle,
 │                 PedidoEstadoHistorial, RecuperacionContrasena,
 │                 Direccion, Favorito)
 ├─ repository/    Interfaces JpaRepository (consultas derivadas y JPQL)
 ├─ service/       Logica de negocio y reglas (stock, roles, tokens,
 │                 reportes, etc.)
 ├─ controller/    Controladores publicos (inicio, productos, carrito,
 │   │             checkout, auth, Google OAuth, pedidos del cliente,
 │   │             favoritos, direcciones)
 │   └─ admin/     Controladores del panel administrativo (dashboard,
 │                 productos, pedidos, clientes, marcas/categorias,
 │                 inventario, reportes, usuarios)
 ├─ carrito/       Objetos de sesion (carrito y datos de entrega en memoria)
 ├─ config/        ProjectConfig (interceptores de acceso admin + idioma),
 │                 AuthInterceptor, PasswordUtil, GlobalModelConfig
 └─ util/          Constantes de sesion y excepcion de negocio
```

Vistas Thymeleaf en `src/main/resources/templates`, con fragmentos comunes en
`general/fragmentos.html` (encabezado con selector de idioma, pie, mensajes,
script de Google Maps) y `admin/fragmentos.html` (barra lateral del panel,
ahora con todas las secciones habilitadas).

No se usa Spring Security (no visto en el curso): el login guarda el usuario
en la sesion HTTP y el acceso a `/admin/**` se protege con un
`HandlerInterceptor` registrado en `ProjectConfig`. El login con Google
tampoco usa Spring Security: es un flujo Authorization Code implementado a
mano en `GoogleAuthController` (llamadas HTTP directas a los endpoints de
Google con `java.net.http.HttpClient`), que al final llama al mismo
mecanismo de sesion que el login normal.

## Requisitos para ejecutar

- JDK 17+
- MySQL 8 (local o en la nube, por ejemplo Aiven, como se vio en la Semana 3)
- NetBeans con soporte de proyectos Maven (el proyecto es un Maven estandar,
  NetBeans lo reconoce directamente con "Open Project")

## Como ejecutar en NetBeans

1. Ejecutar `database/autocr.sql` para crear el esquema y los datos base.
2. Ejecutar `database/catalogo-avance3.sql` para ampliar el catalogo.
3. Ejecutar `database/seed-pedidos-historicos.sql` para cargar los 500
   pedidos utilizados por el dashboard y los reportes.
4. Abrir el proyecto en NetBeans (`File > Open Project`, seleccionar la
   carpeta `autocr`).
5. Configurar las credenciales locales en `.env.properties` (archivo
   ignorado por Git) o mediante las variables `DB_USERNAME` y `DB_PASSWORD`.
6. Ejecutar el proyecto y visitar `http://localhost:8080`.

Si el boton Run de NetBeans no encuentra la clase principal, usar
`nbactions.xml` (ya incluido) o correr manualmente el goal Maven
`spring-boot:run`.

## Google OAuth y Google Maps (opcional)

Ambas integraciones son opcionales: sin configurarlas, la app funciona
normalmente (login local, direcciones sin autocompletado). Para activarlas:

1. Crear un proyecto en [Google Cloud Console](https://console.cloud.google.com/).
2. **OAuth (login con Google)**: en "APIs y servicios > Credenciales", crear
   credenciales OAuth 2.0 de tipo "Aplicacion web" con
   `http://localhost:8080/login/google/callback` como URI de redireccion
   autorizada. Definir las variables de entorno:
   - `GOOGLE_CLIENT_ID`
   - `GOOGLE_CLIENT_SECRET`
3. **Maps (autocompletado de direcciones)**: habilitar "Maps JavaScript API"
   y "Places API" en el mismo proyecto, crear una API key y definir:
   - `GOOGLE_MAPS_API_KEY`
4. Reiniciar la aplicacion. `application.properties` ya lee estas variables
   (`${GOOGLE_CLIENT_ID:}`, etc.) — no hace falta tocar el codigo ni escribir
   las llaves directamente en el archivo.

## Usuarios de prueba (ver `database/autocr.sql`)

| Correo | Contrasena | Rol |
|---|---|---|
| admin@autocr.net | Admin123! | ADMINISTRADOR |
| vendedor@autocr.net | Vendedor123! | VENDEDOR |
| inventario@autocr.net | Inventario123! | INVENTARIO |
| juan@email.com | Cliente123! | CLIENTE |

Si se carga `database/seed-pedidos-historicos.sql`, se agregan 45 clientes
sinteticos (correo `nombre.apellido@gmail.com`), todos con la contrasena
`Cliente123!`.

## Datos del catalogo

Los productos, marcas, categorias, precios (colones) e imagenes de los
scripts de `database/` fueron tomados directamente del sitio real de la empresa
(`autocr.net`), tal como se indico para el rediseno. El catalogo del
Avance 2 (35 productos, 15 marcas, 13 categorias) se amplio en el Avance 3
con productos adicionales reales de autocr.net, para un total de:

- **186 productos**
- **18 marcas** (las 15 originales mas 3D Car Care, BIGBOI y Meguiars)
- **21 categorias** (las 13 originales mas Proteccion de pintura (PPF y
  Wrap), Iluminacion y equipo, Equipo de lavado, Limpieza de interiores,
  Ceras y selladores, Restauradores de plasticos, Limpiadores de aros y
  llantas, Pulidores de pintura)

## Reglas de negocio implementadas

- El stock no puede quedar negativo (validado en `ProductoService` y al
  confirmar el pedido).
- Solo usuarios autenticados pueden confirmar pedidos (`CheckoutController`).
- Cada pedido debe tener al menos un producto (`PedidoService`).
- Los productos desactivados no aparecen en el catalogo publico
  (`ProductoRepository.buscar` filtra por `activo = true`) pero se
  conservan en la base de datos.
- Todo cambio de estado de un pedido queda registrado en
  `PedidoEstadoHistorial`.
- La recuperacion de contrasena usa un token de un solo uso con
  vencimiento de 15 minutos.
- Un usuario que inicia sesion con Google se vincula automaticamente a una
  cuenta local existente con el mismo correo, o crea una cuenta CLIENTE
  nueva si no existe (`UsuarioService.autenticarOCrearConGoogle`).
- Solo `ADMINISTRADOR` puede cambiar el rol o desactivar cuentas de otros
  usuarios (`/admin/usuarios`); un administrador no se puede desactivar a
  si mismo.
- La primera direccion guardada de un cliente queda como predeterminada
  automaticamente; solo puede haber una direccion predeterminada a la vez
  (`DireccionService`).

La secuencia exacta de carga de datos tambien esta documentada en
`database/README.md`.
