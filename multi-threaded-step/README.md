# Multi-threaded-step
The multi-threaded-step project is an example of configuring a spring-batch application to use multi-threading. This example of multi-threading is applied to the step in spring-batch. This project assumes that you have a basic understanding of spring-batch and is intended to highlight implementing the multi-threaded-step. 

## Build
This project is built using Maven. 

*Assuming the user has maven installed follow these steps:*

1. In a terminal navigate to ***multi-threaded-step*** project. For example if the project is located at ~/workspace/spring-batch-examples/multi-threaded-step
   
   ```
   cd ~/workspace/spring-batch-examples/multi-threaded-step
   ```
2. To clean the project using the already installed maven command.
   
   ```
   mvn clean
   ```
3. Create the executable using the maven install command.
   
   ```
   mvn install
   ```
4. Verify build was successful with maven output and/or checking that the target jar files were created. The jar file that is created is an executable jar.

## Setup
The default run structure is setup in the /opt directory and is as follows:

* **multi-threaded-step**
  * **db** - default location of the database. Currently the database writes to a db file instead of in memory.
    * automatically created by the application.
  * **failed** - location of the files that failed during processing.
    * automatically created by the application if files fail processing.
    * creates a subfolder in yyyy-MM-dd format for file history.
  * **input** - location to place input files for processing.
  * **logs** - location of log file.
    * automatically created by the application if folder does not exist
    * previous day's log file will be renamed with the date appended to the end.
  * **processed** - location of the files successfully processed.
    * automatically created by the application if files successfully process.
    * creates a subfolder in yyyy-MM-dd format for file history.
  * **samples** - sample file that can be copied for processing.
  * **scripts** - location of scripts used for setup, running, and other miscellaneous uses you may have.

Note: Currently the project is setup to run in Linux and MacOS environments. This should be easily ported to a Windows console application by modifying some of the parameters in the application.properties and logback.xml.

## Properties
The default [application.properties](./src/main/resources/application.properties) included in the project can be used to control setup regarding running the application.

The user can either override an individual property or the entire application.properties file.

To override an individual property include **--[property-name]=[value]** as a runtime argument. For example:
```
--run-directory=/opt/multi-threaded-step
```

______

To override the entire application.properties with an application properties with a different application.properties file use the **--spring.config.location=file:/path/to/application.properties**. For example:
```
--spring.config.location=file:/opt/multi-threaded-step/config/application.properties
```

The recommended approach is *override the individual property and NOT the entire file*. This will prevent potential issues if the application.properties file is updated with new properties that the user forgets to add to their individual application.properties file.

## Logging
The logging uses the default Spring Boot logging and is configured with [logback.xml](./src/main/resources/logback.xml). This log allows logging to both the console and to a file. The level of logging and size can be controlled in the log file.

To override the logback.xml include **--logging.config=[/path/to/custom/logback.xml]** as a runtime argument. For example:
```
--logging.config=/opt/multi-threaded-step/config/logback.xml
```

## Run

The jar file created is an executable jar file. This means that the program can be run using the **java -jar [/path/to/multi-threaded-step-1.0.jar]** command. Here are a few examples for running.

Running the jar file from the command line without any options:
```
java -jar ~/workspace/spring-batch-examples/multi-threaded-step/target/multi-threaded-step-1.0.jar
```

Running the jar file from the command line overriding the *chunk-size* property:
```
java -jar ~/workspace/spring-batch-examples/multi-threaded-step/target/multi-threaded-step-1.0.jar --chunk-size=4
```

Running the jar file from the command line overriding the logging config file:
```
java -jar /Users/rtarrant/Documents/workspace/spring-batch-examples/multi-threaded-step/target/multi-threaded-step-1.0.jar --logging.config=/opt/multi-threaded-step/config/logback.xml
```

Running the jar file from the command line overriding the logging config file and the *chunk-size* property:
```
java -jar /Users/rtarrant/Documents/workspace/spring-batch-examples/multi-threaded-step/target/multi-threaded-step-1.0.jar --chunk-size=4 --logging.config=/opt/multi-threaded-step/config/logback.xml
```
