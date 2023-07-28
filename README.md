# photosharesite-backend-java
Backend for the photo share site written in java

## Setting up the environment
The following environment variables must be set
 * `AWS_ACCESS_KEY_ID`
 * `AWS_SECRET_ACCESS_KEY`

## Running the server
First compile the jar using maven
```
mvn package
```

Then run the jar
```
java -jar target/backend-1.0.jar server config.yml
```

Then go to http://localhost:8080/swagger-ui to test the endpoints