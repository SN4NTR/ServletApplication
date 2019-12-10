# Servlet Application v2.5

### Pre-requisites

* Java 13
* Tomcat 9.0.27
* Maven 3.1.0
* Docker

Application Deployment
---

#### Deploying with Docker

1. Open project package in cmd and build `war` file with Maven:

```
mvn clean install
```

2. Start docker containers by docker-compose:

```
docker-compose up -d
```

3. Go to the following `URL`:

```
http://localhost:8080/APP_NAME/users
```

You will see the list of users.

> To stop application type in cmd: `docker-compose stop`.

#### Deployment on Tomcat

1. Open Control Panel and set the following system variables:

* `DB_URL=jdbc:mysql://SERVICE_NAME:PORT/DB_NAME`
* `DB_USER=YOUR_USERNAME`
* `DB_PASSWORD=YOUR_PASSWORD`

2. Open project package in cmd and build `war` file with Maven:
   
```
mvn clean install
```

3. Copy `war` file to `CATALINA_HOME/webapps`.
4. Start file `startup.bat`.
5. Go to the following `URL`:

```
http://localhost:8080/APP_NAME/users
```

You will see the list of users.

> To stop application run `shutdown.bat`.

