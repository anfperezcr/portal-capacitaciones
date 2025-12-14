import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { CursoService } from '../../../cursos/services/curso.service';
import { Curso } from '../../../cursos/models/curso.model';
import { MatSelectModule } from '@angular/material/select';


@Component({
  selector: 'app-crear-curso-page',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule
  ],
  templateUrl: './crear-curso-page.component.html'
})
export class CrearCursoPageComponent implements OnInit {

  curso: Curso = {
    nombre: '',
    descripcion: '',
    modulo: 'FULLSTACK', // valor por defecto válido
     activo: true
  };


  esEdicion = false;
  cursoId!: number;

  constructor(
    private cursoService: CursoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if (id) {
      this.esEdicion = true;
      this.cursoId = +id;

      // 🔄 Cargar curso para editar
      this.cursoService.listarCursos().subscribe(cursos => {
        const encontrado = cursos.find(c => c.id === this.cursoId);
        if (encontrado) {
          this.curso = encontrado;
        }
      });
    }
  }

  guardar() {

    if (this.esEdicion) {
      this.cursoService
        .actualizarCurso(this.cursoId, this.curso)
        .subscribe(() => {
          this.router.navigate(['/admin/cursos']);
        });

    } else {
      this.cursoService
        .crearCurso(this.curso)
        .subscribe(() => {
          this.router.navigate(['/admin/cursos']);
        });
    }
  }
}
