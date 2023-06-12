package com.example.cashcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* The main class for the CashCard application.
* It serves as the entry point of the application and contains the main method
* responsible for starting the Spring Boot application.
*/
@SpringBootApplication
public class CashCardApplication {
  /**
  * The main method that starts the CashCard application.
  *
  * @param args The command line arguments passed to the application.
  */
  public static void main(final String[] args) {
    SpringApplication.run(CashCardApplication.class, args);
  }
}
