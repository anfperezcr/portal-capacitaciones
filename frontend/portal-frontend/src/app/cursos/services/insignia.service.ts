import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Insignia } from '../models/insignia.model';

@Injectable({
  providedIn: 'root'
})
export class InsigniaService {

  private apiUrl = 'http://localhost:8080/api/v1/insignias';

  constructor(private http: HttpClient) {}

  crearInsignia(
    usuarioId: number,
    cursoId: number
  ): Observable<Insignia> {

    return this.http.post<Insignia>(this.apiUrl, {
      usuarioId,
      cursoId,
      imagen: 'medalla.png' // simple y fijo para la prueba
    });
  }

  listarInsignias(): Observable<Insignia[]> {
    return this.http.get<Insignia[]>(this.apiUrl);
  }
}
