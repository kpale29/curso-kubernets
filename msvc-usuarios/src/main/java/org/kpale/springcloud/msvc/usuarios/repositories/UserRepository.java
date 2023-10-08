package org.kpale.springcloud.msvc.usuarios.repositories;

import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

}
