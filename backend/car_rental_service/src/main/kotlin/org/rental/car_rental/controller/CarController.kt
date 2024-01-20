package org.rental.car_rental.controller

import jakarta.validation.Valid
import org.rental.car_rental.dto.car.CarCreateUpdateDto
import org.rental.car_rental.dto.car.CarGetDto
import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/cars")
class CarController(
    private val carService: CarService,
) {
    @GetMapping
    @CrossOrigin
    fun getCars(
        @RequestParam type: String?,
        @RequestParam brand: String?,
        @RequestParam minPrice: Float?,
        @RequestParam maxPrice: Float?,
        @RequestParam minSeats: Int?,
        @RequestParam isAutomatic: Boolean?,
    ): List<CarGetDto> {
        val carTypes = type?.split(",")
        val carBrand = brand?.split(",")

        return carService.getAllCars(
            carTypes, carBrand, minPrice, maxPrice, minSeats, isAutomatic
        )
    }

    @GetMapping("/{id}")
    @CrossOrigin
    fun getCar(@PathVariable id: Long): Car = carService.getCarsById(id)

    @PostMapping
    @CrossOrigin
    fun postCar(@RequestBody @Valid carDto: CarCreateUpdateDto): ResponseEntity<Car> {
        val createdCar = carService.createCar(carDto)
        return ResponseEntity(createdCar, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @CrossOrigin
    fun putCar(
        @PathVariable id: Long,
        @RequestBody @Valid carDto: CarCreateUpdateDto,
    ): ResponseEntity<Car> {
        val updatedCar = carService.updateCar(id, carDto)
        return ResponseEntity(updatedCar, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    fun deleteCar(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        val deletedCar = carService.deleteCar(id)
        return ResponseEntity(
            mapOf("message" to "successfully deleted $id"),
            HttpStatus.OK
        )
    }
}
