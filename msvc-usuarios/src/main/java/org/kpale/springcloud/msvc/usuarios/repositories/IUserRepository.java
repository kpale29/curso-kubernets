package org.kpale.springcloud.msvc.usuarios.repositories;

import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email=?1")
    Optional<User> byEmail(String email);

    boolean existsByEmail(String email);

}
