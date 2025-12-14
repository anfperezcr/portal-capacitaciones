export interface Insignia {
  id: number;
  imagen: string;
  fechaOtorgada: string;
  usuarioId: number;
  cursoId: number;
  cursoNombre?: string; // 👈 opcional, útil para mostrar
}
