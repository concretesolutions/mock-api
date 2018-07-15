[![Build Status](https://travis-ci.org/elemental-source/mock-api.svg?branch=master)](https://travis-ci.org/elemental-source/mock-api)
[![codecov.io](https://codecov.io/github/elemental-source/mock-api/coverage.svg?branch=master)](https://codecov.io/github/elemental-source/mock-api?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2be4911c74b14b68a37e78ca4c2c8273)](https://www.codacy.com/app/elemental-source/mock-api?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=elemental-source/mock-api&amp;utm_campaign=Badge_Grade)

## Coverage History
![codecov.io](https://codecov.io/github/elemental-source/mock-api/branch.svg?branch=master)

# Mock API

Allows for reusable http request/response cycles in your tests, similar to WireMock.

## Features

* Capture live responses from external hosts and save the stubs locally
* Define your mocked responses using JSON
* Folder-based data files
* Per-request conditional proxying
* Simple YAML configuration

## Rules

All your requests should be directed to the application. When an HTTP request is made, the following matching rules are applied:

1. Is there a valid mock data file (i.e. request/response pattern) existing in `api.fileBase` folder that matches the request pattern? If so, the mocked response is returned. 
2. If not, does the request uri match any pattern described by the `api.uriConfigurations[].pattern` list? If so, the request will be dispatched to the matching `api.uriConfigurations[].host`.
3. If none of above is true, the request is dispatched to the default host `api.host`.

Should (2) or (3) occur, the response from an external host will be cached according to the `captureState` and `api.file.backup.path` properties. Next time the same request is made, it will be returned directly from the saved data file.

## Example of a matched request

Let's say you want to return the following mocked response for the following request: 

```
GET http://www.example.com/payments/111/description?code=ABCD
```

Expected response:

```
HTTP 200 (OK)
{
    "payment": {
        "from": "John",
        "to": "Fred",
        "value": 33
    }
}
```

You should create the following mock data file (its name has no meaning, it can be anything you like):

```
{
    // Request field describes what the request should look like in order to return the matched response.
    // In this case, query parameter "code" should be equal to "ABCD".
    // The request pattern is described by its method, headers, query parameters and body.
    request": {
        "query": {
            "code": "ABCD"
        }
    },
    // Response field describes the mocked response in case the request matches.
    // You can define the response body and status code.
    "response": {
        "body": {
            "payment": {
                "from": "John",
                "to": "Fred",
                "value": 33
            }
        },
        "httpStatus": 200
        }
}
```

Now, in order to properly use the data file, we should consider where it should be located. `mock-api` will parse the request and use the uri to search for the data file in the `api.fileBase` folder. In our example the request uri is `/payments/111/description?code=ABCD`, this means that the data file should be located in `${api.fileBase}/payments/111/description` folder (i.e. file location is based in uri parts and path parameters, other request fields are described in the data file itself). 

## Requirements

* Java JDK 8
* Gradle 4

## Run

### Using Your Property File
Create a valid property file in `src/main/resources/application-custom.yml` then run with `-Dspring.profiles.active=custom` argument. Example:

```shell
./gradlew bootRun -Dspring.profiles.active=custom
```

### Using Docker Image
To generate the Docker image, run: 

```shell
./gradlew buildDocker
```

By default, the image name will be `elemental-source/mock-api:VERSION`.

In order to run the application, create two folders: one containing the `application-custom.yml` configuration file and the other containing the mock data files. Then run:

```shell
docker run -d --name mock-api \
       -p 9090:9090 \
       -p 5000:5000 \
       -v /path/to/application-custom.yml:/config/application.yml \
       -v /path/to/mock/data/files:/data \
       elemental-source/mock-api:VERSION
```

Port `9090` exposes the service while port `5000` can be used to debug the application.

You can check application logs from the container: `docker logs -f mock-api`

You can configure your program arguments in your IDE:
```
--spring.config.location=file/name/example/application.yml
```

#### Using docker-compose

Run `docker-compose up --build`

Get results: `curl -X GET 'http://localhost:9090/guests/132'`

Look `api.host` property at `src/main/resources/application.yml` file.
The value is http://www.mocky.io, the host that will be called if there is no file that dont match with request.
If you create a new mock, you can call directly. Concat `localhost:9090` with the generated link, like this:

```shell
curl -X GET 'http://localhost:9090/v2/5b4bbb0c3100003503a7de45'
```

The response will not found at mock files and then, the external host will be called.

## TODO

- [X] Fix code Style
- [ ] Create an example of a property file
- [ ] Split unit from integrated tests
- [ ] Fix skipping tests
- [ ] Review dependencies (check, for instance, if it's even necessary to have both GSON and modelmapper dependencies)
- [ ] Use objectmapper as component: `compile ('com.fasterxml.jackson.datatype: jackson-datatype-jdk8')``
