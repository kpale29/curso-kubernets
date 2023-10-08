package org.kpale.springcloud.msvc.usuarios.repositories;

import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserRepository extends CrudRepository<User,Long> {

}
