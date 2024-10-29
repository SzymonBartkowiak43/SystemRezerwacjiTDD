package com.example.systemrezerwacji.salon_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SalonRepository extends CrudRepository<Salon, Long> {
}
