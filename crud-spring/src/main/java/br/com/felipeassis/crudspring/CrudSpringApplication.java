package br.com.felipeassis.crudspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.felipeassis.crudspring.enums.Category;
import br.com.felipeassis.crudspring.model.Course;
import br.com.felipeassis.crudspring.model.Lesson;
import br.com.felipeassis.crudspring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDataBase(CourseRepository repository) {
		return args -> {
			repository.deleteAll();
			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory(Category.FRONT_END);
			
			Lesson l = new Lesson();
			l.setName("Introdução");
			l.setYoutubeUrl("Nb4uxLxdvxo");
			l.setCourse(c);
			c.getLessons().add(l);
			
			Lesson l1 = new Lesson();
			l1.setName("Angular");
			l1.setYoutubeUrl("ZLpyS4qqEZE");
			l1.setCourse(c);
			c.getLessons().add(l1);

			repository.save(c);
		};
	}

}
