import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';

import { AuthService } from '../auth/services/auth.service';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { NgIf } from '@angular/common';


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    RouterOutlet,   // ✅ NECESARIO para <router-outlet>
    RouterLink,     // ✅ NECESARIO para routerLink

    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    NgIf
  ],
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent {

  constructor(private authService: AuthService
    ) {}

  esAdmin(): boolean {
    return this.authService.getRol() === 'ADMIN';
  }

  logout() {
      this.authService.logout();


  }

  irAGithub() {
    window.open(
      'https://github.com/anfperezcr/portal-capacitaciones',
      '_blank'
    );
  }

  getNombreUsuario(): string {
    return this.authService.getUsuario()?.nombre ?? '';
  }


}
