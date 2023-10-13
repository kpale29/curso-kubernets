package org.kpale.springcloud.msvc.cursos.clients;

import org.kpale.springcloud.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="msvc-usuarios", url = "localhost:8001")
public interface IUserClientRest {

    @GetMapping("/{id}")
    User getById(@PathVariable Long id);

    @PostMapping
    User post( @RequestBody User user);



}
