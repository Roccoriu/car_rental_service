package org.rental.car_rental.repository

import org.rental.car_rental.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, Long> {
}