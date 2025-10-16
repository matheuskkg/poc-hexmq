FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /mensageria
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /mensageria
COPY --from=build /mensageria/target/*.jar poc-mensageria.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "poc-mensageria.jar"]