# 📘 Rutas de la Aplicación (ROUTES.md)

## 🌐 Público

| Ruta        | Descripción                 |
|-------------|-----------------------------|
| `/`         | Página de inicio (Landing)  |
| `/login`    | Iniciar sesión              |
| `/register` | Crear cuenta                |

---

## 👤 Usuario autenticado (`USER`)

Protegido por `authGuard` y renderizado dentro de `UserLayoutComponent`.

| Ruta                                     | Descripción                                     |
|------------------------------------------|-------------------------------------------------|
| `/dashboard`                             | Panel principal del usuario                     |
| `/dashboard/vehicles`                    | Listado de vehículos disponibles                |
| `/dashboard/reserve/:vehicleId`          | Formulario para reservar un vehículo            |
| `/dashboard/my-reservations`             | Lista de reservas del usuario                   |
| `/dashboard/reservation-summary`         | Resumen de la última reserva                    |
| `/dashboard/vehicles/:vehicleId/calendar`| Calendario de disponibilidad por vehículo       |

---

## 🛠️ Administrador (`ADMIN`)

Protegido por `authGuard` + `roleGuard`, renderizado dentro de `AdminLayoutComponent`.

| Ruta                          | Descripción                                |
|-------------------------------|--------------------------------------------|
| `/admin/dashboard`            | Panel principal del administrador          |
| `/admin/vehicles`             | Gestión de vehículos                       |
| `/admin/create-vehicle`       | Crear nuevo vehículo                       |
| `/admin/edit-vehicle/:id`     | Editar vehículo existente                  |
| `/admin/reservations`         | Ver y administrar todas las reservas       |

---

## ❌ Rutas de error

| Ruta            | Descripción                      |
|------------------|----------------------------------|
| `/unauthorized` | Usuario sin permisos suficientes |
| `**`            | Página no encontrada (404)       |

---

## 🧩 Notas

- Todas las rutas están centralizadas en `app.routes.constants.ts` a través de `ROUTES_PATH`.
- Las rutas son utilizadas en `app.routes.ts` con `standalone` components.
- La navegación condicional por rol se realiza mediante `authGuard` y `roleGuard`.

