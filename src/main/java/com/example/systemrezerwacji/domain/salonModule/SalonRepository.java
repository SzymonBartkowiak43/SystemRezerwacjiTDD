package com.example.systemrezerwacji.domain.salonModule;

import com.example.systemrezerwacji.domain.userModule.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonRepository extends CrudRepository<Salon, Long> {
    List<Salon> getSalonsByUser(User user);
}
