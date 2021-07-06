FROM amazoncorretto:11
EXPOSE 8090
ADD target/rules-0.0.1-SNAPSHOT.jar rulesservice.jar 
ENTRYPOINT ["java","-jar","/rulesservice.jar"]
