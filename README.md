# Servlet Application v4.0.1

### Pre-requisites

* Java 13
* Tomcat 9.0.27
* Maven 3.1.0
* Docker

### Application Deployment

#### Setting up of system variables

Set the following system variables: `DB_URL=jdbc:mysql://SERVICE_NAME:PORT/DB_NAME`, `DB_USER=YOUR_USERNAME`, 
`DB_PASSWORD=YOUR_PASSWORD`.

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

1. Open project package in cmd and build `war` file with Maven:
   
```
mvn clean install
```

2. Copy `war` file to `CATALINA_HOME/webapps`.
3. Start file `startup.bat`.
4. Go to the following `URL`:

```
http://localhost:8080/APP_NAME/users
```

You will see the list of users.

> To stop application run `shutdown.bat`.

