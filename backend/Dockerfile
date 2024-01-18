FROM gradle:jdk20-alpine AS BUILDER
LABEL authors="riu"

WORKDIR /app
COPY ./car_rental_service /app

RUN gradle build --no-daemon

FROM amazoncorretto:20.0.2
EXPOSE 8080

COPY --from=BUILDER /app/build/libs/*.jar /app/app.jar


ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]