package org.kpale.springcloud.msvc.usuarios.services;

import org.kpale.springcloud.msvc.usuarios.clients.ICourseClientRest;
import org.kpale.springcloud.msvc.usuarios.models.entities.User;
import org.kpale.springcloud.msvc.usuarios.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository repository;

    private final ICourseClientRest client;

    public UserService(IUserRepository repository, ICourseClientRest client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> list() {
        return (List<User>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> byId(Long id) {
        return this.repository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return this.repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.repository.deleteById(id);
        this.client.deleteCourseUser(id);
    }

    @Override
    public Optional<User> byEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllByIds(Iterable<Long> ids) {
       return (List<User>) this.repository.findAllById(ids);
    }

}
