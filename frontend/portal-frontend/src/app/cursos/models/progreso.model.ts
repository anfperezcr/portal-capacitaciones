export interface Progreso {
  id: number;
  estado: 'INICIADO' | 'COMPLETADO';
  cursoId: number;
  usuarioId: number;
}
