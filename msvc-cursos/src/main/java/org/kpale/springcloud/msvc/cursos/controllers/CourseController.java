package org.kpale.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import jakarta.validation.Valid;
import org.kpale.springcloud.msvc.cursos.models.User;
import org.kpale.springcloud.msvc.cursos.models.entities.Course;
import org.kpale.springcloud.msvc.cursos.services.CourseService;
import org.kpale.springcloud.msvc.cursos.services.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        Optional<Course> user =  this.service.getByUsersId(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid  @RequestBody Course user, BindingResult result){
        if(result.hasErrors()){
            return validateResult(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Valid @RequestBody Course newUser, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validateResult(result);
        }

        Optional<Course> user = this.service.byId(id);
        if(user.isPresent()) {
            Course userDb = user.get();
            userDb.setName(newUser.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(userDb));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> user = this.service.byId(id);
        if(user.isPresent()) {
            this.service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("deleteCourseUser/{id}")
    public ResponseEntity<?> deleteCourseUser(@PathVariable Long id){
        this.service.deleteCourseUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/setUser/{courseId}")
    public ResponseEntity<?> setUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> newUser;

        try{
            newUser = this.service.setUser(user,courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error:", "No Existe el usuario o error en la comunicacion: " + e.getMessage()));
        }

        if(newUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/postUser/{courseId}")
    public ResponseEntity<?> postUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> newUser;

        try{
            newUser = this.service.postUser(user,courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error:", "No se pudo crear el usuario o error en la comunicacion: " + e.getMessage()));
        }

        if(newUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteUser/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> newUser;

        try{
            newUser = this.service.deleteUser(user,courseId);
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error:", "No Existe el usuario o error en la comunicacion: " + e.getMessage()));
        }

        if(newUser.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(newUser.get());
        }

        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validateResult(BindingResult result) {
        Map<String,String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo" + error.getField() + " " + error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
