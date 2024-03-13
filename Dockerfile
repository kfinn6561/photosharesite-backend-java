#Build stage

FROM gradle:8.0 AS builder
WORKDIR /usr/app/
COPY . .
RUN gradle clean shadowJar

# Package stage

FROM eclipse-temurin:17
COPY --from=builder /usr/app/build/libs/backend-1.0-all.jar .
COPY config.yml .
EXPOSE 8080
ENTRYPOINT java -jar backend-1.0-all.jar server config.yml