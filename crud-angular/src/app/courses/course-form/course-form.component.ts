import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../services/courses.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private snackBar: MatSnackBar,
    private service: CoursesService
  ) {
    this.form = this.fb.group({
      name: [null],
      category: [null]
    });
  }

  ngOnInit() {
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
