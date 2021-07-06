FROM amazoncorretto:11
EXPOSE 8087
ADD target/transaction-service-0.0.1-SNAPSHOT.jar transactionservice.jar 
ENTRYPOINT ["java","-jar","/transactionservice.jar"]
