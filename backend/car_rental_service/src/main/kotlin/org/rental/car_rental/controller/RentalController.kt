package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.rental.RentalCreateDto
import org.rental.car_rental.model.Rental
import org.rental.car_rental.service.RentalService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/rental")
class RentalController(private val rentalService: RentalService) {
    @GetMapping
    @CrossOrigin
    fun getRentals(): List<Rental> = rentalService.getAllRentals()

    @GetMapping("/{id}")
    @CrossOrigin
    fun getRental(@PathVariable id: Long): Rental = rentalService.getRentalsById(id)

    @PostMapping
    @CrossOrigin
    fun postRental(@Valid @RequestBody rentalDto: RentalCreateDto): ResponseEntity<Rental> {
        val createdRental = rentalService.createRental(rentalDto)
        return ResponseEntity(createdRental, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    @CrossOrigin
    fun putRental(
            @PathVariable id: Long,
            @Valid @RequestBody rentalDto: RentalCreateDto,
    ): ResponseEntity<Rental> {
        val updatedRental = rentalService.updateRental(id, rentalDto)
        return ResponseEntity(updatedRental, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    fun deleteRental(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedRental = rentalService.deleteRental(id)
        return ResponseEntity(
                mapOf("message" to "successfully deleted", "resource" to deletedRental.toString()),
                HttpStatus.OK
        )
    }
}