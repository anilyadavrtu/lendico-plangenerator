# Lendico Plan Generator
Repayment Plan Generator with Java 8, Maven, Spring Boot, Mockito and swagger MVC for Lendico
## Running the project locally

### Installation

Clone this repository:

```sh
$ git clone git@gitlab.com:anil_live/lendico-plangenerator.git
```

Build with Maven

```sh
$ cd <your_git_home>/lendico-plangenerator
$ mvn install
```

### Running the application

Start the Spring Boot application

```sh
$ cd <your_git_home>/LendicoPlangeneratorApplication
$ java -jar target/lendico-plangenerator-1.0.0-SNAPSHOT.jar
```

### Making a HTTP POST request

open http://localhost:8080/swagger-ui.html to make a POST request:

```
http://localhost:8080/swagger-ui.html#/repayment-plan-controller
```

As for the JSON payload, Use the example below:

```
{
  "duration": 24,
  "loanAmount": 5000,
  "nominalRate": 5.0,
  "startDate": "2018-01-01T00:00:01Z"
}
```
### Open URL to check the response Screenshot
```
https://gitlab.com/anil_live/lendico-plangenerator/blob/master/screencapture-localhost-8080-swagger-ui-html-2019-12-25-15_22_36.pdf
```