package org.rental.car_rental.repository

import org.rental.car_rental.model.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Long> {
}