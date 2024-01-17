package org.rental.car_rental.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*;
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    val firstName: String = "",
    val lastName: String = "",
    val dateOfBirth: LocalDate? = LocalDate.now(),
    val email: String = "",

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    val rentals: List<Rental> = mutableListOf(),
)