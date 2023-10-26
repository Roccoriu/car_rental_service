package org.rental.car_rental

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CarRentalServiceApplication

fun main(args: Array<String>) {
	runApplication<CarRentalServiceApplication>(*args)
}
