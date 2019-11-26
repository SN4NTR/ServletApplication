# Servlet Application v4.0.1

### Pre-requisites

* Java 13
* IntelliJ IDEA
* Tomcat 9.0.27
* Maven 3.1.0
* Docker

### Start Application

* Open project package in cmd and build `war` file with Maven:

```
mvn clean install
```

* Start MySQL Database:

```
docker-compose up
```

* Deploy `war` file to Tomcat:

1. Copy `war` file from `target` directory to Tomcat directory (e.g. `C:/Tomcat/webapps`).
2. Start Tomcat server (`Tomcat/bin/startup.bat`).
3. Type `http://localhost:8080/YOUR_WAR_FILE_NAME/users` in browser to see all users.