package org.rental.car_rental.repository

import org.rental.car_rental.model.Rental
import org.springframework.data.jpa.repository.JpaRepository

interface RentalRepository : JpaRepository<Rental, Long> {
}