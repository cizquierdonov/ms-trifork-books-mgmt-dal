# Maven internal build
FROM maven:3.8.3-openjdk-17
WORKDIR /maven
COPY .  .
RUN mvn -f ./pom.xml clean package -DskipTests=false

# opensource JDK image
FROM openjdk:17

# Encoding charset variable
ENV LANG C.UTF-8

#Add microservice generated JAR
COPY --from=0 /maven/target/*.jar /api/app.jar
RUN ls /api
WORKDIR /api
ENTRYPOINT exec java -jar app.jar

