package org.kpale.springcloud.msvc.cursos.clients;

import org.kpale.springcloud.msvc.cursos.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios", url = "host.docker.internal:8001")
public interface IUserClientRest {

    @GetMapping("/{id}")
    User getById(@PathVariable Long id);

    @PostMapping
    User post( @RequestBody User user);

    @GetMapping("/getAllByCourse")
    List<User> getAllByCourse(@RequestParam Iterable<Long> ids);
}
