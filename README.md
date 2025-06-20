# 📱 HelpWave Backend

**Aplicación móvil de microvoluntariado digital basada en videollamadas para brindar asistencia remota, especialmente a personas adultas mayores.**

HelpWave conecta a personas que requieren asistencia rápida con voluntarios disponibles mediante videollamadas, permitiendo resolver tareas cotidianas y técnicas de forma remota, segura y solidaria.

Este repositorio contiene la API desarrollada en Spring Boot que proporciona los servicios fundamentales para la operación de la aplicación móvil, gestionando autenticación, perfiles, habilidades, disponibilidad, sesiones de ayuda y comentarios.

---

## 🚀 Funcionalidades principales

- 🔐 Autenticación con JWT
- 👤 Gestión de usuarios, roles y perfiles
- 🎯 Asignación y administración de habilidades
- 📅 Control de disponibilidad de voluntarios
- 📞 Registro de sesiones de ayuda y motivos de reporte
- ⭐ Evaluación posterior a la videollamada
- 🌍 Gestión de idiomas por perfil
- 📲 Manejo de tokens de dispositivo para notificaciones push

---

## 🧰 Tecnologías utilizadas

| Componente        | Tecnología            |
|-------------------|-----------------------|
| Framework         | Spring Boot           |
| Lenguaje          | Java 17               |
| Base de datos     | PostgreSQL            |
| Seguridad         | Spring Security + JWT |
| ORM               | Spring Data JPA       |
| Despliegue        | Render + Docker       |
| Documentación API | Postman               |

---

## 🔗 Endpoints

| Controlador   |   Método   |             Endpoint            |                     Descripción                     |
|---------------|------------|---------------------------------|-----------------------------------------------------|
| Availability  |    POST    |  /availabilities                | Agregar una disponibilidad a un usuario |
| Availability  |    DELETE  |  /availabilities/{id}           | Eliminar una disponibilidad por su id |
| Availability  |    GET     |  /availabilities/{id}           | Obtener una disponibilidad por su id |
| Availability  |    POST    |  /availabilities/batch          | Agregar más de una disponibilidad a un usuario |
| Availability  |    PUT     |  /availabilities                | Actualizar una disponibilidad |
| Availability  |    PUT     |  /availabilities/profile/{id}   | Actualizar la disponibilidad por el id de un usuario |
| Availability  |    GET     |  /availabilities/user/{idUser}  | Obtener una disponibilidad por el id de un usuario |
| Comments  |    POST    |  /comments                      | Agregar un comentario asociado a una videollamada |
| Comments  |    DELETE  |  /comments/{id}           | Eliminar un comentario por su id |
| Comments  |    GET     |  /comments/{id}           | Obtener un comentario por su id |
| Comments  |    GET     |  /comments          | Obtener todos los comentarios |
| Comments  |    PUT     |  /comments                | Actualizar un comentario |
| Device  |    POST    |  /devices | Agregar un dispositivo |
| Device  |    DELETE  |  /devices/{id}           | Eliminar un comentario por su id |
| Device  |    GET     |  /devices/{id}           | Obtener un dispositivo por su id |
| Device  |    GET     |  /devices          | Obtener todos los dispositivos |
| Device  |    PUT     |  /devices                | Actualizar un dispositivo |
| Device  |    POST    |  /devices/upsert | Agregar o editar un dispositivo dependiendo si el token ingresado existe o no |
| Empairing  |    POST    |  /empairings | Agregar un emparejamiento |
| Empairing  |    DELETE  |  /empairings/{id}           | Eliminar un emparejamiento por su id |
| Empairing  |    GET     |  /empairings/{id}           | Obtener un emparejamiento por su id |
| Empairing  |    GET     |  /empairings          | Obtener todos los emparejamientos |
| Empairing  |    PUT     |  /empairings                | Actualizar un emparejamiento |
| Empairing  |    POST    |  /empairings/accept/{empairingId} | Aceptar un emparejamiento por su id para generar una videollamada |
| JwtAuthentication  |    POST     |  /authenticate                | Iniciar Sesión con un nombre de usuario y contraseña |
| Language  |    POST    |  /languages | Agregar un idioma |
| Language  |    DELETE  |  /languages/{id}           | Eliminar un idioma por su id |
| Language  |    GET     |  /languages/{id}           | Obtener un idioma por su id |
| Language  |    GET     |  /languages          | Obtener todos los idiomas |
| Language  |    PUT     |  /languages                | Actualizar un idioma |
| LanguageProfile  |    POST    |  /languageProfiles                | Agregar un idioma a un usuario |
| LanguageProfile  |    DELETE  |  /languageProfiles/{id}           | Eliminar un idioma asociado a un usuario por su id |
| LanguageProfile  |    GET     |  /languageProfiles/{id}           | Obtener el idioma de un usuario por su id |
| LanguageProfile  |    POST    |  /languageProfiles/batch          | Agregar más de un idioma a un usuario |
| LanguageProfile  |    PUT     |  /languageProfiles                | Actualizar un idioma de un usuario |
| LanguageProfile  |    GET     |  /languageProfiles   | Obtener todos los idiomas de todos los usuarios |
| LanguageProfile  |    GET     |  /languageProfiles/user/{idUser}  | Obtener todos los idiomas de un usuario por su id |
| LanguageProfile  |    PUT     |  /languageProfiles/profile/{id}  | Actualizar los idiomas de un usuario por su id |
| Level  |    GET     |  /levels/progress/{idProfile} | Obtener el progreso de un usuario por su id |
| Level  |    POST    |  /levels          | Agregar un nivel |
| Level  |    DELETE  |  /levels/{id}           | Eliminar un nivel por su id |
| Level  |    GET     |  /levels/{id}           | Obtener un nivel por su id |
| Level  |    GET     |  /levels          | Obtener todos los niveles |
| Level  |    PUT     |  /levels                | Actualizar un nivel |
| Profile  |    POST    |  /profiles          | Agregar un perfil asociado a un usuario |
| Profile  |    DELETE  |  /profiles/{id}           | Eliminar un perfil por su id |
| Profile  |    GET     |  /profiles/{id}           | Obtener un perfil por su id |
| Profile  |    GET     |  /profiles          | Obtener todos los perfiles |
| Profile  |    PATCH   |  /profiles/{id}          | Actualizar parcialmente un perfil |
| Reports  |    POST    |  /reports | Agregar un reporte asociado a una videollamada |
| Reports  |    DELETE  |  /reports/{id}           | Eliminar un reporte por su id |
| Reports  |    GET     |  /reports/{id}           | Obtener un reporte por su id |
| Reports  |    GET     |  /reports          | Obtener todos los reportes |
| Reports  |    PUT     |  /reports                | Actualizar un reporte |
| Request  |    POST    |  /requests | Agregar una solicitud para generar un emparejamiento y enviar notificaciones a voluntarios |
| Request  |    DELETE  |  /requests/{id}           | Eliminar una solicitud por su id |
| Request  |    GET     |  /requests/{id}           | Obtener una solicitud por su id |
| Request  |    GET     |  /requests          | Obtener todos las solicitudes |
| Request  |    PUT     |  /requests                | Actualizar una solicitud |
| Request  |    PUT     |  /requests/cancel/{idRequest} | Cancelar una solicitud para que no pueda ser contestada |
| Role  |    POST    |  /roles | Agregar un rol |
| Role  |    DELETE  |  /roles/{id}           | Eliminar un rol por su id |
| Role  |    GET     |  /roles/{id}           | Obtener un rol por su id |
| Role  |    GET     |  /roles          | Obtener todos los roles |
| Role  |    PUT     |  /roles                | Actualizar un rol |
| Skill  |    POST    |  /skills | Agregar una habilidad |
| Skill  |    DELETE  |  /skills/{id}           | Eliminar una habilidad por su id |
| Skill  |    GET     |  /skills/{id}           | Obtener una habilidad por su id |
| Skill  |    GET     |  /skills          | Obtener todas las habilidades |
| Skill  |    PUT     |  /skills                | Actualizar una habilidad |
| SkillProfile  |    POST    |  /skillProfiles                | Agregar una habilidad a un usuario |
| SkillProfile  |    DELETE  |  /skillProfiles/{id}           | Eliminar una habilidad asociado a un usuario por su id |
| SkillProfile  |    GET     |  /skillProfiles/{id}           | Obtener una habilidad de un usuario por el id |
| SkillProfile  |    POST    |  /skillProfiles/batch          | Agregar más de una habilidad a un usuario |
| SkillProfile  |    PUT     |  /skillProfiles                | Actualizar una habilidad |
| SkillProfile  |    GET     |  /skillProfiles   | Obtener todas las habilidades de todos los usuarios |
| SkillProfile  |    GET     |  /skillProfiles/user/{idUser}  | Obtener todas las habilidades de un usuario por su id |
| SkillProfile  |    PUT     |  /skillProfiles/profile/{id}  | Actualizar las habilidades de un usuario por su id |
| StateReport  |    POST    |  /stateReports | Agregar un estado de reporte |
| StateReport  |    DELETE  |  /stateReports/{id}           | Eliminar un estado de reporte por su id |
| StateReport  |    GET     |  /stateReports/{id}           | Obtener un estado de reporte por su id |
| StateReport  |    GET     |  /stateReports          | Obtener todos los estados de reporte |
| StateReport  |    PUT     |  /stateReports                | Actualizar un estado de reporte |
| TypeReport  |    POST    |  /typeReports | Agregar un tipo de reporte |
| TypeReport  |    DELETE  |  /typeReports/{id}           | Eliminar un tipo de reporte por su id |
| TypeReport  |    GET     |  /typeReports/{id}           | Obtener un tipo de reporte por su id |
| TypeReport  |    GET     |  /typeReports          | Obtener todos los tipos de reporte |
| TypeReport  |    PUT     |  /typeReports                | Actualizar un tipo de reporte |
| User  |    POST    |  /user/register          | Agregar un usuario y un perfil asociado |
| User  |    DELETE  |  /user/{id}           | Eliminar un usuario por su id |
| User  |    GET     |  /user/check-username | Verificar un usuario por su nombre de usuario |
| User  |    GET     |  /user/{id}           | Obtener un usuario por su id |
| User  |    GET     |  /user/list          | Obtener todos los usuarios |
| User  |    PUT   |  /user/{id}          | Actualizar un usuario |
| Videocall  |    GET     |  /videocalls/empairings/{id} | Obtener una videollamada por su id de emparejamiento |
| Videocall  |    POST    |  /videocalls | Agregar una videollamada |
| Videocall  |    DELETE  |  /videocalls/{id}           | Eliminar una videollamada por su id |
| Videocall  |    GET     |  /videocalls/{id}           | Obtener una videollamada por su id |
| Videocall  |    GET     |  /videocalls          | Obtener todos las videollamadas |
| Videocall  |    PUT     |  /videocalls                | Actualizar una videollamada |
| Videocall  |    PUT     |  /videocalls/end | Finalizar una videollamada para ambos usuarios conectados por el canal |

---

## 🧪 Ejecución local

### 🔧 Requisitos previos

- Java 17+
- PostgreSQL
- Maven
- Archivo firebase-service-account.json en src/main/resources/
- Permisos para acceder a Agora y Firebase

### ▶️ Pasos de instalación

1. Clonar el repositorio:

```bash
git clone https://github.com/Helpwave-App/helpwave_backend.git
cd helpwave_backend
```

2. Configurar variables de entorno (usadas en `application.properties`):

```env
DB_URL=jdbc:postgresql://localhost:5432/helpwave
DB_USERNAME=tu_usuario
DB_PASSWORD=tu_contraseña
PORT=8080
JWT_SECRET=tu_clave_secreta
AGORA_APP_ID=tu_app_id_de_agora
AGORA_APP_CERTIFICATE=tu_app_certificate_de_agora
```

3. Crear la base de datos:

```sql
CREATE DATABASE helpwave;
```

4. Levantar la aplicación:

```bash
./mvnw spring-boot:run
```

5. Ejecutar el script de inicialización de datos:

🔗 [HWAPP-Initial_Script-v.1.0.sql](./docs/HWAPP-Initial_Script-v.1.0.sql)


---

## 📄 Configuración de propiedades

El backend utiliza `application.properties` para gestionar la configuración, incluyendo:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
application.security.jwt.expiration=86400000
application.security.jwt.refresh-token.expiration=604800000

agora.app-id=${AGORA_APP_ID}
agora.app-certificate=${AGORA_APP_CERTIFICATE}
```

---

## 🐳 Despliegue en Render

El despliegue en producción se realiza mediante **Docker** y Render, apuntando a la rama `master`.

### 📁 Dockerfile incluido

El archivo `Dockerfile` está configurado para compilar la aplicación y exponer el puerto configurado mediante la variable `${PORT}` (por defecto: `8080`).

Render se encarga de:

- Construir la imagen a partir del `Dockerfile`
- Ejecutar la aplicación con las variables de entorno necesarias
- Desplegar automáticamente en cada _push_ a `master`

---

## 📄 Documentación de la API

La documentación completa de los endpoints se realizó en Postman. La colección se encuentra disponible en el siguiente archivo:

🔗 [HelpWave.postman_collection.json](./docs/HelpWave.postman_collection.json)

---

## 👤 Autores

| Nombre           | Rol                       |
|------------------|---------------------------|
| Elvia Arteaga    | Arquitectura, Flutter Dev |
| Fabrizzio Cuchca | Backend & DevOps Engineer |

---

## 📜 Licencia

Este proyecto se distribuye bajo licencia **MIT**.  
Uso permitido con fines educativos, de investigación o sin fines de lucro.

---

## 🧩 Créditos y agradecimientos

Este backend forma parte del ecosistema **HelpWave**, una solución digital desarrollada como parte del trabajo de investigación para la obtención del título profesional en Ingeniería de Software e Ingeniería de Sistemas.

🔽 [Descargar última versión APK](https://github.com/Helpwave-App/helpwave_mobile_app/releases/latest/download/app-release.apk)
🔽 [Backend en Render](https://helpwave-backend.onrender.com)

