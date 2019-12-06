# Servlet Application v4.0.1

### Pre-requisites

* Java 13
* Tomcat 9.0.27
* Maven 3.1.0
* Docker

### Start Application

* Open project package in cmd and build `war` file with Maven:

```
mvn clean install
```

* Start application:

```
docker-compose up
```

* Go to the following `URL`:

```
http://localhost:8080/app/users
```

You will see the list of users.

