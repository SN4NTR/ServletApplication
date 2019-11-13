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

* Deploy `war` file to Tomcat:

1. Open IntelliJ IDEA -> Edit Configuration -> Add new configuration -> Tomcat.
2. Select deployed `war` file.
3. Select `URL`.

* Start MySQL Database:

```
docker-compose up
```