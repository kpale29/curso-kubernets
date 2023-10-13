package org.kpale.springcloud.msvc.cursos.services;

import org.kpale.springcloud.msvc.cursos.models.User;
import org.kpale.springcloud.msvc.cursos.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    List<Course> list();
    Optional<Course> byId(Long id);
    Course save(Course course);
    void delete(Long id);
    Optional<User> setUser(User user, Long courseId);
    Optional<User> postUser(User user, Long courseId);
    Optional<User> deleteUser(User user, Long courseId);
    void deleteCourseUserById(Long id);
    Optional<Course> getByUsersId(Long id);
}
