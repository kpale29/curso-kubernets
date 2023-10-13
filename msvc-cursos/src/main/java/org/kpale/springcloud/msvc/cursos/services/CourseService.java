package org.kpale.springcloud.msvc.cursos.services;

import org.kpale.springcloud.msvc.cursos.clients.IUserClientRest;
import org.kpale.springcloud.msvc.cursos.models.User;
import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.kpale.springcloud.msvc.cursos.models.entities.CourseUser;
import org.kpale.springcloud.msvc.cursos.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService implements ICourseService{

    private final ICourseRepository repository;

    private final IUserClientRest client;

    public CourseService(ICourseRepository repository, IUserClientRest client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> byId(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return this.repository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> setUser(User user, Long courseId) {
        Optional<Course> course = this.repository.findById(courseId);
        if(course.isPresent()){
            User userMsvc = this.client.getById(user.getId());

            Course newCourse = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUsuarioId(userMsvc.getId());

            newCourse.addCourseUser(courseUser);
            this.repository.save(newCourse);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> postUser(User user, Long courseId) {
        Optional<Course> course = this.repository.findById(courseId);
        if(course.isPresent()){
            User userNewMsvc = this.client.post(user);

            Course newCourse = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUsuarioId(userNewMsvc.getId());

            newCourse.addCourseUser(courseUser);
            this.repository.save(newCourse);
            return Optional.of(userNewMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(User user, Long courseId) {
        Optional<Course> course = this.repository.findById(courseId);
        if(course.isPresent()){
            User userMsvc = this.client.getById(user.getId());

            Course newCourse = course.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUsuarioId(userMsvc.getId());

            newCourse.removeCourseUser(courseUser);
            this.repository.save(newCourse);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCourseUserById(Long id) {
        this.repository.deleteCourseUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getByUsersId(Long id) {
        Optional<Course> oCourse = this.repository.findById(id);
        if(oCourse.isPresent()){
            Course course = oCourse.get();
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(CourseUser::getUsuarioId).toList();

                List<User> users = this.client.getAllByCourse(ids);
                course.setUsers(users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }
}
