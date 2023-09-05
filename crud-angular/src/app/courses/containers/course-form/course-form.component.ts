import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { Course } from './../../model/course';
import { Lesson } from '../../model/lesson';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss']
})
export class CourseFormComponent implements OnInit {

  form!: FormGroup;

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
    this.form = this.fb.group({
      _id: [course._id],
      name: [course.name, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)
      ]],
      category: [course.category, [Validators.required]],
      lessons: this.fb.array(this.retrieveLessons(course), Validators.required)
    });

    console.log(course);
  }

  addNewLesson() {
    const lessons = <UntypedFormArray> this.form.get('lessons');
    lessons.push(this.createLesson());
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

  getLessonsFormArray() {
    return (<UntypedFormArray> this.form.get('lessons')).controls;
  }

  isFormArrayRequired() {
    const lessons = <UntypedFormArray> this.form.get('lessons');

    return !lessons.valid && lessons.hasError('required') && lessons.touched;
  }

  onCancel() {
    this.location.back();
  }

  onSubmit() {
    if (this.form.valid) {
      this.service.save(this.form.value).subscribe({
        next: () => this.onSuccess(),
        error: () => this.onError()
      });
    } else {
      alert('Form inválido!');
    }
  }

  removeLesson(index: number) {
    const lessons = <UntypedFormArray> this.form.get('lessons');
    lessons.removeAt(index);
  }

  private createLesson(lesson: Lesson = { id: '', name: '', youtubeUrl: '' }) {
    return this.fb.group({
      id: [lesson.id, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)
      ]],
      name: [lesson.name, [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)
      ]],
      youtubeUrl: [lesson.youtubeUrl, [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(11)
      ]]
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

  private retrieveLessons(course: Course) {
    const lessons = [];

    if (course?.lessons) {
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)));
    } else {
      lessons.push(this.createLesson());
    }

    return lessons;
  }

}
