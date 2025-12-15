import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Progreso } from '../models/progreso.model';

@Injectable({
  providedIn: 'root'
})
export class ProgresoService {

  private apiUrl = 'http://localhost:8080/api/v1/progresos';

  constructor(private http: HttpClient) {}

  actualizarEstado(
    progresoId: number,
    estado: 'INICIADO' | 'COMPLETADO'
  ): Observable<Progreso> {
    return this.http.put<Progreso>(
      `${this.apiUrl}/${progresoId}`,
      { estado }
    );
  }

  listarProgresos(): Observable<Progreso[]> {
    return this.http.get<Progreso[]>(this.apiUrl);
  }

  crearProgreso(data: {
    usuarioId: number;
    cursoId: number;
    estado: 'INICIADO' | 'COMPLETADO';
  }) {
    return this.http.post<Progreso>(this.apiUrl, data);
  }


}
