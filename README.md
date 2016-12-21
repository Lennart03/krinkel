# Krinkel
[![Build Status](https://travis-ci.com/Bumbolt/krinkel.svg?token=Zeuk5x1ZDC7EQTmDtyaK&branch=master)](https://travis-ci.com/Bumbolt/krinkel)

## Module dependencies
![alt tag](http://i.imgur.com/Pj9z23V.png)

## ERD
![alt tag](http://i.imgur.com/dyYmUBP.png)


## Links

* [Angular Frontend](http://localhost:8080/)
* [Static Website with general info](http://localhost:8080/site/index.html)

## How to clone

1. git config --global http.sslVerify false
2. CLONE PROJECT
3. git config --global http.sslVerify true
4. git config http.sslVerify false

## Setup global project

1. Start MySQL server
2. Create database "krinkel"
3. Run Maven
4. Startup Application

## Setup frontend project

1. Goto Krinkel-Frontend/
2. Run "npm install"
3. Run "gulp serve"

## Build with integration tests disabled

    mvn clean package -DskipIntegration

## Build a single Maven Submodule

Only builds one of the submodules, not everything:

    mvn clean package -pl Krinkel-Backend/


## Overview of API Endpoints
### ChiroColleagueController

| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| GET      | $HOSTNAME:$PORT/api/colleagues                | Get all colleagues by current User adNumber

### ChiroContactController
| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| GET      | $HOSTNAME:$PORT/api/contact/{adNumber}        | Get information about current User from chiro

### ChiroPloegController
| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| GET      | $HOSTNAME:$PORT/api/ploegen/{adNumber}        | Get all ploegen by adNumber from chiro

### GraphController
| Method   | URL                                                          | Description (all angular-nvd3)
| -------- |:------------------------------------------------------------:|:---------------------------
| GET      | $HOSTNAME:$PORT/api/graph/sun                                | make Sunburst Chart 
| GET      | $HOSTNAME:$PORT/api/graph/status                             | make Pie Chart
| GET      | $HOSTNAME:$PORT/api/graph/uniqueLoginsPerVerbond             | make MuliBar Chart with all login data
| GET      | $HOSTNAME:$PORT/api/graph/uniqueLoginsPerVerbondLastTwoWeeks | make MuliBar Chart with off last two weeks

### RegistrationParticipantController
| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| POST     | $HOSTNAME:$PORT/api/participants              | Register a new Participant (Deelnemer)

### RegistrationVolunteerController
| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| POST     | $HOSTNAME:$PORT/api/volunteers                | Register a new Volunteer (Medewerker)

### UnitController
| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| GET      | $HOSTNAME:$PORT/api/units                           | Get alle Verbonden
| GET      | $HOSTNAME:$PORT/api/units/{stamNummer}                    | Get alle Gewest/Groepen by stamNummer
| GET      | $HOSTNAME:$PORT/api/units/{stamNummer}/participants | Get alle participants by stamNummer groep
| GET      | $HOSTNAME:$PORT/api/units/{stamNummer}/volunteers   | Get alle volunteers by stamNummer groep

## /api/units/{stamNummer}
Should return the following info:

    {
        "naam" : "",
        "stamnummer : "",
        "bovenliggende_stamnummer" : "" | null,
        "onderliggende_stamnummers" : [ "", ... ]
    }

## Auth for angular development (gulp serve)

In your browser console run following script:

    document.cookie="Authorization=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3YW5uZXMzODYyOTMiLCJmaXJzdG5hbWUiOiJXYW5uZXMiLCJsYXN0bmFtZSI6IlZhbiBEb3JwZSIsImFkbnVtbWVyIjoiMzg2MjkzIiwiZW1haWwiOiJ3YW5uZXMudmFuZG9ycGVAcmVhbGRvbG1lbi5jb20iLCJyb2xlIjoiQURNSU4iLCJpYXQiOjE0NzYyMjE5NDl9.tBEEHInAi2XIytldcPw6j0Y_fQDDt5WI2t2Qyo_6qBc"

This will Authenticate and save following user in AuthService:

    {
        adnummer: "386293",
        email: "wannes.vandorpe@realdolmen.com",
        firstname: "Wannes",
        iat: 1476221949,
        lastname: "Van Dorpe",
        role: "ADMIN",
        sub: "wannes386293"
    }
    
    
## Switching profiles

The following three profiles are available:

* development : During coding
* production : The real thing
* test : Unit testing

The default profile is `development`.

Each profile loads `application.properties` which defines the settings  common to all environments. 
In addition a `application-<profile>.properties` is loaded with specific settings for the profile.
These properties files can be found in **Krinkel-App-Config** for common, `development` and `production`; and **Krinkel-Test-Base** for `test`.

You can set the profile at runtime by launching the executable the following parameter: `-Dspring.profiles.active=production|development|test`
