export const ROUTES_PATH = {
  ROOT: '',
  LOGIN: 'login',
  REGISTER: 'register',
  DASHBOARD: 'dashboard',
  DASHBOARD_VEHICLES: 'vehicles',
  RESERVE: 'reserve/:vehicleId',
  MY_RESERVATIONS: 'my-reservations',
  RESERVATION_SUMMARY: 'reservation-summary',
  VEHICLE_CALENDAR: 'vehicles/:vehicleId/calendar',

  ADMIN: 'admin',
  ADMIN_DASHBOARD: 'dashboard',
  ADMIN_VEHICLES: 'vehicles',
  ADMIN_CREATE_VEHICLE: 'create-vehicle',
  ADMIN_EDIT_VEHICLE: 'edit-vehicle/:id',
  ADMIN_RESERVATIONS: 'reservations',
  PAYMENT: 'payment',

  UNAUTHORIZED: 'unauthorized'
} as const;
