FROM amazoncorretto:11
EXPOSE 8086
ADD target/account-service-0.0.1-SNAPSHOT.jar accountservice.jar 
ENTRYPOINT ["java","-jar","/accountservice.jar"]
