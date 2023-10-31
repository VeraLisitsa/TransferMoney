FROM adoptopenjdk/openjdk11:alpine-jre

EXPOSE 5500

COPY target/TransferMoney-0.0.1-SNAPSHOT.jar TransferMoney.jar

CMD ["java", "-jar", "TransferMoney.jar"]