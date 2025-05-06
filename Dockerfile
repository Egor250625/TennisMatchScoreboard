
FROM tomcat:10.1


RUN rm -rf /usr/local/tomcat/webapps/*


COPY target/TennisTable.war /usr/local/tomcat/webapps/ROOT.war


EXPOSE 8080
