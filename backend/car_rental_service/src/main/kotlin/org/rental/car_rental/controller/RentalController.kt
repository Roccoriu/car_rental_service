package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.dto.rental.RentalUpdateDto
import org.rental.car_rental.model.Rental
import org.rental.car_rental.service.RentalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/rental")
class RentalController(private val rentalService: RentalService) {
    @GetMapping
    fun getRentals(): List<Rental> = rentalService.getAllRentals()

    @GetMapping("/{id}")
    fun getRental(@PathVariable id: Long): Rental = rentalService.getRentalsById(id)

    @PostMapping
    fun postRental(@Valid @RequestBody rentalDto: RentalCreateDto): ResponseEntity<Rental> {
        val createdRental = rentalService.createRental(rentalDto)
        return ResponseEntity(createdRental, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putRental(
            @PathVariable id: Long,
            @Valid @RequestBody rentalDto: RentalUpdateDto,
    ): ResponseEntity<Rental> {
        val updatedRental = rentalService.updateRental(id, rentalDto)
        return ResponseEntity(updatedRental, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deleteRental(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedRental = rentalService.deleteRental(id)
        return ResponseEntity(
                mapOf("message" to "successfully deleted", "resource" to deletedRental.toString()),
                HttpStatus.OK
        )
    }
}