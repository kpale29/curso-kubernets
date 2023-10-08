package org.kpale.springcloud.msvc.usuarios.controllers;

import feign.Response;
import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.kpale.springcloud.msvc.usuarios.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> post(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@RequestBody User newUser, @PathVariable Long id){
       Optional<User> user = service.byId(id);
       if(user.isPresent()) {
           User userDb = user.get();
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
}
