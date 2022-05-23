<h1 align="left"> Qardio Case Study </h1>

### Qardio Case Study - Temperature API
In this project a REST microservice developed.

#How To Run:
- The project has been dockerized. In order to run the service, it is sufficient to call the **"docker-compose up"** command from the path of the project folder which include docker-compose.yaml.
  It can also be run by TemperatureApplication.java class. There are no need any environment variables.

- Unit tests can be run with the **"mvn test"** command.

# Technical Details
- The application was developed using Java 11.
- Spring Boot was used as application framework.
- H2 in memory db was used as database.
- Junit was used for unit tests.
- The application was built with maven.
- The application was dockerized and a docker-compose.yml was written.


#Sample Request And Response:

You can find the sample json file for Postman under the **"postman"** directory under the project directory.

**- Sample Request and Response For Temperature Data Saving:**

For Temperature Data Saving;

**Post Url**: http://localhost:8088/v1/temperature/save

**Body**:
{
"deviceId": "test1",
"tempDegree": 4.0,
"date": "2022-05-22T13:32:59.286402400"
}

For Bulk Temperature Data Saving;
**Post Url**: http://localhost:8088/v1/temperature/bulk-save

**Body**:
[{
"deviceId": "test2",
"tempDegree": 7.0,
"date": "2022-05-23T13:32:59.286402400"
}, {
"deviceId": "test1",
"tempDegree": 4.0,
"date": "2022-05-22T13:32:59.286402400"
}]

**Response**:
{
"timestamp": "2022-05-22T23:25:47.3742763",
"response": "CREATED"
}

**-Sample Request and Response For Temperature Aggregate Data Report:**

For Hourly Aggregate Data;
**Get Url:** http://localhost:8088/v1/temperature/hourly

For Daily Aggregate Data;
**Get Url:** http://localhost:8088/v1/temperature/daily

**Response:**
{
"tempDataCount": 5,
"averageTempDegree": 14.4,
"minTempDegree": 4.0,
"maxTempDegree": 32.0
}

---
### Prepared by
- Busra ICOZ
- icozbsr@gmail.com
- 05/22/2022
#
#
#
#
#