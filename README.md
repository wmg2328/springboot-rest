# springboot-rest
This project is a basic spring boot application with simple CURD operations exposed as REST services

## 🔨 Building from source

Prerequisites:

- Java 8
- Gradle 4.4>
- Install docker if you want to run sample docker image

Steps:

1. Clone springboot-rest (or download the source code and extract it) and navigate
  into it:

   ```sh
   git clone https://github.com/wmg2328/springboot-rest.git
   cd springboot-rest
   ```

2. Run following command on terminal to build and deploy application:

   ```./gradlew build && java -jar build/libs/springboot-rest-0.0.1-SNAPSHOT.jar```
   
## :whale: Run application using Docker

Steps:

1. Pull docker image from Docker Hub
  
    ```docker pull wwiraj/springboot-rest:1.0.0```
  
2. Run docker container

    ```docker run --rm -d --network=host -p 8080:8080 wwiraj/springboot-rest:1.0.0```
    
## Test sample services

Use curl(or any other tool) to test following API

      curl -v localhost:8080/book
      curl -v localhost:8080/book/1
      curl -X POST localhost:8080/book -H 'Content-type:application/json' -d '{"title": "Spring Boot in Action", "author": "Craig Walls", "price": 40.23}'
      curl -X PUT localhost:8080/book/1 -H 'Content-type:application/json' -d '{"title":"Advanced API Security","author":"Prabath Siriwardena","price":54.99}'
      curl -X DELETE localhost:8080/book/3

    
    

