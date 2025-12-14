import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login.component';
import { LayoutComponent } from './layout/layout.component';
import { authGuard } from './auth/auth.guard';

import { CursosPageComponent } from './cursos/pages/cursos-page.component';

import { PerfilPageComponent } from './perfil/pages/perfil-page.component';

import { adminGuard } from './auth/guards/admin.guard';

import { AdminCursosPageComponent } from './admin/cursos/pages/admin-cursos-page.component';

import { CrearCursoPageComponent } from './admin/cursos/pages/crear-curso-page.component';



export const routes: Routes = [

  // 🔓 Login
  {
    path: 'login',
    component: LoginComponent
  },

  // 🔐 Área protegida
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [

      // 📚 Cursos usuario
      {
        path: 'cursos',
        component: CursosPageComponent
      },

      // 👤 Perfil
      {
        path: 'perfil',
        component: PerfilPageComponent
      },

      // 🛠 ADMIN
      {
        path: 'admin',
        canActivate: [adminGuard],
        children: [

          {
            path: 'cursos',
            component: AdminCursosPageComponent
          },

          {
            path: 'cursos/nuevo',
            component: CrearCursoPageComponent
          },

          {
            path: 'cursos/editar/:id',
            component: CrearCursoPageComponent
          }
        ]
      },

      // default
      {
        path: '',
        redirectTo: 'cursos',
        pathMatch: 'full'
      }
    ]
  },

  // fallback
  {
    path: '**',
    redirectTo: 'login'
  }
];
