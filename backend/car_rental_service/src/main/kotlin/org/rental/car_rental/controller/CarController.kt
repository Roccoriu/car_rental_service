package org.rental.car_rental.controller

import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/cars")
class CarController(private val carService: CarService) {

    @GetMapping
    fun getCars(): List<Car> = carService.getAllCars()


//    @PostMapping
//    fun postCar(): Optional<Car> {
//
//    }

    @GetMapping("/{id}")
    fun getCar(@PathVariable id: String): Optional<Car> = carService.getCarsById(id.toLong())

}
