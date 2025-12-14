import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Curso } from '../models/curso.model';

@Injectable({
  providedIn: 'root'
})
export class CursoService {

  private apiUrl = 'http://localhost:8080/api/v1/cursos';

  constructor(private http: HttpClient) {}

  listarCursos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(this.apiUrl);
  }

  crearCurso(curso: Curso) {
    return this.http.post<Curso>(this.apiUrl, curso);
  }

  actualizarCurso(id: number, curso: Curso) {
    return this.http.put<Curso>(`${this.apiUrl}/${id}`, curso);
  }

  eliminarCurso(id: number) {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


}
