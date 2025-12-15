import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';

import { CursoService } from '../services/curso.service';
import { ProgresoService } from '../services/progreso.service';
import { InsigniaService } from '../services/insignia.service';

import { Curso } from '../models/curso.model';
import { Progreso } from '../models/progreso.model';
import { Insignia } from '../models/insignia.model';

import { AuthService } from '../../auth/services/auth.service';


@Component({
  selector: 'app-cursos-page',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './cursos-page.component.html'
})
export class CursosPageComponent implements OnInit {

  cursosPorModulo: Record<string, Curso[]> = {};
  progresosPorCurso: Record<number, Progreso> = {};
  insigniasPorCurso: Record<number, Insignia> = {};

  // 🔴 Simulado por ahora (vendrá del login)
  usuarioId!: number;

  constructor(
    private cursoService: CursoService,
    private progresoService: ProgresoService,
    private insigniaService: InsigniaService,
    private snackBar: MatSnackBar,   // ✅ CORRECTO
    private authService: AuthService
  ) {}

  ngOnInit(): void {

    const usuario = this.authService.getUsuario();

    if (!usuario) {
      return;
    }

    this.usuarioId = usuario.id;

    // 1️⃣ Cursos
    this.cursoService.listarCursos().subscribe(cursos => {
      this.cursosPorModulo = this.agruparPorModulo(cursos);
    });

    // 2️⃣ Progresos SOLO del usuario logueado
    this.progresoService.listarProgresos().subscribe(progresos => {
      progresos
        .filter(p => p.usuarioId === this.usuarioId)
        .forEach(p => {
          this.progresosPorCurso[p.cursoId] = p;
        });
    });

    // 3️⃣ Insignias SOLO del usuario logueado
    this.insigniaService.listarInsignias().subscribe(insignias => {
      insignias
        .filter(i => i.usuarioId === this.usuarioId)
        .forEach(i => {
          this.insigniasPorCurso[i.cursoId] = i;
        });
    });
  }


  //metodo para iniciar curso, cuando no hay progreso en DB
      iniciarCurso(cursoId: number) {

        this.progresoService.crearProgreso({
          usuarioId: this.usuarioId,
          cursoId: cursoId,
          estado: 'INICIADO'
        }).subscribe(progreso => {

          this.progresosPorCurso[cursoId] = progreso;

        });
      }

  marcarCompletado(cursoId: number) {

    const progreso = this.progresosPorCurso[cursoId];

    if (!progreso) {
      alert('El curso no tiene progreso asociado');
      return;
    }

    this.progresoService
      .actualizarEstado(progreso.id, 'COMPLETADO')
      .subscribe(() => {

        progreso.estado = 'COMPLETADO';

        // 🏅 Crear insignia
        this.insigniaService
          .crearInsignia(this.usuarioId, cursoId)
          .subscribe(insignia => {

            this.insigniasPorCurso[cursoId] = insignia;

            // 🎉 Feedback visual
            this.snackBar.open(
              '🎉 ¡Curso completado! Has ganado una insignia',
              'Cerrar',
              { duration: 4000 }
            );
          });
      });
  }

  private agruparPorModulo(cursos: Curso[]): Record<string, Curso[]> {
    return cursos.reduce((acc, curso) => {
      acc[curso.modulo] = acc[curso.modulo] || [];
      acc[curso.modulo].push(curso);
      return acc;
    }, {} as Record<string, Curso[]>);
  }
}
