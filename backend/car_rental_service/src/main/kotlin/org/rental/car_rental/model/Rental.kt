package org.rental.car_rental.model

import jakarta.persistence.*;
import java.time.LocalDate

@Entity
@Table(name = "rental")
class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0;

    val startDate: LocalDate = LocalDate.now();
    val endDate: LocalDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    val customer: Customer? = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    val car: Car? = null;
}
