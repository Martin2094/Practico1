FROM maven:3.9-eclipse-temurin-24 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests


FROM quay.io/wildfly/wildfly:41.0.0.Final-jdk25

COPY --from=build /app/target/Practico1-1.0.0.war \
    /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080