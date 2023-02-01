import { Course } from './../../model/course';
import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { __classPrivateFieldSet } from 'tslib';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form = this.fb.group({
    _id: [''],
    name: ['', [
      Validators.required,
      Validators.minLength(5),
      Validators.maxLength(100)
    ]],
    category: ['', [Validators.required]]
  });

  constructor(
    private fb: NonNullableFormBuilder,
    private location: Location,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private service: CoursesService
  ) {
  }

  ngOnInit() {
    const course: Course = this.route.snapshot.data['course'];
    console.log(course);
    this.form.setValue({
      _id: course._id,
      name: course.name,
      category: course.category
    });
  }

  getErrorMessage(fieldName: string) {
    const field = this.form.get(fieldName);

    if (field?.hasError('required')) {
      return 'Campo obrigatório';
    }
    if(field?.hasError('minlength')) {
      const requiredLength = field.errors ? field.errors['minlength']['requiredLength'] : 5;

      return `Tamanho mínimo precisa ser de ${requiredLength} caracteres.`;
    }
    if(field?.hasError('maxlength')) {
      const requiredLength = field.errors ? field.errors['maxlength']['requiredLength'] : 100;

      return `Tamanho máximo excedido de ${requiredLength} caracteres.`;
    }

    return 'Campo inválido';
  }

  onCancel() {
    this.location.back();
  }

  onSubmit() {
    this.service.save(this.form.value).subscribe({
      next: result => this.onSuccess(),
      error: () => this.onError()
    });
  }

  private onError() {
    this.snackBar.open('Erro ao salvar curso', '', {
      duration: 5000
    });
  }

  private onSuccess() {
    this.snackBar.open('Curso salvo com sucesso!', '', {
      duration: 5000
    });
    this.onCancel();
  }

}
