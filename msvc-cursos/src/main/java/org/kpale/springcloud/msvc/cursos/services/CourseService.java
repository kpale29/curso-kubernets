package org.kpale.springcloud.msvc.cursos.services;

import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.kpale.springcloud.msvc.cursos.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService implements ICourseService{

    private final ICourseRepository repository;

    public CourseService(ICourseRepository repository) {
        this.repository = repository;
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
}
