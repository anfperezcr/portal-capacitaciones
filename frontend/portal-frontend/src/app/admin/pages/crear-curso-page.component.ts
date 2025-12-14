import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

import { CursoService } from '../../cursos/services/curso.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-crear-curso-page',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule
  ],
  templateUrl: './crear-curso-page.component.html'
})
export class CrearCursoPageComponent {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private cursoService: CursoService,
    private router: Router
    ) {
    this.form = this.fb.group({
      nombre: ['', Validators.required],
      descripcion: ['', Validators.required],
      modulo: ['', Validators.required],
      activo: [true]
    });
  }

  guardar() {
    if (this.form.invalid) return;

    this.cursoService.crearCurso(this.form.value)
      .subscribe(() => {
        alert('Curso creado correctamente');
        this.router.navigate(['/cursos']);
      });
  }

}
