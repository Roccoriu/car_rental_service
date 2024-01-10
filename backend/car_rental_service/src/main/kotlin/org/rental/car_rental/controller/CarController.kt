package org.rental.car_rental

import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CarController(private val carService: CarService) {

    @GetMapping("/cars")
    fun getAllCars(): List<Car> {
        return carService.getCars()
    }
}
