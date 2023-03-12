# John's Notes

First of all, this code challenge is an outrage! The Beatles was a flat organization, 
except when John reported to Yoko. Paul McCartney has certainly progressed beyond
Developer 1 by now, and Pete Best is an honorable mention at _best_. Personally, I'm a drummer, 
so I was glad to see Ringo progressing so nicely in his dev career.

This was a great coding challenge as it provided some interpretation to complete and 
content for some great discussion. I strayed from the exact instructions a bit based on my 
own experience and judgement. Hopefully what I've done is in the ballpark of what you were looking for, 
and if not, I'd love to find out where I was off-base!

### General 

1) The code was not commented, and I mostly kept it that way. I only added some for classes and for code-challenge review, 
and resisted the urge to spend time commenting the whole project.
1) I cleaned up the code analysis Warnings, but when run, there are some Warnings such as...
"Registering converter from class java.time.LocalDateTime to class java.time.Instant..." that I'd want to resolve.

### ReportingStructure Notes

1) Some additional context and use cases may steer things differently, but I saw this as just another read of Employee data
with a lot more detail, including the object graph. That's why I added it to the EmployeeService and not create
a new service for this. That is also why I called it EmployeeReportingStructure. 
1) I nested the direct reports with their Employee details and details of their DirectReports, however, it
may be a better service to provide a tree of EmployeeReportingStructure objects, but I didn't go there.

### Compensation Notes:

1) Unlike ReportingStructure, Compensation has its own set of Compensation/money related services and has its own data and service.
1) For Compensation, I decided to go with a one-to-many since that's usually the case and more interesting. Also,
there's no enforcement for having one Compensation per Employee. Another service endpoint or filter would have 
it only retrieve the current compensation value.
1) I created Compensation as a separate entity with its CompensationId as all entities that I've ever worked with have 
their own unique id.  Perhaps this is not in line with this type of system. It associates with an existing EmployeeId.
1) I resisted the urge to make Compensation be EmployeeCompensation as that is how it would be in the systems I've built.
A good topic of conversation.

---
(Original Readme)

# Coding Challenge



## What's Provided
A simple [Spring Boot](https://projects.spring.io/spring-boot/) web application has been created and bootstrapped 
with data. The application contains information about all employees at a company. On application start-up, an in-memory 
Mongo database is bootstrapped with a serialized snapshot of the database. While the application runs, the data may be
accessed and mutated in the database without impacting the snapshot.

### How to Run
The application may be executed by running `gradlew bootRun`.

### How to Use
The following endpoints are available to use:
```
* CREATE
    * HTTP Method: POST 
    * URL: localhost:8080/employee
    * PAYLOAD: Employee
    * RESPONSE: Employee
* READ
    * HTTP Method: GET 
    * URL: localhost:8080/employee/{id}
    * RESPONSE: Employee
* UPDATE
    * HTTP Method: PUT 
    * URL: localhost:8080/employee/{id}
    * PAYLOAD: Employee
    * RESPONSE: Employee
```
The Employee has a JSON schema of:
```json
{
  "type":"Employee",
  "properties": {
    "employeeId": {
      "type": "string"
    },
    "firstName": {
      "type": "string"
    },
    "lastName": {
          "type": "string"
    },
    "position": {
          "type": "string"
    },
    "department": {
          "type": "string"
    },
    "directReports": {
      "type": "array",
      "items" : "string"
    }
  }
}
```
For all endpoints that require an "id" in the URL, this is the "employeeId" field.

## What to Implement
Clone or download the repository, do not fork it.

### Task 1
Create a new type, ReportingStructure, that has two properties: employee and numberOfReports.

For the field "numberOfReports", this should equal the total number of reports under a given employee. The number of 
reports is determined to be the number of directReports for an employee and all of their distinct reports. For example, 
given the following employee structure:
```
                    John Lennon
                /               \
         Paul McCartney         Ringo Starr
                               /        \
                          Pete Best     George Harrison
```
The numberOfReports for employee John Lennon (employeeId: 16a596ae-edd3-4847-99fe-c4518e82c86f) would be equal to 4. 

This new type should have a new REST endpoint created for it. This new endpoint should accept an employeeId and return 
the fully filled out ReportingStructure for the specified employeeId. The values should be computed on the fly and will 
not be persisted.

### Task 2
Create a new type, Compensation. A Compensation has the following fields: employee, salary, and effectiveDate. Create 
two new Compensation REST endpoints. One to create and one to read by employeeId. These should persist and query the 
Compensation from the persistence layer.

## Delivery
Please upload your results to a publicly accessible Git repo. Free ones are provided by Github and Bitbucket.
