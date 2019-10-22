DROP DATABASE IF EXISTS superheroSightingsTest;
CREATE DATABASE superheroSightingsTest;

USE superheroSightingsTest;

CREATE TABLE Hero (
	heroID INT PRIMARY KEY AUTO_INCREMENT,
    heroName VARCHAR (50) NOT NULL,
    heroDescription VARCHAR(200) NOT NULL,
    superPower VARCHAR(50) NOT NULL,
    heroStatus CHAR(2) NOT NULL
);

CREATE TABLE Location (
	locationID INT PRIMARY KEY AUTO_INCREMENT,
    locationName VARCHAR(50) NOT NULL,
    locationDescrip VARCHAR(200) NOT NULL,
	street1 VARCHAR(100) NOT NULL,
    street2 VARCHAR(100),
    city VARCHAR(50) NOT NULL,
	state CHAR(2) NOT NULL,
    zipcode CHAR(5),
    country VARCHAR(80) NOT NULL,
    locationLat CHAR(15) NOT NULL,
    locationLong CHAR(15) NOT NULL
);

CREATE TABLE Org (
	orgID INT PRIMARY KEY AUTO_INCREMENT,
    orgName VARCHAR(50) NOT NULL,
    orgDescrip VARCHAR(200) NOT NULL,
    orgPhone CHAR(15),
    orgEmail VARCHAR(50),
    orgStatus CHAR(2) NOT NULL,
    locationID INT NOT NULL,
    FOREIGN KEY fk_Org_Loc(locationID) REFERENCES Location(locationID)
);

CREATE TABLE OrgMembers (
	orgMemID INT PRIMARY KEY AUTO_INCREMENT,
    orgID INT NOT NULL,
    heroID INT NOT NULL, 
    FOREIGN KEY fk_OM_Org(OrgID) REFERENCES Org(orgID),
    FOREIGN KEY fk_OM_Hero(HeroID) REFERENCES Hero(HeroID)
);

CREATE TABLE Sighting (
	sightingID INT PRIMARY KEY AUTO_INCREMENT,
    sightingDate DATETIME NOT NULL, 
    locationID INT NOT NULL, 
    heroID INT NOT NULL, 
    FOREIGN KEY fk_Sight_Loc(locationID) REFERENCES Location(locationID),
    FOREIGN KEY fk_Sight_Hero(heroID) REFERENCES Hero(heroID)
);
