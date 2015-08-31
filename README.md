# rest-error-handling
A common Java library for REST Controller error handling and logging

## Introduction
This library is adding an implementation of the ```ControllerAdvice```, which is catching all unhandled exceptions
thrown by Spring REST Controller classes and making sure that both error message and error log contain the same
reference to the error id (better supportability).

## Usage

To use the library in the Java Spring project, please add this dependency to the pom.xml file of the project:
```
<dependency>
    <groupId>org.trustedanalytics</groupId>
    <artifactId>rest-error-handling</artifactId>
    <version>...</version>
</dependency>
```

Then enable this feature using annotation:
```
@SpringBootApplication
@EnableRestErrorHandling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```