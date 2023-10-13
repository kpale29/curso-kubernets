package org.kpale.springcloud.msvc.cursos.repositories;

import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ICourseRepository extends CrudRepository<Course,Long> {
    @Modifying
    @Query("delete from CourseUser cu where cu.userId = ?1")
    void deleteCourseUserById(Long id);
}
