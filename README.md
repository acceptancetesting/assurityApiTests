# API Testing with Cucumber and RestAssured

This project is an example of how to use Cucumber and RestAssured to perform API testing. It includes sample feature files and step definitions for testing a hypothetical API for a Catalogue service.

## Getting Started

To get started with this project, clone the repository to your local machine:

GIT Clone https://github.com/acceptancetesting/assurityApiTests.git


### Prerequisites

This project requires the following software to be installed on your machine:

- Java JDK 8 or later
- Maven 3 or later

### Installing

To install the required dependencies, run the following command from the project root directory:

`mvn clean install`

## Environment Configuration
- Configuration files that contain properties for the API endpoints, such as the base URL and authentication credentials.
- There is a separate properties file for each environment (e.g., dev, uat, prod) that the API supports.
```
baseUrl=https://api.tmsandbox.co.nz/v1/
categories.basePath=Categories
```


## Running the Tests

To run the tests, use the following command:
`mvn clean test -Denv=<environment>`


Replace `<environment>` with the name of the environment you want to run the tests against, for example `dev`, `test`, or `prod`.

### Generating Reports

To generate a HTML report for the test results, use to run tests as mentioned above:
The report will be generated in the `target/cucumber-report/cucumber.html` directory.

## Built With

- [Cucumber](https://cucumber.io/) - A tool for behavior-driven development (BDD) testing
- [RestAssured](https://rest-assured.io/) - A Java library for testing RESTful APIs
- [Maven](https://maven.apache.org/) - A build automation tool for Java projects

## Author

- [Avneesh Rupal](https://github.com/acceptancetesting/assurityApiTests)



