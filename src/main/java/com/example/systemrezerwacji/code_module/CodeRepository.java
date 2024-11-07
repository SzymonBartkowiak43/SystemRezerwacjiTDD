package com.example.systemrezerwacji.code_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface CodeRepository extends CrudRepository<Code,Long> {
    Optional<Code> findByCode(String code);
}
