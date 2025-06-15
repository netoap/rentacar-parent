export interface JwtPayload {
  sub: string;   // user's email
  role: string;  // user's role
  exp?: number;  // optional: expiration timestamp
}

export function decodeToken(token: string): JwtPayload | null {
  try {
    const payload = token.split('.')[1];
    const decoded = atob(payload);
    return JSON.parse(decoded);
  } catch {
    return null;
  }
}
