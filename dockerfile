FROM openjdk:18
# RUN addgroup -S spring && adduser -S spring -G spring
# USER spring:spring
# ADD target/cursos-0.0.1-SNAPSHOT.jar app.jar
ARG JAR_FILE=target/bootcamp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bootcamp-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar","bootcamp-0.0.1-SNAPSHOT.jar" ]