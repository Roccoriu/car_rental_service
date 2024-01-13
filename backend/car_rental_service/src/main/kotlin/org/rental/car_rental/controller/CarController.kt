package org.rental.car_rental.controller

import org.rental.car_rental.dto.car.CarCreateDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/cars")
class CarController(private val carService: CarService) {

    @GetMapping
    fun getCars(): List<Car> = carService.getAllCars()


    @GetMapping("/{id}")
    fun getCar(@PathVariable id: String): Optional<Car> = carService.getCarsById(id.toLong())

    @PostMapping
    fun postCar(@RequestBody carDto: CarCreateDto): ResponseEntity<Car> {
        val createdCar = carService.createCar(carDto)
        return ResponseEntity(createdCar, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun putCar(@RequestBody carDto: CarCreateDto): ResponseEntity<Car> {
        val updatedCar = carService.updateCar(carDto)
        return ResponseEntity(updatedCar, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: String): ResponseEntity<Map<String, String>> {
        val deletedCar = carService.deleteCar(id.toLong())
        return ResponseEntity(
                mapOf("message" to "Successfully deleted", "resource" to deletedCar.toString()),
                HttpStatus.OK
        )
    }
}
