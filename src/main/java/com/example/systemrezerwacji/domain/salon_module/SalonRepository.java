package com.example.systemrezerwacji.domain.salon_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalonRepository extends CrudRepository<Salon, Long> {
}
