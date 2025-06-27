
import { jwtDecode } from 'jwt-decode';

export interface JwtPayload {
  sub: string;
  role?: string;
  roles?: string[];
  exp?: number;
}

export function decodeToken(token: string): JwtPayload | null {
  try {
    return jwtDecode<JwtPayload>(token);
  } catch (err) {
    console.error('Invalid token', err);
    return null;
  }
}
