# 🚗 Rent a Car App – Frontend

Aplicación Angular moderna para la gestión de reservas de vehículos. Utiliza componentes standalone, enrutamiento centralizado y layouts separados por rol (`USER` y `ADMIN`).

---

## 📁 Estructura del proyecto

```
src/
├── admin/               # Panel administrativo (vehículos, reservas)
├── auth/                # Autenticación, guards, y JWT
├── dashboard/           # Panel del usuario autenticado
├── reservation/         # Formularios y vistas de reservas
├── vehicle/             # Calendario y listado de vehículos
├── routing/             # Constantes centralizadas de rutas
├── errors/              # Componentes de error (404, 403)
├── app.routes.ts        # Declaración principal de rutas
├── app.config.ts        # Configuración de arranque standalone
```

---

## 🧭 Navegación y rutas

- Declaradas en `app.routes.ts`
- Centralizadas con `ROUTES_PATH` en `routing/app.routes.constants.ts`

📄 Consulta [`ROUTES.md`](./ROUTES.md) para el listado completo de rutas por rol.

---

## 🛡️ Seguridad y layout

- `authGuard`: protege rutas autenticadas
- `roleGuard`: restringe rutas de administración
- `UserLayoutComponent`: layout para `/dashboard`
- `AdminLayoutComponent`: layout para `/admin`

---

## 🚀 Tecnologías utilizadas

- Angular 16+ con Standalone API
- Angular Material
- JWT + guards personalizados
- Lazy loading con `loadComponent()`
- Diseño limpio y responsive

---

## ✅ Buenas prácticas aplicadas

- Rutas centralizadas: `ROUTES_PATH`
- Organización modular por dominio (`admin`, `auth`, `dashboard`, etc.)
- Componentes reutilizables y layouts separados
- Importación explícita de Angular Material por componente

---

## 🧪 Cómo iniciar el proyecto

```bash
npm install
ng serve
```

---

## ✍️ Para contribuir

1. Usa constantes desde `ROUTES_PATH` al agregar rutas nuevas.
2. Coloca los componentes según su contexto (`admin/`, `dashboard/`, etc.).
3. Si agregas una ruta nueva, actualiza también [`ROUTES.md`](./ROUTES.md).

---

## 📄 Documentación de rutas

Consulta el archivo [`ROUTES.md`](./ROUTES.md) para ver todas las rutas disponibles por rol de usuario.

---
