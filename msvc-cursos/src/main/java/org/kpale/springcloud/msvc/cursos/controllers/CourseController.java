package org.kpale.springcloud.msvc.cursos.controllers;

import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.kpale.springcloud.msvc.cursos.services.CourseService;
import org.kpale.springcloud.msvc.cursos.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    private final ICourseService service;

    public CourseController(ICourseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Course> get(){
        return this.service.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<Course> user =  this.service.byId(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Course user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody Course newUser, @PathVariable Long id){
        Optional<Course> user = service.byId(id);
        if(user.isPresent()) {
            Course userDb = user.get();
            userDb.setName(newUser.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> user = service.byId(id);
        if(user.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
