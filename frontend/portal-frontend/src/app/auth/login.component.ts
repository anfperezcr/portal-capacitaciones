import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  username = '';
  password = '';
  error = '';


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
      this.authService.login({
        username: this.username,
        password: this.password
      }).subscribe({
        next: () => {
          this.router.navigate(['/cursos']);
        },
        error: () => {
          this.error = 'Credenciales inválidas';
        }
      });
    }
}
