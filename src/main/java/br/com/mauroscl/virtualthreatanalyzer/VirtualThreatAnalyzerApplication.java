package br.com.mauroscl.virtualthreatanalyzer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VirtualThreatAnalyzerApplication {

  public static void main(String[] args) {
    SpringApplication.run(VirtualThreatAnalyzerApplication.class, args);
  }

}
