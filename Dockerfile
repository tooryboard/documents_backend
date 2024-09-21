FROM tomcat:9.0.95-jdk17
COPY target/*.war /usr/local/tomcat/webapps/ROOT.war