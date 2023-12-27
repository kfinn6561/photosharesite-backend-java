#Build stage

FROM gradle:8.0 AS builder
WORKDIR /usr/app/
COPY . .
RUN gradle clean jar

# Package stage

FROM eclipse-temurin:17
COPY --from=builder /usr/app/build/libs/backend-1.0.jar .
COPY config.yml .
EXPOSE 8080
ENTRYPOINT java -jar backend-1.0.jar server config.yml