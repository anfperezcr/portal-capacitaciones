export interface Curso {
  id?: number;
  nombre: string;
  descripcion: string;
  modulo: 'FULLSTACK' | 'APIS' | 'CLOUD' | 'DATA';
  activo?: boolean;
}
