#!/usr/bin/bash

# Start database
docker volume create realtor_volume
docker image pull postgres:latest
docker container run -d --name realtor_db -p 5432:5432 -v realtor_volume -e POSTGRES_DB=realtor_db -e POSTGRES_USER=realtor -e POSTGRES_PASSWORD=secretpassword postgres

