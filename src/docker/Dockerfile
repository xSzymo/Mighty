FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
COPY build/libs/knight-back-end-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]