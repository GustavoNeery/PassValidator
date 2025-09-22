FROM eclipse-temurin:21-jdk

WORKDIR /PassValidator

COPY /target/*.jar PassValidator.jar

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "PassValidator.jar"]