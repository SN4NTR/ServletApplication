FROM tomcat:latest
RUN rm -rf /usr/local/tomcat/webapps/*
CMD ["catalina.sh", "run"]
EXPOSE 8080