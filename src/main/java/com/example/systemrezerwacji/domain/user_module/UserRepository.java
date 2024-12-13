package com.example.systemrezerwacji.domain.user_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<User, Long> {
    User getUserById(Long id);
    Optional<User> getUserByEmail(String email);
}