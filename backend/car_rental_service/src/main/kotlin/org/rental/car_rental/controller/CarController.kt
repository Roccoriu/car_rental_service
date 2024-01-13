package org.rental.car_rental

import org.rental.car_rental.model.Car
import org.rental.car_rental.service.CarService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class CarController(private val carService: CarService) {

    @GetMapping("/cars")
    fun getCars(): List<Car> = carService.getAllCars()


//    @PostMapping("/cars")
//    fun postCar(): Optional<Car> {
//
//    }

    @GetMapping("/car/{id}")
    fun getCar(@PathVariable id: String): Optional<Car> = carService.getCarsById(id.toLong())

}
