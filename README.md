# 🚗 Rent a Car

**Plataforma web de alquiler de vehículos** desarrollada como Trabajo Fin de Máster del Máster Universitario en Ingeniería Web (UPM). El sistema está basado en una arquitectura de microservicios con frontend en Angular y backend en Spring Boot, preparado para entornos reales mediante Docker y JWT.

---

## 📌 Características principales

### 👥 Usuarios
- Registro e inicio de sesión
- Búsqueda de vehículos disponibles
- Creación de reservas y cancelación
- Historial de reservas y pagos

### 👨‍💼 Administradores
- Gestión de flota (CRUD de vehículos)
- Gestión de usuarios (roles, bloqueo)
- Consulta global de reservas y transacciones

---

## ⚙️ Tecnologías utilizadas

| Tecnología        | Funcionalidad                           |
|------------------|------------------------------------------|
| **Angular 17**   | Frontend standalone + Angular Material   |
| **Spring Boot**  | Backend de microservicios                |
| **JWT**          | Autenticación y autorización             |
| **PostgreSQL**   | Base de datos relacional                 |
| **Docker**       | Contenerización y despliegue             |
| **Swagger**      | Documentación de APIs                    |
| **GitHub Actions** | CI/CD automático                       |

---

## 🧩 Estructura del Proyecto

```plaintext
rentacar-parent/
├── auth-service/
├── customer-service/
├── vehicle-service/
├── reservation-service/
├── payment-service/
├── api-gateway/
├── rentacar-commons/
└── rentacar-frontend/ (Angular)
```

---

## 🚀 Cómo ejecutar el proyecto

### Backend

```bash
cd rentacar-parent
docker-compose up --build
```

> Incluye PostgreSQL + todos los microservicios

### Frontend

```bash
cd rentacar-frontend
npm install
ng serve
```

---

## 🔐 Accesos por defecto

| Rol         | Usuario          | Contraseña |
|-------------|------------------|------------|
| Admin       | admin@demo.com   | admin123   |
| Cliente     | user@demo.com    | user123    |

> Puedes registrar nuevos usuarios desde `/register`.

---

## 📚 Documentación

- Swagger UI: http://localhost:{port}/swagger-ui.html
- Diagrama de arquitectura, secuencia y clases (ver `/docs`)

---

## 🛠️ Posibles mejoras futuras

- Integración con pasarelas de pago reales
- Estadísticas para administradores
- App móvil con Ionic o Flutter
- Soporte multilenguaje (i18n)

---

## 🧑 Autor

**Alexander Pinheiro Pires Neto**

Julio de 2025

---

## 📄 Licencia

Este proyecto se distribuye con fines académicos. Puedes utilizarlo como base para tus propios desarrollos educativos.
