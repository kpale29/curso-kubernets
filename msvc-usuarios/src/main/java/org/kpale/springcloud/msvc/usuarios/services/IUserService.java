package org.kpale.springcloud.msvc.usuarios.services;

import org.kpale.springcloud.msvc.usuarios.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> list();
    Optional<User> byId(Long id);
    User save(User user);
    void delete(Long id);
    Optional<User> byEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAllByIds(Iterable<Long> ids);
}
