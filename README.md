# 📱 HelpWave

**Aplicación móvil de microvoluntariado digital basada en videollamadas para brindar asistencia remota, especialmente a personas adultas mayores.**

HelpWave conecta a personas que requieren asistencia rápida con voluntarios disponibles mediante videollamadas, permitiendo resolver tareas cotidianas y técnicas de forma remota, segura y solidaria.

---

## 🚀 Funcionalidades principales

- 🔐 Registro e inicio de sesión con Firebase
- 🆘 Solicitud de ayuda categorizada
- 📲 Notificaciones push a voluntarios
- 📹 Videollamadas integradas (Agora)
- ✅ Evaluación posterior a la llamada
- 🌟 Sistema de niveles por desempeño
- 🌐 Gestión de idiomas y disponibilidad
- 📊 Reporte de videollamadas
- 🎨 Interfaz multilingüe (Easy Localization)
- 🌓 Soporte de tema claro/oscuro

---

## 🧰 Tecnologías utilizadas

| Módulo        | Tecnología                                 |
|---------------|--------------------------------------------|
| Frontend      | Flutter 3.x, Dart                          |
| Estado        | Riverpod + StateNotifier                   |
| Backend       | Spring Boot, Java, PostgreSQL              |
| Comunicación  | Firebase Auth, Firestore, Cloud Messaging  |
| Videollamadas | Agora Video SDK                            |
| Despliegue    | Render |

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

- Base de Datos de PostreSQL
- Archivo `firebase-service-account.json` en `src/main/resources/`
- Permisos para acceder a Agora y Firebase

---

### 👥 Testers actuales

- elvia.arteaga98@gmail.com  
- cuchcafabrizzio@gmail.com

---

## 👤 Autores

| Nombre           | Rol                       |
|------------------|---------------------------|
| Elvi Arteaga     | Arquitectura, Flutter Dev |
| Fabrizzio Cuchca | Backend & DevOps Engineer |

---

## 📜 Licencia

Este proyecto se distribuye bajo licencia **MIT**.  
Uso permitido con fines educativos, de investigación o sin fines de lucro.

---

## 🧩 Créditos y agradecimientos

Proyecto desarrollado como parte del trabajo de investigación para el título profesional en Ingeniería de Software.  
Inspirado por iniciativas de microvoluntariado digital.

🔽 [Descargar última versión APK](https://github.com/Helpwave-App/helpwave_mobile_app/releases/latest/download/app-release.apk)
🔽 [Backend en Render](https://helpwave-backend.onrender.com)

