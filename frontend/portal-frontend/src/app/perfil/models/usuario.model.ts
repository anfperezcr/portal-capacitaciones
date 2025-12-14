export interface Usuario {
  id: number;
  username: string;
  nombre: string;
  rol: 'ADMIN' | 'USER';
}
