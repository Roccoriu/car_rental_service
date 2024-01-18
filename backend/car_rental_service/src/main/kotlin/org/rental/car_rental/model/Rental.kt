package org.rental.car_rental.model

import jakarta.persistence.*;
import java.time.LocalDate

@Entity
@Table(name = "rental")
data class Rental(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    var startDate: LocalDate,
    var endDate: LocalDate,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    var car: Car? = null,
)
