package org.kpale.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-cursos", url = "host.docker.internal:8002")
public interface ICourseClientRest {
    @DeleteMapping("deleteCourseUser/{id}")
    void deleteCourseUser(@PathVariable Long id);
}
