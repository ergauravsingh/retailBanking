FROM amazoncorretto:11
EXPOSE 8080
ADD target/bankmvc-0.0.1-SNAPSHOT.jar bankservice.jar 
ENTRYPOINT ["java","-jar","/bankservice.jar"]
