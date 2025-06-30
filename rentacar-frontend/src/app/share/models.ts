export interface Reservation {
  id: number;
  customerId: string;
  carId: number;
  vehicleModel: string; // nuevo campo
  startDate: string;
  endDate: string;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED';
}



export interface Vehicle {
  id: number;
  model: string;
  year: number;
  price: number;
  available: boolean;
}

export interface User {
  email: string;
  role: string;
}
export interface AuthResponse {
  token: string;
  user: User;
}