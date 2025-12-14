import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CursoService } from '../../../cursos/services/curso.service';
import { Curso } from '../../../cursos/models/curso.model';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';

import { RouterLink } from '@angular/router';


@Component({
  selector: 'app-admin-cursos-page',
  standalone: true,
  imports: [CommonModule,
            MatButtonModule,
            MatCardModule,
            RouterLink],
  templateUrl: './admin-cursos-page.component.html'
})
export class AdminCursosPageComponent implements OnInit {

  cursos: Curso[] = [];

  constructor(private cursoService: CursoService) {}

  ngOnInit(): void {
    this.cargarCursos();
  }

  cargarCursos() {
    this.cursoService.listarCursos()
      .subscribe(cursos => this.cursos = cursos);
  }

  eliminarCurso(id: number) {
    if (!confirm('¿Eliminar curso?')) return;

    this.cursoService.eliminarCurso(id)
      .subscribe(() => this.cargarCursos());
  }
}
