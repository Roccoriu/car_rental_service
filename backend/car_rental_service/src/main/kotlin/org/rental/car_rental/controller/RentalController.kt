package org.rental.car_rental.controller

import org.rental.car_rental.model.Rental
import org.rental.car_rental.service.RentalService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/rental")
class RentalController(private val rentalService: RentalService) {
    @GetMapping
    fun getRentals(): List<Rental> = rentalService.getAllRentals()

    @GetMapping("/{id}")
    fun getRental(@PathVariable id: String): Optional<Rental> = rentalService.getCarsById(id.toLong())
}