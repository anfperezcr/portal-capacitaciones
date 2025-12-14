import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';

import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';

import { AuthService } from '../auth/services/auth.service';


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    RouterOutlet,   // ✅ NECESARIO para <router-outlet>
    RouterLink,     // ✅ NECESARIO para routerLink

    MatSidenavModule,
    MatToolbarModule,
    MatListModule
  ],
  templateUrl: './layout.component.html'
})
export class LayoutComponent {

  constructor(private authService: AuthService
    ) {}

  esAdmin(): boolean {
    return this.authService.getRol() === 'ADMIN';
  }




  }
