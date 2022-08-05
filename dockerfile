FROM openjdk:18
ARG JAR_FILE=target/bootcamp-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} bootcamp-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar","bootcamp-0.0.1-SNAPSHOT.jar" ]