#Build stage

FROM gradle:latest AS builder
WORKDIR /usr/app/
COPY . .
RUN gradle clean build

# Package stage

FROM openjdk:latest
COPY --from=builder /usr/app/build/libs/backend-1.0.jar .
COPY config.yml .
EXPOSE 8080
ENTRYPOINT java -jar backend-1.0.jar server config.yml