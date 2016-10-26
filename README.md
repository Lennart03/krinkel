# Krinkel
[![Build Status](https://travis-ci.org/Nawsen/Krinkel.svg?branch=master)](https://travis-ci.org/Nawsen/Krinkel)
[![Coverage Status](https://coveralls.io/repos/github/Nawsen/Krinkel/badge.svg)](https://coveralls.io/github/Nawsen/Krinkel)

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