FROM tomcat:9.0-jdk17-temurin
COPY target/TennisTable.war /usr/local/tomcat/webapps/
