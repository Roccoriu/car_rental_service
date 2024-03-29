package org.rental.car_rental.error.handler

import org.rental.car_rental.error.exception.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.lang.Exception
import java.util.stream.Collectors

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<String> =
        ResponseEntity(ex.message, HttpStatus.NOT_FOUND)


    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex
            .bindingResult
            .fieldErrors
            .stream()
            .collect(
                Collectors.toMap(
                    { error -> error.field },
                    { error -> error.defaultMessage ?: "Invalid" }
                ))

        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleDeserializationErrors(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, String>> {
        return ResponseEntity(
            mapOf("error" to "invalid param${ex.message?.split("parameter")?.get(1)}"),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(S3Exception::class)
    fun handleS3Error(ex: S3Exception): ResponseEntity<Map<String, String>> =
        ResponseEntity(
            mapOf("error" to "an error happened while uploading to s3 bucket"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )

    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(ex: Exception, request: WebRequest): ResponseEntity<String> {
        logger.error("Unhandled exception occured: ${ex.message}", ex)
        return ResponseEntity("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}