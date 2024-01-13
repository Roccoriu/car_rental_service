package org.rental.car_rental.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.*;

@Configuration
class SwaggerConfig {
    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
                .info(Info()
                        .title("Car Rental Service API")
                        .version("v1")
                )
    }

}