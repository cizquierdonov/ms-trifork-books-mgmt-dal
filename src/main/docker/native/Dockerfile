# Simple Dockerfile adding Maven and GraalVM Native Image compiler to the standard
# https://github.com/graalvm/container/pkgs/container/graalvm-ce image

FROM ghcr.io/graalvm/graalvm-ce:ol7-java17-22.3.1

ADD . /build
WORKDIR /build

RUN yum install -y unzip zip

# Install SDKMAN
RUN \
    # Install SDKMAN
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
    sdk install maven; \
    # Install GraalVM Native Image
    gu install native-image;

# Install GraalVM Native Image
RUN gu install native-image;

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn --version

RUN native-image --version

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -B clean package -P native -DskipTests=false

# opensource JDK image
#FROM openjdk:17
FROM springci/graalvm-ce:java17-0.12.x

# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=0 /build/target/*.jar /api/app.jar
RUN ls /api
WORKDIR /api
ENTRYPOINT exec java -jar app.jar

