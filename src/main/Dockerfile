# Build interno maven
FROM maven:3.8.3-openjdk-17
#FROM maven:3.6.3-openjdk-8 AS mvn-compile
WORKDIR /maven
COPY .  .
RUN mvn -f ./pom.xml clean package -DskipTests=false

#Imagen opensource JDK 8 (Se alojan en repo google)
FROM openjdk:17-oracle
#Variable de entorno de juego de caracteres basado en el proyecto
ENV LANG C.UTF-8
#Agrega el JAR generado del microservicio
COPY --from=mvn-compile /maven/target/*.jar /api/app.jar
RUN ls /api
WORKDIR /api
ENTRYPOINT exec java -jar app.jar

