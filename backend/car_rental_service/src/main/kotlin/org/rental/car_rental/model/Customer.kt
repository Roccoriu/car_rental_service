package org.rental.car_rental.model

import jakarta.persistence.*;
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "customer")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0;
    var firstName: String = "";
    var lastName: String = "";
    var dateOfBirth: LocalDate? = LocalDate.now();
    var email: String = "";

    @OneToMany(mappedBy = "customer", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val rentals: List<Rental> = mutableListOf();
}