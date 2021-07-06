FROM amazoncorretto:11
EXPOSE 8085
ADD target/customer-0.0.1-SNAPSHOT.jar customerservice.jar 
ENTRYPOINT ["java","-jar","/customerservice.jar"]
