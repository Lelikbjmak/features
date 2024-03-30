package com.innowise.queryparametrization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.innowise")
@EnableScheduling
public class QuerypaPametrizationApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuerypaPametrizationApplication.class, args);
  }

}
