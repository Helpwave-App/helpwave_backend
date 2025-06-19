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
| CI/CD         | GitHub Actions + Firebase App Distribution |

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
| LanguageProfile  |    PUT     |  /languageProfiles                | Actualizar una disponibilidad |
| LanguageProfile  |    GET     |  /languageProfiles   | Obtener todos los idiomas de todos los usuarios |
| LanguageProfile  |    GET     |  /languageProfiles/user/{idUser}  | Obtener un los idiomas de un usuario por el id de un usuario |
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
| Comments  |    POST    |  /comments | Agregar un comentario asociado a una videollamada |
| Comments  |    DELETE  |  /comments/{id}           | Eliminar un comentario por su id |
| Comments  |    GET     |  /comments/{id}           | Obtener un comentario por su id |
| Comments  |    GET     |  /comments          | Obtener todos los comentarios |
| Comments  |    PUT     |  /comments                | Actualizar un comentario |

---

## 🧪 Ejecución local

### 🔧 Requisitos previos

- Base de Datos de PostreSQL
- Archivo `firebase-service-account.json` en `src/main/resources/`
- Permisos para acceder a Agora y Firebase

---

## 🔁 Versionado automático

El proyecto sigue **Semantic Versioning** (`major.minor.patch`). El archivo `pubspec.yaml` y `CHANGELOG.md` se actualizan automáticamente mediante [GitHub Actions](https://github.com/features/actions).

### 🎛️ Lanzamiento manual desde GitHub Actions

1. Ir a la pestaña **Actions → Build & Distribute HelpWave**
2. Clic en **“Run workflow”**
3. Seleccionar:
   - `patch`, `minor`, o `major`
   - Ingresar notas de la versión

> El workflow:
> - Actualiza la versión
> - Edita el `CHANGELOG.md`
> - Crea un `git tag`
> - Compila el APK
> - Lo distribuye a Firebase App Distribution

---

## 🚀 Distribución de builds

### 📂 Script local para distribución manual

```bash
./scripts/deploy.sh "Notas de esta versión"
```

Este script compila el APK en modo release y lo distribuye automáticamente a Firebase App Distribution.

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

