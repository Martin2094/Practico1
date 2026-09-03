# ==============================
# ETAPA 1: COMPILAR LA APLICACIÓN
# ==============================
FROM maven:3.9-eclipse-temurin-24 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Descargar el driver de PostgreSQL para usarlo en WildFly
RUN mvn dependency:get \
    -Dartifact=org.postgresql:postgresql:42.7.7

# ==============================
# ETAPA 2: WILDFLY
# ==============================
FROM quay.io/wildfly/wildfly:41.0.0.Final-jdk25

# ------------------------------------------------
# 1. Instalar driver PostgreSQL como módulo WildFly
# ------------------------------------------------

RUN mkdir -p /opt/jboss/wildfly/modules/org/postgresql/main

COPY --from=build \
    /root/.m2/repository/org/postgresql/postgresql/42.7.7/postgresql-42.7.7.jar \
    /opt/jboss/wildfly/modules/org/postgresql/main/postgresql.jar

RUN printf '%s\n' \
'<?xml version="1.0" encoding="UTF-8"?>' \
'<module xmlns="urn:jboss:module:1.9" name="org.postgresql">' \
'    <resources>' \
'        <resource-root path="postgresql.jar"/>' \
'    </resources>' \
'    <dependencies>' \
'        <module name="java.sql"/>' \
'        <module name="java.naming"/>' \
'        <module name="jakarta.transaction.api"/>' \
'    </dependencies>' \
'</module>' \
> /opt/jboss/wildfly/modules/org/postgresql/main/module.xml


# ------------------------------------------------
# 2. Configurar WildFly
#    Usamos standalone-full porque necesitamos JMS
# ------------------------------------------------

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --commands="\
embed-server --server-config=standalone-full.xml,\
/subsystem=datasources/jdbc-driver=postgresql:add(\
driver-name=postgresql,\
driver-module-name=org.postgresql,\
driver-class-name=org.postgresql.Driver),\
/subsystem=datasources/data-source=Practico1DS:add(\
jndi-name=java:/jdbc/Practico1DS,\
driver-name=postgresql,\
connection-url=\"jdbc:postgresql://\${env.DB_HOST:postgresql}:\${env.DB_PORT:5432}/\${env.DB_NAME:practico1}\",\
user-name=\"\${env.DB_USER:postgres}\",\
password=\"\${env.DB_PASSWORD:postgres}\"),\
/subsystem=messaging-activemq/server=default/jms-queue=queue_alta_trabajador:add(\
entries=[\"java:/jms/queue/queue_alta_trabajador\",\"java:jboss/exported/jms/queue/queue_alta_trabajador\"]),\
stop-embedded-server"


# ------------------------------------------------
# 3. Copiar la aplicación
# ------------------------------------------------

COPY --from=build \
    /app/target/Practico1-1.0.0.war \
    /opt/jboss/wildfly/standalone/deployments/Practico1-1.0.0.war


EXPOSE 8080


# ------------------------------------------------
# 4. Ejecutar WildFly con configuración FULL
# ------------------------------------------------

CMD ["/opt/jboss/wildfly/bin/standalone.sh", \
     "-c", "standalone-full.xml", \
     "-b", "0.0.0.0"]