
FROM tomcat:10.1-jdk11


COPY target/TennisTable.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080


CMD ["catalina.sh", "run"]
