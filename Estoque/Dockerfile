FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install -DskipTests \
    -Dos.detected.classifier=linux-x86_64 \
    -Dprotoc.artifact=com.google.protobuf:protoc:3.25.6:exe:linux-x86_64 \
    -Dgrpc.artifact=io.grpc:protoc-gen-grpc-java:1.70.0:exe:linux-x86_64

FROM eclipse-temurin:21-jre

COPY --from=build /app/target/estoque-1.0-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]