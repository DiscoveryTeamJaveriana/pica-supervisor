FROM openjdk:8-jdk-alpine
COPY target/pica-supervisor-0.0.1-SNAPSHOT.jar pica-supervisor.jar
ENTRYPOINT ["java", "-jar", "pica-supervisor.jar"]