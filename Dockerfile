FROM adoptopenjdk/openjdk11:jdk-11.0.14.1_1-alpine-slim

EXPOSE 8080

COPY /target/goldenticketnew-*.jar /usr/local/lib/app.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]