
FROM maven:3.9.6-eclipse-temurin-19 AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk17
COPY --from=build /app/target/TennisTable.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
