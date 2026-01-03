FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src

RUN chmod +x mvnw && ./mvnw -B -ntp clean package -DskipTests

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/target/conway-0.0.1-SNAPSHOT.jar"]
