import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../models/login-request.model';
import { LoginResponse } from '../models/login-response.model';
import { Observable, tap } from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/v1/auth/login';
  private STORAGE_KEY = 'usuario';

  constructor(
    private http: HttpClient,
    private router: Router
    ) {}



  login(data: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.apiUrl, data)
      .pipe(
        tap(usuario => {
          // 💾 Guardamos sesión simple
          localStorage.setItem(this.STORAGE_KEY, JSON.stringify(usuario));
        })
      );
  }

  logout(): void {
    localStorage.removeItem(this.STORAGE_KEY);
    this.router.navigate(['/login']);
  }

  getUsuario(): LoginResponse | null {
    const data = localStorage.getItem(this.STORAGE_KEY);
    return data ? JSON.parse(data) : null;
  }

  isAuthenticated(): boolean {
    return !!this.getUsuario();
  }

  getUsuarioId(): number | null {
    return this.getUsuario()?.id ?? null;
  }

  getRol(): string | null {
    return this.getUsuario()?.rol ?? null;
  }

  isAdmin(): boolean {
    return this.getRol() === 'ADMIN';
  }

  isUser(): boolean {
    return this.getRol() === 'USER';
  }


}
