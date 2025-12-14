export interface LoginResponse {
  id: number;
  username: string;
  nombre: string;
  rol: 'ADMIN' | 'USER';
}
