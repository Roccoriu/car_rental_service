package org.rental.car_rental.model

import java.time.LocalDate

data class Rental(
    var id: Long,
    var carId: Long,
    var customerName: String,
    var startDate: LocalDate,
    var endDate: LocalDate,
)
