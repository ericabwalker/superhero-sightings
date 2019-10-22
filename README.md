# Superhero Sightings

The superhero sightings application allows users to create superhero and supervillian organizations comprised of many heros or villians, and allows users to report sightings.

The application is built using Java, Spring Boot, Thymeleaf, and MySQL.

## Specification

The technical and design specification can be found in the [specification document](SPECIFICATION.pdf).

## Running the app

### Install JDK (if not installed)
1. Downlaod the [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html) and run the installer

#### Install MySQL (if not installed)
1. `brew install mysql`
2. `brew services start mysql`
3. `mysqladmin -u root password 'rootroot'` (This password is set in `test` and `main` `application.properties` files. User and password can be changed there if needed.) 

### Set up databases
1. `mysql -u root -p < SuperheroSightings.sql`
2. `mysql -u root -p < SuperheroSightingsTest.sql`

### Run tests
1. `./mvnw clean compile test`

### Run application
1. `./mvnw clean compile spring-boot:run`
2. Use app by opening [http://localhost:8080](http://localhost:8080) in a browser
3. Start by adding a location, then a hero, then an org, and then a sighting!