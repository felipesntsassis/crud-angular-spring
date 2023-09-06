import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormArray, FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';

import { CoursesService } from '../../services/courses.service';
import { Course } from './../../model/course';
import { Lesson } from '../../model/lesson';
import { FormUtilsService } from 'src/app/shared/form/form-utils.service';

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
    private service: CoursesService,
    public formUtils: FormUtilsService
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
  }

  addNewLesson() {
    const lessons = <UntypedFormArray> this.form.get('lessons');
    lessons.push(this.createLesson());
  }

  getLessonsFormArray() {
    return (<UntypedFormArray> this.form.get('lessons')).controls;
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
      this.formUtils.validateAllFormFields(this.form);
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
