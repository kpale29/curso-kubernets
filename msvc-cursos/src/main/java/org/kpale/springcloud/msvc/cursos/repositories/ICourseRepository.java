package org.kpale.springcloud.msvc.cursos.repositories;

import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface ICourseRepository extends CrudRepository<Course,Long> {
}
