# Servlet Application v2.5

### Pre-requisites

* Java 13
* Tomcat 9.0.27
* Maven 3.1.0
* Docker

Application Deployment
---

### Deploying with Docker

1. Go to folder `env` and open file `db_temp.env`. Fill the following fields:
* `MYSQL_DATABASE=YOUR_DB_NAME`
* `MYSQL_USER=YOUR_USERNAME`
* `MYSQL_PASSWORD=YOUR_PASSWORD`
* `MYSQL_ROOT_PASSWORD=YOUR_ROOT_PASSWORD`

2. Open `tomcat_temp.env` and fill all fields:
* `DB_URL=jdbc:mysql://DB_SERVICE_NAME:PORT/DB_NAME`
* `DB_USER=YOUR_USERNAME`
* `DB_PASSWORD=YOUR_PASSWORD`

3. Rename `db_temp.env` to `db.env` and `tomcat_temp.env` to `tomcat.env`.

4. Open project package in cmd and build `war` file with Maven:

```
mvn clean install
```

5. Start docker containers by docker-compose:

```
docker-compose up
```

6. Go to the following `URL`:

```
http://localhost:PORT_NUMBER/APP_NAME/users
```

You will see the list of users.

> To stop application type in cmd: `docker-compose stop`.

### Deployment on Tomcat

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
http://localhost:PORT_NUMBER/APP_NAME/users
```

You will see the list of users.

> To stop application run `shutdown.bat`.

