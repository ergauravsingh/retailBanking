FROM amazoncorretto:11
EXPOSE 8084
ADD target/auth-service-0.0.1-SNAPSHOT.jar authservice.jar 
ENTRYPOINT ["java","-jar","/authservice.jar"]
