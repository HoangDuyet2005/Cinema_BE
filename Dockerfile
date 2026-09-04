FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY /target/goldenticketnew-*.jar /usr/local/lib/app.jar

ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]