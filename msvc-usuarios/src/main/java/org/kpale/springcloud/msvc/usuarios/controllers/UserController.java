package org.kpale.springcloud.msvc.usuarios.controllers;

import feign.Response;
import jakarta.validation.Valid;
import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.kpale.springcloud.msvc.usuarios.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController  {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> get(){
        return this.service.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Optional<User> user =  this.service.byId(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> post(@Valid  @RequestBody User user, BindingResult result ){
        if(result.hasErrors()){
            return validateResult(result);
        }

        if(service.existsByEmail(user.getEmail()))
            return ResponseEntity.badRequest().body(Collections.singletonMap("Error","El correo electronico ya ha sido registrado"));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@Valid @RequestBody User newUser,BindingResult result, @PathVariable Long id){

        if(result.hasErrors()) return validateResult(result);

        Optional<User> user = service.byId(id);
        if(user.isPresent()) {
            User userDb = user.get();

            if(!newUser.getEmail().isEmpty() && !newUser.getEmail().equalsIgnoreCase(userDb.getEmail()) && service.byEmail(newUser.getEmail()).isPresent())
                return ResponseEntity.badRequest().body(Collections.singletonMap("Error","El correo electronico ya ha sido registrado"));

            userDb.setName(newUser.getName());
            userDb.setEmail(newUser.getEmail());
            userDb.setPassword(newUser.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
       }

       return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> user = service.byId(id);
        if(user.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
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
