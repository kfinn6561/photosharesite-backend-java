# photosharesite-backend-java
Backend for the photo share site written in java

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