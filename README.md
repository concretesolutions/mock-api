[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

App created to mock with REST using JSON.

## Rules

When a request is made the following flow is followed:

* Does it exist in the mock folder (as per the `api.fileBase` property)? If so, return the mock. 
* Does the uri fit into any pattern in the `api.uriConfigurations []. Pattern` list? If so, it will redirect according to the configuration and cache according to the `backup` field.
* If it does not enter the previous flows, it will redirect to the default host `api.host`

## Requirements
* Java JDK 8
* Gradle 4

## Run

## Using Your Property File
Create your `src / main / resources / application-custom.yml` property file and run it with the` -Dspring.profiles.active = custom` argument. Example:

```
gradle bootRun -Dspring.profiles.active=custom
```

## Using Docker Image
To generate the project Docker image, run: 

```
gradle buildDocker
```

By default, the image name will be `elemental-source / mock-api: VERSAO`.

To run the application, create two directories: one containing the `application-custom.yml` configuration file and the other containing the mock files. Then run:

```
docker run -d --name mock-api \
       -p 9090:9090 \
       -v /path/para/arquivo/application-custom.yml:/config/application.yml \
       -v /path/para/diretorio/dados/:/data \
       elemental-source/mock-api:VERSAO
```

The `9090` port exposes the service while the` 5000` port is used to debug the application.

To view the application logs from the container: `docker logs -f mock-api`

## ALL
- [X] Correct Code Style
- [ ] Insert example of "property file" in README
- [ ] Separate unit tests from integrated tests
- [ ] Fix skipping tests
- [ ] Review dependencies (see, for example, if it is even necessary to have the GSON or modelmapper)
- [ ] Use objectmapper as component: `compile ('com.fasterxml.jackson.datatype: jackson-datatype-jdk8')``
