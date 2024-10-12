# Expass 7

## Task 1 - Postgres image

I build the container with a docker compose file containing the following:
```yml
services:
    my-postgres:
      image: postgres
      ports:
        - "5432:5432"
      environment:
        POSTGRES_PASSWORD: secret
      volumes:
        - type: bind
          source: ./schema.up.sql
          target: /docker-entrypoint-initdb.d/init.sql
          read_only: true
```
With this and by following the other steps the unit tests passed.

## Task 2 - dockerized application

By using the documentation for dockerfiles and the dockerfile used in lecture 14, I managed to make the
application run and by exposing it to the 8080 port I could access and use the app as I would normally.

[Link to dockerfile](./Dockerfile)
