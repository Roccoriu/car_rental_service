package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/cars")
class CarController(private val carService: CarService) {
    @GetMapping
    @CrossOrigin
    fun getCars(): List<Car> = carService.getAllCars()


    @GetMapping("/{id}")
    fun getCar(@PathVariable id: Long): Car = carService.getCarsById(id)

    @PostMapping
    fun postCar(@Valid @RequestBody carDto: CarCreateUpdateDto): ResponseEntity<Car> {
        val createdCar = carService.createCar(carDto)
        return ResponseEntity(createdCar, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putCar(
            @PathVariable id: Long,
            @Valid @RequestBody carDto: CarCreateUpdateDto,
    ): ResponseEntity<Car> {
        val updatedCar = carService.updateCar(id, carDto)
        return ResponseEntity(updatedCar, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedCar = carService.deleteCar(id)
        return ResponseEntity(
                mapOf("message" to "successfully deleted", "resource" to deletedCar.toString()),
                HttpStatus.OK
        )
    }
}
