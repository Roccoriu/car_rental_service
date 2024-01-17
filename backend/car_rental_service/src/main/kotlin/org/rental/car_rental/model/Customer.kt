package org.rental.car_rental.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*;
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "customer")
data class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var firstName: String = "",
    var lastName: String = "",
    var dateOfBirth: LocalDate? = LocalDate.now(),
    var email: String = "",

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    var rentals: List<Rental> = mutableListOf(),
)