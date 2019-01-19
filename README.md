# Metasandra [WIP]

A metadata management service built on top of Cassandra. Many services
and projects require storing some form of metadata, such as user information.
This project provides a very easy to use RESTful service built using Spring
Boot and Cassandra and provides a schemaless solution to store and keep track
of metadata.

## Build Locally

The project depends on cassandra so you can quickly spin up cassandra locally
by running the following:

```sh
docker run --rm --name metasandra -p 7000:7000 -p 9042:9042 cassandra
```

Then the service can be run using the following command:

```sh
mvn spring-boot:run
```

## TODO

- Exception handling for endpoints
- Unit test using cassandra unit
- Track commits in a different table
- Track history for metadata
- Make api to view and set versions of metadata
- Queries responds with latests or sert version of metadata
- Improve label query api (perhaps map reduce)
- Schedule task to delete old versions of metadata
- Improve configuration management
- Solidify api and unit test
- Launch initial version of service
