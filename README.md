# photosharesite-backend-java
Backend for the photo share site written in java

## Setting up the environment
The following environment variables must be set
 * `AWS_ACCESS_KEY_ID`
 * `AWS_SECRET_ACCESS_KEY`

## Running the server
Run with gradle
```
./gradlew run
```

## Running the tests
Run the tests with gradle
```
./gradlew test
```

## Running with Docker
build the docker image with
```
docker build -t pss-backend .
```

Then run using
```
docker run -p 8080:8080 -e AWS_ACCESS_KEY_ID=<access-key> -e AWS_SECRET_ACCESS_KEY=<secret-key> pss-backend
```


Then go to http://localhost:8080/swagger-ui to test the endpoints