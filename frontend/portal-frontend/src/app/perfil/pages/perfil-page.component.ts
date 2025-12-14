import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../services/usuario.service';
import { ProgresoService } from '../../cursos/services/progreso.service';
import { InsigniaService } from '../../cursos/services/insignia.service';

import { Usuario } from '../models/usuario.model';
import { Progreso } from '../../cursos/models/progreso.model';
import { Insignia } from '../../cursos/models/insignia.model';

import { AuthService } from '../../auth/services/auth.service';

import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';


@Component({
  selector: 'app-perfil-page',
  standalone: true,
  imports: [CommonModule,
            RouterLink,
            MatButtonModule],
  templateUrl: './perfil-page.component.html'
})
export class PerfilPageComponent implements OnInit {

  usuario!: Usuario;
  progresos: Progreso[] = [];
  insignias: Insignia[] = [];

  // 🔴 Temporal (luego vendrá del login)
  usuarioId!: number;


  constructor(
    private usuarioService: UsuarioService,
    private progresoService: ProgresoService,
    private insigniaService: InsigniaService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    const usuarioLogueado = this.authService.getUsuario();

    if (!usuarioLogueado) {
      return;
    }

    this.usuarioId = usuarioLogueado.id;

    // 👤 Perfil
    this.usuarioService.obtenerPorId(this.usuarioId)
      .subscribe(usuario => this.usuario = usuario);

    // 📚 Historial
    this.progresoService.listarProgresos()
      .subscribe(progresos => {
        this.progresos = progresos.filter(
          p => p.usuarioId === this.usuarioId
        );
      });

    // 🏅 Insignias
    this.insigniaService.listarInsignias()
      .subscribe(insignias => {
        this.insignias = insignias.filter(
          i => i.usuarioId === this.usuarioId
        );
      });
  }

  esAdmin(): boolean {
    return this.authService.getRol() === 'ADMIN';
  }


}
