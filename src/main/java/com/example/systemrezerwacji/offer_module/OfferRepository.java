package com.example.systemrezerwacji.offer_module;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface OfferRepository extends CrudRepository<Offer, Long> {
    List<Offer> findAllBySalonId(Long salonId);
    Offer findOfferById(Long offerId);
}
