import { Component, OnInit } from '@angular/core';

import { Course } from '../model/course';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
})
export class CoursesComponent implements OnInit {

  courses: Course[] = [
    { _id: '1', name: 'Angular', category: 'Front-end' },
  ];
  displayedColumns = ['name', 'category'];

  constructor() {
    // Também está certo! Mas poupa uma linha de código
    // this.courses = [];
  }

  ngOnInit(): void {
  }
}
