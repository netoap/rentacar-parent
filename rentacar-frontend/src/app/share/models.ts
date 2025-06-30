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
  available: boolean;
  licensePlate: string;
  pricePerDay: number;
  category?: string;
  location?: string; 
}


export interface User {
  email: string;
  role: string;
}
export interface AuthResponse {
  token: string;
  user: User;
}

export interface Payment {
  reservationId: string;
  amount: number;
  status: string;
  paymentDate: string; // 👈 importante
  vehicle?: {
    model: string;
    licensePlate: string;
  };
  reservationPrice?: number;
}
