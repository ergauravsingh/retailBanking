FROM amazoncorretto:11
EXPOSE 8761
ADD target/discovery-server-0.0.1-SNAPSHOT.jar discoveryServer.jar 
ENTRYPOINT ["java","-jar","/discoveryServer.jar"]
