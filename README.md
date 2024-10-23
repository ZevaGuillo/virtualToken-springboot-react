# Virtual Token Service

## Descripción
El **Virtual Token Service** es una aplicación basada en Spring Boot y React que permite la gestión de tokens virtuales. Proporciona funcionalidades para generar, reclamar y listar tokens, lo que facilita el seguimiento de su uso y estado. Este servicio está diseñado para ser escalable y fácil de integrar con otras aplicaciones.

## Configuración del Proyecto
1. Clona este repositorio en tu máquina local.
2. Asegúrate de tener configurada tu base de datos en `application.properties`.
3. Ejecuta el proyecto con Maven:
   ```bash
   mvn spring-boot:run


## Funcionalidades
- **Generar Token**: Permite crear un nuevo token asociado a un cliente.
- **Usar Token**: Facilita el reclamo de un token por parte de un cliente.
- **Ver Tokens**: Proporciona una lista paginada de todos los tokens generados, con la opción de filtrar por cliente, fechas y estado.

## Autenticación
La aplicación utiliza **JWT (JSON Web Tokens)** para la autenticación de usuarios. JWT permite la verificación segura de la identidad del usuario y la protección de los endpoints de la API. Al iniciar sesión, se genera un token que debe ser enviado en las cabeceras de las solicitudes para acceder a los recursos protegidos.

## Documentación de la API
La documentación de la API está disponible a través de Swagger UI. Puedes acceder a ella mediante la siguiente URL:

[Swagger UI](http://localhost:8080/api/v1/swagger-ui/index.html)

## Estructura del Proyecto
### Controlador `TokenController`
Este controlador expone los siguientes endpoints:

- **Generar Token**
  - **Método**: `GET`
  - **Ruta**: `/api/v1/token/generarToken`
  - **Parámetros**:
    - `cliente`: ID del cliente para el cual se genera el token (requerido).
  - **Respuesta**: Retorna un objeto `TokenDto` con el token generado.

- **Usar Token**
  - **Método**: `POST`
  - **Ruta**: `/api/v1/token/usarToken`
  - **Parámetros**:
    - `cliente`: ID del cliente que reclama el token (requerido).
    - `token`: El token que se va a reclamar (requerido).
  - **Respuesta**: Retorna un booleano indicando si el reclamo fue exitoso.

- **Ver Tokens**
  - **Método**: `GET`
  - **Ruta**: `/api/v1/token/all`
  - **Parámetros** (opcionales):
    - `token`: El token a buscar.
    - `startDate`: Fecha de inicio para filtrar los tokens.
    - `endDate`: Fecha de fin para filtrar los tokens.
    - `status`: Estado del token (por defecto: "all").
    - `pageable`: Información de paginación.
  - **Respuesta**: Devuelve una página de objetos `TokenDto` que cumplen con los filtros especificados.

### Controlador `AuthController`
Este controlador expone los siguientes endpoints para la autenticación de usuarios:

- **Registrar Usuario**
  - **Método**: `POST`
  - **Ruta**: `/api/v1/auth/register`
  - **Cuerpo**:
    - `authCreateUser`: Objeto que contiene la información del nuevo usuario (requerido).
  - **Respuesta**: Retorna un objeto `AuthResponse` con información sobre el usuario registrado y un estado `201 Created`.

- **Iniciar Sesión**
  - **Método**: `POST`
  - **Ruta**: `/api/v1/auth/login`
  - **Cuerpo**:
    - `userRequest`: Objeto que contiene las credenciales del usuario (requerido).
  - **Respuesta**: Retorna un objeto `AuthResponse` con el token JWT y un estado `200 OK`.


