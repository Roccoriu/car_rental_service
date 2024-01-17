package org.rental.car_rental.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.*;

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("Car Rental Service API")
                .version("v1")
                .contact(
                    Contact()
                        .name("Rocco Ciccone")
                        .email("rocco.ciccone@proton.me")

                )
                .license(License().name("GPLv3"))
        )
    }

}