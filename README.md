# Krinkel
[![Build Status](https://travis-ci.org/Nawsen/Krinkel.svg?branch=master)](https://travis-ci.org/Nawsen/Krinkel)
[![Coverage Status](https://coveralls.io/repos/github/Nawsen/Krinkel/badge.svg)](https://coveralls.io/github/Nawsen/Krinkel)

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

## Build a single Maven Submodule

Only builds one of the submodules, not everything:

    mvn clean package -pl Krinkel-Backend/


## Overview of API Endpoints

| Method   | URL                                           | Description
| -------- |:---------------------------------------------:|:---------------------------
| POST     | $HOSTNAME:$PORT/api/users?password=""&user="" | User login
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

## Test users

* PJ : password
* Ziggy : test