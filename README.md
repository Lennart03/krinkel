# Krinkel
[![Build Status](https://travis-ci.org/Nawsen/Krinkel.svg?branch=master)](https://travis-ci.org/Nawsen/Krinkel)
[![Coverage Status](https://coveralls.io/repos/github/Nawsen/Krinkel/badge.svg)](https://coveralls.io/github/Nawsen/Krinkel)

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

| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| POST     | $HOSTNAME:$PORT/api/participants              | Register a new Participant (Deelnemer)
| POST     | $HOSTNAME:$PORT/api/volunteers                | Register a new Volunteer (Medewerker)
| GET      | $HOSTNAME:$PORT/api/units                     | List of all groups.
| GET      | $HOSTNAME:$PORT/api/units/{stamnummer}        | Info of the group identified by {stamnummer}

## /api/units/{stamnummer}
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