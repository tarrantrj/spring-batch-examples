# Multi-threaded-step
The multi-threaded-step project is an example of configuring a spring-batch application to use multi-threading. This example of multi-threading is applied to the step in spring-batch. This project assumes that you have a basic understanding of spring-batch and is intended to highlight implementing the multi-threaded-step. 

## Build

## Setup
Currently the 

## Properties
The default [application.properties](./src/main/resources/application.properties) included in the project can be used to control setup regarding running the application.

The user can either override an individual property or the entire application.properties file.

To override an individual property include --[property-name]=[value] as a runtime argument. For example:
```
--run-directory=/opt/multi-threaded-step
```

To override the entire application.properties with an application properties with a different application.properties file use the --spring.config.location=file:/path/to/application.properties. For example:
```
--spring.config.location=file:/opt/multi-threaded-step/config/application.properties
```

The recommended approach is override the individual property and not the entire file. This will prevent potential issues if the application.properties file is updated with new properties that the user forgets to add to their individual application.properties file.

## Logging

## Run


